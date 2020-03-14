package fr.themsou.renderEngine;

import fr.themsou.models.RawModel;
import fr.themsou.models.textures.ModelTexture;
import fr.themsou.models.textures.TerrainTexture;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class Loader {

    private List<Integer> vaos = new ArrayList<>();
    private List<Integer> vbos = new ArrayList<>();
    private List<Integer> textures = new ArrayList<>();

    public RawModel loadToVAO(float[] positions, float[] textureCoords, float[] normals, int[] indices){ // Crée un VAO et le renvoie sous forme de RawModel (Qui peut être stoqué dans un TexturedModel)

        int vaoID = GL30.glGenVertexArrays();
        vaos.add(vaoID);
        GL30.glBindVertexArray(vaoID); // Bind le VAO
            bindIndicesBuffer(indices); // Définir les indices de vertex
            storeDataInAttributeList(0, 3, positions); // Définir les positions des vertex
            storeDataInAttributeList(1, 2, textureCoords); // Définir les coordonnés de texture
            storeDataInAttributeList(2, 3, normals); // Définir les normales des vertex
        GL30.glBindVertexArray(0); // Unbind le VAO

        return new RawModel(vaoID, indices.length);
    }

//////////////////////////////////// ENREGISTREMENT DES VBO /////////////////////////////////////

    private void storeDataInAttributeList(int attributeNumber, int coordinateSize,  float[] data){ // Charger des floats dans un VBO et l'ajouter dans un slot du VBO

        int vboID = GL15.glGenBuffers(); // générer le vVBO
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID); // bind le VBO

        FloatBuffer buffer = storeDataInFloatBuffer(data); // Traduire la data en data enregistrable dans le VBO
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW); // Initialiser le contenu du VBO
        GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0); // Ajouter le VBO dans le VAO

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0); // unbind le VBO

    }

    private void bindIndicesBuffer(int[] indices){ // Charger les indices de vertex dans le slot spécial (Automatic)

        // Les VAOs contiennent un "slot spécial" pour les index vbo, le fait de bind un vbo "d'index"
        // le bind automatiquement au VAO courrant. C'est pourquoi on n'ajoute pas le vbo dans le VAO
        // et qu'on ne dé-bind pas le vbo.

        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);

        IntBuffer buffer = storeDataInIntBuffer(indices); // Traduire la data en data enregistrable dans le VBO
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW); // Initialiser le contenu du VBO (Puis il se bind automatiquement au VAO)

    }

/////////////// TRANSFERER DES FLOAT EN FLOATBUFFER (Changer l'emplacement de la variable pour que la CG puisse la lire) ///////////////

    private IntBuffer storeDataInIntBuffer(int[] data){

        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();

        return buffer;
    }
    private FloatBuffer storeDataInFloatBuffer(float[] data){

        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();

        return buffer;
    }

//////////////////////////////////////////////////////////////////////////////////////////

    // MODEL
    public ModelTexture loadModelTexture(String modelName, String fileName, int sample){
        return loadTexturePath("res/model/" + modelName + "/" + (fileName == null ? "texture.png" : fileName), sample);
    }
    public ModelTexture loadTexture(String fileName, int sample){
        return loadTexturePath("res/textures/" + fileName, sample);
    }
    public ModelTexture loadTexturePath(String path, int sample){ // Instancie un ModelTexture à partir d'un ID de texture

        ModelTexture modelTexture = new ModelTexture(loadTexturePathId(path), sample);
        return modelTexture;
    }

    // TERRAIN
    public String getTerrainFileName(String terrainName, String fileName){
        return "res/terrains/" + terrainName + "/" + fileName;
    }
    public TerrainTexture loadTerrainTexture(String terrainName, String fileName){
        return loadTerrainTexturePath(getTerrainFileName(terrainName, fileName));
    }
    public TerrainTexture loadTerrainTexturePath(String path){ // Instancie un terrainTexture à partir d'un ID de texture

        TerrainTexture terrainTexture = new TerrainTexture(loadTexturePathId(path));
        return terrainTexture;
    }

    // GENERAL
    public int loadTexturePathId(String path){ // Charger une texture et renvoyer un ID (Qui peut être chargé dans un ModelTexture qui lui même peut être stoqué dans un TexturedModel)
        Texture texture = null;
        try{
            texture = TextureLoader.getTexture("PNG", new FileInputStream(path));
            GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, -0.4f);
        }catch(IOException e){ e.printStackTrace(); }

        return texture.getTextureID();
    }

//////////////////////////////////////////////////////////////////////////////////////////

    public void cleanUP(){

        for(int vaoID : vaos){
            GL30.glDeleteVertexArrays(vaoID);
        }
        for(int vboID : vbos){
            GL15.glDeleteBuffers(vboID);
        }
        for(int textureID : textures){
            GL11.glDeleteTextures(textureID);
        }

    }
}
