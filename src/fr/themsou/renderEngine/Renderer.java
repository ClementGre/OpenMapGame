package fr.themsou.renderEngine;

import fr.themsou.entities.Entity;
import fr.themsou.textures.ModelTexture;
import fr.themsou.utils.Maths;
import fr.themsou.models.RawModel;
import fr.themsou.models.TexturedModel;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Matrix4f;
import fr.themsou.shaders.StaticShader;

import java.util.List;
import java.util.Map;

public class Renderer {

    private static final float FOV = 70;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000f;

    private Matrix4f projectionMatrix;
    private StaticShader shader;

    public Renderer(StaticShader shader){

        this.shader = shader;

        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);

        createProjectionMatrix();
        shader.start();
            shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }

    public void prepare(){

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(0.2f, 0.2f,0.2f, 1);

    }

    public void render(Map<TexturedModel, List<Entity>> entities){

        for(TexturedModel model : entities.keySet()){
            prepareTexturedModel(model);
            List<Entity> batch = entities.get(model);

            for(Entity entity : batch){
                prepareInstance(entity);
            }
            unbindTexturedModel();
        }

    }

    public void prepareTexturedModel(TexturedModel texturedModel){

        RawModel model = texturedModel.getRawModel();

        GL30.glBindVertexArray(model.getVaoID()); // Bind le VAO
        GL20.glEnableVertexAttribArray(0); // Bind le VBO de la position
        GL20.glEnableVertexAttribArray(1); // Bind le VBO de la position de la texture
        GL20.glEnableVertexAttribArray(2); // Bind le VBO des normales

        ModelTexture texture = texturedModel.getTexture();
        shader.loadShineVariables(texture.getShineDumper(), texture.getReflectivity());

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getID());

    }

    public void unbindTexturedModel(){

        GL20.glDisableVertexAttribArray(2);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
    }

    public void prepareInstance(Entity entity){

        Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
        shader.loadTransformationMatrix(transformationMatrix); // Charge la transformationMatrix de l'entity dans le shader
        GL11.glDrawElements(GL11.GL_TRIANGLES, entity.getModel().getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0); // Dessiner à l'écran les vertex du VBO

    }

    private void createProjectionMatrix(){ // Initialise la matrice de ptojection

        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_lenght = FAR_PLANE - NEAR_PLANE;

        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_lenght);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_lenght);
        projectionMatrix.m33 = 0;

    }
}
