package fr.themsou.renderEngine;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class Loader {

    private List<Integer> vaos = new ArrayList<>();
    private List<Integer> vbos = new ArrayList<>();

    public RawModel loadToVAO(float[] positions, int[] indices){

        int vaoID = createAndBindVAO();
        bindIndicesBuffer(indices);
        storeDataInAttributeList(0, positions);
        unbindVAO();

        return new RawModel(vaoID, indices.length);

    }

    private void storeDataInAttributeList(int attributeNumber, float[] data){

        int vboID = GL15.glGenBuffers(); // générer le vVBO
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID); // bind le VBO

        FloatBuffer buffer = storeDataInFloatBuffer(data); // Traduire la data en data enregistrable dans le VBO
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW); // Initialiser le contenu du VBO
        GL20.glVertexAttribPointer(attributeNumber, 3, GL11.GL_FLOAT, false, 0, 0); // Ajouter le VBO dans le VAO

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0); // unbind le VBO

    }

    private int createAndBindVAO(){
        int vaoID = GL30.glGenVertexArrays();
        vaos.add(vaoID);
        GL30.glBindVertexArray(vaoID);
        return vaoID;
    }
    private void unbindVAO(){
        GL30.glBindVertexArray(0);
    }

    private void bindIndicesBuffer(int[] indices){

        // Les VAOs contiennent un "slot spécial" pour les index vbo, le fait de bind un vbo "d'index"
        // le bind automatiquement au VAO courrant. C'est pourquoi on n'ajoute pas le vbo dans le VAO
        // et qu'on ne dé-bind pas le vbo

        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);

        IntBuffer buffer = storeDataInIntBuffer(indices); // Traduire la data en data enregistrable dans le VBO
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW); // Initialiser le contenu du VBO (Puis il se bind automatiquement au VAO)

    }

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

    public void cleanUP(){

        for(int vaoID : vaos){
            GL30.glDeleteVertexArrays(vaoID);
        }
        for(int vboID : vbos){
            GL15.glDeleteBuffers(vboID);
        }

    }
}
