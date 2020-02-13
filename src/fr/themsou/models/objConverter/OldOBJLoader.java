package fr.themsou.models.objConverter;

import fr.themsou.main.Main;
import fr.themsou.renderEngine.Loader;
import fr.themsou.models.textures.ModelTexture;
import fr.themsou.models.RawModel;
import fr.themsou.models.TexturedModel;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class OldOBJLoader {

    public static TexturedModel loadObjTexturedModel(String modelName, Loader loader, int sample){

        RawModel model = OldOBJLoader.loadObjModel(modelName, Main.loader);
        ModelTexture texture = Main.loader.loadTexturePath("res/model/" + modelName + "/texture.png", sample);
        return new TexturedModel(model, texture);
    }

    public static RawModel loadObjModel(String modelName, Loader loader){

        FileReader fr = null;
        try{
            fr = new FileReader(new File("res/model/" + modelName + "/model.obj"));
        }catch(FileNotFoundException e){
            System.err.println("Couldn't load file !");
            e.printStackTrace();
        }

        BufferedReader reader = new BufferedReader(fr);
        String line;

        List<Vector3f> vertices = new ArrayList<>();
        List<Vector2f> textures = new ArrayList<>();
        List<Vector3f> normals = new ArrayList<>();

        float[] verticesArray = null;
        float[] textureArray = null;
        float[] normalsArray = null;

        List<Integer> indices = new ArrayList<>();
        int[] indicesArray = null;

        try{

            while(true){
                line = reader.readLine();
                String[] currentLine = line.split(" ");

                if(line.startsWith("v ")){ // Vertex coordinate

                    Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
                    vertices.add(vertex);

                }else if(line.startsWith("vt ")){ // Texture coordiante

                    Vector2f texture = new Vector2f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]));
                    textures.add(texture);

                }else if(line.startsWith("vn ")){ // Normals

                    Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
                    normals.add(vertex);

                }else if(line.startsWith("f ")){ // Face

                    textureArray = new float[vertices.size()*2];
                    normalsArray = new float[vertices.size()*3];
                    break;
                }
            }

            while(line != null){
                if(!line.startsWith("f ")){
                    line = reader.readLine();
                    continue;
                }

                String[] currentLine = line.split(" ");
                String[] vertex1 = currentLine[1].split("/");
                String[] vertex2 = currentLine[2].split("/");
                String[] vertex3 = currentLine[3].split("/");


                // Demande à la méthode de stoquer dans les Array (et dans la List pour les indices) les donnés des List
                processVertex(vertex1, indices, textures, normals, textureArray, normalsArray);
                processVertex(vertex2, indices, textures, normals, textureArray, normalsArray);
                processVertex(vertex3, indices, textures, normals, textureArray, normalsArray);

                line = reader.readLine();
            }

            reader.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        verticesArray = new float[vertices.size()*3];
        indicesArray = new int[indices.size()];

        int vertexPointer = 0;

        for(Vector3f vertex : vertices){ // Convertis la List "vertices" en Array "verticesArray"

            verticesArray[vertexPointer++] = vertex.x;
            verticesArray[vertexPointer++] = vertex.y;
            verticesArray[vertexPointer++] = vertex.z;
        }

        for(int i = 0; i < indices.size(); i++){ // Convertis la List "indices" en Array "indicesArray"
            indicesArray[i] = indices.get(i);
        }

        return loader.loadToVAO(verticesArray, textureArray, normalsArray, indicesArray);

    }

    private static void processVertex(String[] vertexData, List<Integer> indices, List<Vector2f> textures, List<Vector3f> normals, float[] textureArray, float[] normalsArray){


        int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
        indices.add(currentVertexPointer);

        Vector2f currentTex = textures.get(Integer.parseInt(vertexData[1]) - 1);
        textureArray[currentVertexPointer*2] = currentTex.x;
        textureArray[currentVertexPointer*2+1] = 1 - currentTex.y;

        Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2]) - 1);
        normalsArray[currentVertexPointer*3] = currentNorm.x;
        normalsArray[currentVertexPointer*3+1] = currentNorm.y;
        normalsArray[currentVertexPointer*3+2] = currentNorm.z;

    }

}
