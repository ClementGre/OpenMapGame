package fr.themsou.renderEngine;

import fr.themsou.models.RawModel;
import fr.themsou.shaders.TerrainShader;
import fr.themsou.entities.Terrain;
import fr.themsou.models.ModelTexture;
import fr.themsou.utils.Maths;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.util.List;

public class TerrainRenderer {

    private TerrainShader shader;

    public TerrainRenderer(TerrainShader shader, Matrix4f projectionMatrix){

        this.shader = shader;

        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }

    public void render(List<Terrain> terrains){

        for(Terrain terrain : terrains){

            prepareTerrain(terrain); // Bind tous les VBO et setup les shaders
            loadModelMatrix(terrain); // Charge la matrice de translation du terrain

            GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0); // Dessiner à l'écran les vertex du VBO

            unbindTexturedModel(); // unbind les VBO
        }

    }

    public void prepareTerrain(Terrain terrain){

        RawModel model = terrain.getModel();

        GL30.glBindVertexArray(model.getVaoID()); // Bind le VAO
        GL20.glEnableVertexAttribArray(0); // Bind le VBO de la position
        GL20.glEnableVertexAttribArray(1); // Bind le VBO de la position de la texture
        GL20.glEnableVertexAttribArray(2); // Bind le VBO des normales

        ModelTexture texture = terrain.getTexture();
        if(texture.isHasTransparency()){
            MasterRenderer.disableCulling();
        }else MasterRenderer.enableCulling();
        shader.loadShineVariables(texture.getShineDumper(), texture.getReflectivity());

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getID());

    }

    public void unbindTexturedModel(){

        GL20.glDisableVertexAttribArray(2);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
    }

    public void loadModelMatrix(Terrain terrain){
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(new Vector3f(terrain.getX(), terrain.getY(), terrain.getZ()), 0, 0, 0, 1);
        shader.loadTransformationMatrix(transformationMatrix); // Charge la transformationMatrix de l'entity dans le shader
    }
}
