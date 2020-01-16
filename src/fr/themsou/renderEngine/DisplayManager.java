package fr.themsou.renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import static org.lwjgl.opengl.GL11.*;

public class DisplayManager {

    private static int width = 1280;
    private static int height = 720;
    private static int fpsCap = 120;


    public static void createDisplay(int width, int height, int fpsCap, String title){

        if(width != 0) DisplayManager.width = width;
        if(height != 0) DisplayManager.height = height;
        if(fpsCap != 0) DisplayManager.fpsCap = fpsCap;

        ContextAttribs attribs = new ContextAttribs(3,2);
        attribs.withForwardCompatible(true);
        attribs.withProfileCore(true);

        try{

            Display.setDisplayMode(new DisplayMode(DisplayManager.width, DisplayManager.height));
            Display.setTitle(title);
            Display.setResizable(true);
            Display.create(new PixelFormat(), attribs);

            glEnable(GL_DEPTH_TEST);

        }catch(LWJGLException e){ e.printStackTrace(); }

        glViewport(0, 0, DisplayManager.width,  DisplayManager.height);

    }

    public static void updateDisplay(){

        Display.sync(DisplayManager.fpsCap);
        Display.update();
    }

    public static void clearBuffers(){

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public static boolean isClosed(){

        return Display.isCloseRequested();
    }

    public static void closeDisplay(){

        Display.destroy();
    }
}
