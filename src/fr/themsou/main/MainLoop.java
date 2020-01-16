package fr.themsou.main;

import fr.themsou.renderEngine.DisplayManager;
import org.lwjgl.opengl.Display;

public class MainLoop {

    public static void startMainLoop(){

        while(!Display.isCloseRequested()){


            // Game logic

            // Render vertex

            DisplayManager.updateDisplay(); // Update screen ans sync FPS

        }
        DisplayManager.closeDisplay();
    }
}
