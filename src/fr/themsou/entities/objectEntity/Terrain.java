package fr.themsou.entities.objectEntity;

import fr.themsou.models.RawModel;
import fr.themsou.models.textures.TerrainTexture;
import fr.themsou.models.textures.TerrainTexturePack;
import fr.themsou.renderEngine.Loader;
import fr.themsou.utils.Maths;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Terrain {

    private static final float SIZE = 100;
    private static final int HEIGHT = 20;
    private static final int MAX_PIXEL_COLOUR = 256*256*256;

    private float x;
    private float y;
    private float z;

    private RawModel model;
    private TerrainTexturePack textures;
    private TerrainTexture blendMap;
    private String heightMap;

    private float[][] heights;

    public Terrain(int gridX, int gridZ, Loader loader, TerrainTexture blendMap, String heightMap, TerrainTexturePack textures){

        this.blendMap = blendMap;
        this.heightMap = heightMap;
        this.textures = textures;
        this.x = gridX * SIZE;
        this.z = gridZ * SIZE;
        this.model = generateTerrainRawModel(loader);

    }

    public boolean isIn(float x, float z){
        float terrainX = x - this.x;
        float terrainZ = z - this.z;

        float gridSquareSize = SIZE / (heights.length - 1f);
        int gridX = (int) Math.floor(terrainX / gridSquareSize);
        int gridZ = (int) Math.floor(terrainZ / gridSquareSize);

        if(gridX >= heights.length-1 || gridZ >= heights.length-1 || gridX < 0 || gridZ < 0){
            return false;
        }return true;
    }

    public float getHeight(float x, float z){
        float terrainX = x - this.x;
        float terrainZ = z - this.z;

        float gridSquareSize = SIZE / (heights.length - 1f);
        int gridX = (int) Math.floor(terrainX / gridSquareSize);
        int gridZ = (int) Math.floor(terrainZ / gridSquareSize);

        if(gridX >= heights.length-1 || gridZ >= heights.length-1 || gridX < 0 || gridZ < 0){
            return 0;
        }
        float xCoord = (terrainX % gridSquareSize)/gridSquareSize;
        float zCoord = (terrainZ % gridSquareSize)/gridSquareSize;

        float answer;
        if(xCoord <= (1-zCoord)){
            answer = Maths.barryCentric(new Vector3f(0, heights[gridX][gridZ], 0), new Vector3f(1,
                            heights[gridX + 1][gridZ], 0), new Vector3f(0,
                            heights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
        }else{
            answer = Maths.barryCentric(new Vector3f(1, heights[gridX + 1][gridZ], 0), new Vector3f(1,
                            heights[gridX + 1][gridZ + 1], 1), new Vector3f(0,
                            heights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
        }

        return answer;
    }

    private RawModel generateTerrainRawModel(Loader loader){

        // Lis la heightMap
        BufferedImage heightMapImage = null;
        try{
            heightMapImage = ImageIO.read(new File(heightMap));
        }catch(IOException e){ e.printStackTrace(); }

        int VERTEX_COUNT = heightMapImage.getHeight();
        heights = new float[VERTEX_COUNT][VERTEX_COUNT];

        // Initialise count qui représente le nombre de vertex
        int count = VERTEX_COUNT * VERTEX_COUNT;

        // Initialise les tableau contenant les donnés du RawModel
        float[] vertices = new float[count * 3];
        float[] normals = new float[count * 3];
        float[] textureCoords = new float[count * 2];
        int[] indices = new int[6 * (VERTEX_COUNT-1) * (VERTEX_COUNT-1) ];

        // Setup les tableau de vertex, de textures et de normals
        int vertexPointer = 0;
        for(int i = 0 ; i < VERTEX_COUNT ; i++){
            for(int j = 0 ; j < VERTEX_COUNT ; j++){

                vertices[vertexPointer*3] = (float) j / ((float) VERTEX_COUNT - 1) * SIZE;
                float height = getHeight(j, i, heightMapImage);
                heights[j][i] = height;
                vertices[vertexPointer*3+1] = height;
                vertices[vertexPointer*3+2] = (float) i / ((float) VERTEX_COUNT - 1) * SIZE;

                Vector3f normal = calculateNormal(i, j, heightMapImage);
                normals[vertexPointer*3] = normal.x;
                normals[vertexPointer*3+1] = normal.y;
                normals[vertexPointer*3+2] = normal.z;

                textureCoords[vertexPointer*2] = (float)j/((float)VERTEX_COUNT - 1);
                textureCoords[vertexPointer*2+1] = (float)i/((float)VERTEX_COUNT - 1);

                vertexPointer++;
            }
        }

        // Setup le tableau d'indices
        int pointer = 0;
        for(int gz = 0 ; gz < VERTEX_COUNT-1 ; gz++ ){
            for(int gx = 0 ; gx < VERTEX_COUNT-1 ; gx++ ){

                int topLeft = (gz * VERTEX_COUNT) + gx;
                int topRight = topLeft + 1;
                int bottomLeft = ((gz+1) * VERTEX_COUNT) + gx;
                int bottomRight = bottomLeft + 1;

                indices[pointer++] = topLeft;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = topRight;
                indices[pointer++] = topRight;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = bottomRight;
            }
        }
        return loader.loadToVAO(vertices, textureCoords, normals, indices);
    }
    private Vector3f calculateNormal(int x, int z, BufferedImage heightMap){
        float heightL = getHeight(x-1, z, heightMap);
        float heightR = getHeight(x+1, z, heightMap);
        float heightD = getHeight(x, z-1, heightMap);
        float heightU = getHeight(x, z+1, heightMap);

        Vector3f normal = new Vector3f(heightL-heightR, 2f, heightD-heightU);
        normal.normalise();
        return normal;
    }
    private float getHeight(int x, int z, BufferedImage heightMap){
        if(x<0 || x>=heightMap.getHeight() || z<0 || z>=heightMap.getHeight()){
            return 0;
        }
        float height = heightMap.getRGB(x, z)*-1;
        return height / MAX_PIXEL_COLOUR * HEIGHT;

    }

    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public float getZ() {
        return z;
    }
    public RawModel getModel() {
        return model;
    }
    public TerrainTexturePack getTextures() {
        return textures;
    }
    public TerrainTexture getBlendMap() {
        return blendMap;
    }
}
