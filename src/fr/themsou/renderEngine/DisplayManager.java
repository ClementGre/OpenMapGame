package fr.themsou.renderEngine;

import fr.themsou.entities.Camera;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;

public class DisplayManager {

    private static int width = 1280;
    private static int height = 720;
    private static int fpsCap = 120;

    public static Camera camera;

    public static void createDisplay(int width, int height, int fpsCap, String title){

        if(width != 0) DisplayManager.width = width;
        if(height != 0) DisplayManager.height = height;
        if(fpsCap != 0) DisplayManager.fpsCap = fpsCap;

        ContextAttribs attribs = new ContextAttribs(3,2).withForwardCompatible(true).withProfileCore(true);

        try{

            Display.setDisplayMode(new DisplayMode(DisplayManager.width,  DisplayManager.height));
            Display.setTitle(title);
            Display.setResizable(true);
            Display.create(new PixelFormat(), attribs);


        }catch(LWJGLException e){ e.printStackTrace(); }

        glViewport(0, 0, DisplayManager.width,  DisplayManager.height);
        camera = new Camera(new Vector3f(0, 0, 0));

    }

    public static void updateDisplay(){

        Display.sync(DisplayManager.fpsCap);
        Display.update();
    }

    public static void closeDisplay(){

        Display.destroy();
    }

    public static void setTitle(String title){
        Display.setTitle(title);
    }
}
