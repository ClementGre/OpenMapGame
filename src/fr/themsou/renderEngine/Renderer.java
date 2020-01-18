package fr.themsou.renderEngine;

import models.RawModel;
import models.TexturedModel;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.TextureLoader;

public class Renderer {

    public void prepare(){

        GL11.glClearColor(1, 0, 0, 1);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

    }

    public void render(TexturedModel texturedModel){

        RawModel model = texturedModel.getRawModel();

        GL30.glBindVertexArray(model.getVaoID()); // Bind le VAO
            GL20.glEnableVertexAttribArray(0); // Bind le VBO de la position
            GL20.glEnableVertexAttribArray(1); // Bind le VBO de la position de la texture

                GL13.glActiveTexture(GL13.GL_TEXTURE0);
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getID());
                GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0); // Dessiner à l'écran les vertex du VBO

            GL20.glDisableVertexAttribArray(1);
            GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);

    }
}
