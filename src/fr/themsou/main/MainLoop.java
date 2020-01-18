package fr.themsou.main;

import fr.themsou.renderEngine.DisplayManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class MainLoop {

    public static void startMainLoop(){

        while(!Display.isCloseRequested()){

            // Game logic
            if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) Mouse.setGrabbed(false);
            if(Mouse.isButtonDown(0)) Mouse.setGrabbed(true);
            if(Mouse.isGrabbed()){
                DisplayManager.camera.input();
            }
            DisplayManager.setTitle("OpenMapGame - " + DisplayManager.camera.getPosition());


            // Render vertex
            Main.renderer.prepare(); // Clear last screen

            Main.shader.start(); // Bind le StaticShader sauvegardé dans Main

                //DisplayManager.camera.getPerspectiveProjection();
                //DisplayManager.camera.update();

                Main.renderer.render(Main.texturedModel); // Fais le rendu du TexturedModel sauvegardé dans Main

            Main.shader.stop(); // Unind le StaticShader sauvegardé dans Main


            // Update screen ans sync FPS
            DisplayManager.updateDisplay();

        }
        // CLose display
        Main.onCloseDisplay();
    }
}
