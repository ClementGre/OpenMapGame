package fr.themsou.main;

import fr.themsou.entities.Entity;
import fr.themsou.entities.Light;
import fr.themsou.renderEngine.DisplayManager;
import fr.themsou.renderEngine.OBJLoader;
import fr.themsou.models.TexturedModel;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

public class MainLoop {

    public static void startMainLoop(){

        // SETUP ENTYTIES

        TexturedModel model = OBJLoader.loadObjTexturedModel("stall", Main.loader);
        model.getTexture().setShineDumper(10);
        model.getTexture().setReflectivity(1);
        Entity entity = new Entity(model, new Vector3f(0, 0, 0), 0, 0, 0, 1f);

        Light light = new Light(new Vector3f(-10000f, 10000f, -10000f), new Vector3f(0.9f, 0.86f, 0.66f));

        // BOUCLE PRINCIPALE DU JEU
        while(!Display.isCloseRequested()){

            // Game logic
            if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) Mouse.setGrabbed(false);
            if(Mouse.isButtonDown(0)) Mouse.setGrabbed(true);
            if(Mouse.isGrabbed()){
                DisplayManager.camera.updateInputs();
            }
            DisplayManager.setTitle("OpenMapGame - " + DisplayManager.camera.getPosition() + " [" + DisplayManager.camera.getPitch() + ";" + DisplayManager.camera.getYaw() + "]");

            //entity.increaseRotation(0, 0.5f, 0);

            // Render vertex
            Main.renderer.prepare(); // Clear last screen

            Main.shader.start(); // Bind le StaticShader sauvegardé dans Main

                Main.shader.loadLight(light);
                Main.shader.loadViewMatrix(DisplayManager.camera);
                Main.renderer.render(entity, Main.shader);

            Main.shader.stop(); // Unind le StaticShader sauvegardé dans Main


            // Update screen ans sync FPS
            DisplayManager.updateDisplay();

        }
        // CLose display
        Main.onCloseDisplay();
    }
}
