package fr.themsou.renderEngine;

import fr.themsou.entities.Entity;
import fr.themsou.models.textures.ModelTexture;
import fr.themsou.utils.Maths;
import fr.themsou.models.RawModel;
import fr.themsou.models.TexturedModel;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Matrix4f;
import fr.themsou.shaders.StaticShader;

import java.util.List;
import java.util.Map;

public class EntityRenderer {

    private StaticShader shader;

    public EntityRenderer(StaticShader shader, Matrix4f projectionMatrix){

        this.shader = shader;

        shader.start();
            shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }

    public void render(Map<TexturedModel, List<Entity>> entities){

        for(TexturedModel model : entities.keySet()){
            prepareTexturedModel(model); // Bind tous les VBO et les variables de shaders du model du groupe d'entité
            List<Entity> batch = entities.get(model);

            for(Entity entity : batch){
                loadModelMatrix(entity); // Charge la translation matrix de l'entity
                GL11.glDrawElements(GL11.GL_TRIANGLES, entity.getModel().getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0); // Dessiner à l'écran les vertex du VBO
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
        if(texture.isHasTransparency()){
            MasterRenderer.disableCulling();
        }else MasterRenderer.enableCulling();
        // SHADER Settings

        shader.loadModelTextureSettings(texture);

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getID());

    }

    public void unbindTexturedModel(){

        GL20.glDisableVertexAttribArray(2);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
    }

    public void loadModelMatrix(Entity entity){

        Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getLocation().toVector(), entity.getLocation().getPitch(), entity.getLocation().getYaw(), entity.getLocation().getRoll(), entity.getScale());
        shader.loadTransformationMatrix(transformationMatrix); // Charge la transformationMatrix de l'entity dans le shader

    }
}
