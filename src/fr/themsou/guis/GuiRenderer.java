package fr.themsou.guis;

import fr.themsou.constants.Angle;
import fr.themsou.main.DisplayManager;
import fr.themsou.main.Main;
import fr.themsou.models.RawModel;
import fr.themsou.utils.Maths;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import java.util.List;

public class GuiRenderer {

    private final RawModel quad;
    private GuiShader shader;

    public GuiRenderer(){

        float[] positions = {-1, 1, -1, -1, 1, 1, 1, -1};
        quad = Main.loader.loadToVAO(positions);
        shader = new GuiShader();

    }

    public void render(List<GuiTexture> guis){

        shader.start();

        GL30.glBindVertexArray(quad.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        // Actiuve la transparence (canal alpha)
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        // Désactive le depth test, pour pouvoir afficher un élément par dessus un autre sans que son alpha le cache
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        for(GuiTexture gui : guis){
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, gui.getTexture());

            Vector2f[] vectors = adaptGuiPositionAndScale(gui);

            Matrix4f matrix = Maths.createTransformationMatrix(vectors[0], vectors[1]);
            shader.loadTransformation(matrix);

            GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
        }

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);

        shader.stop();
    }

    private Vector2f[] adaptGuiPositionAndScale(GuiTexture gui){

        Vector2f scale = gui.getScale();
        if(gui.isAdaptSize()){
            scale = new Vector2f(scale.getX() / 1280, scale.getY() / 720);
        }else{
            scale = new Vector2f(scale.getX() / DisplayManager.width, scale.getY() / DisplayManager.height);
        }

        Vector2f position = gui.getPosition();

        if(!gui.isAdaptPosition()){
            position = new Vector2f(position.getX() / DisplayManager.width * 1280, position.getY() / DisplayManager.height * 720);
        }

        if(gui.getOrigin() == Angle.TOP_RIGHT){
            position = new Vector2f(1280 - position.getX(), 720 - position.getY());
        }else if(gui.getOrigin() == Angle.TOP_LEFT){
            position = new Vector2f(position.getX(), 720 - position.getY());
        }else if(gui.getOrigin() == Angle.BOTTOM_RIGHT){
            position = new Vector2f(1280 - position.getX(), position.getY());
        }

        position = new Vector2f(position.getX() / 1280 * 2 -1, position.getY() / 720 * 2 -1);

        if(gui.getOrigin() == Angle.TOP_RIGHT){
            position = new Vector2f(position.getX() - scale.getX(), position.getY() - scale.getY());
        }else if(gui.getOrigin() == Angle.TOP_LEFT){
            position = new Vector2f(position.getX() + scale.getX(), position.getY() - scale.getY());
        }else if(gui.getOrigin() == Angle.BOTTOM_RIGHT){
            position = new Vector2f(position.getX() - scale.getX(), position.getY() + scale.getY());
        }else if(gui.getOrigin() == Angle.BOTTOM_LEFT){
            position = new Vector2f(position.getX() + scale.getX(), position.getY() + scale.getY());
        }

        return new Vector2f[]{position, scale};

    }

    public void cleanUp(){
        shader.cleanUP();
    }
}
