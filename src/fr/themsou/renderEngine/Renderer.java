package fr.themsou.renderEngine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Renderer {

    public void prepare(){

        GL11.glClearColor(1, 0, 0, 1);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

    }

    public void render(RawModel model){

        GL30.glBindVertexArray(model.getVaoID()); // Bind le VAO
            GL20.glEnableVertexAttribArray(0); // Bind le VBO

                GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0); // Dessiner à l'écran les vertex du VBO

            GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);

    }
}
