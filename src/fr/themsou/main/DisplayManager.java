package fr.themsou.main;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import static org.lwjgl.opengl.GL11.*;

public class DisplayManager {

    private static int width = 1280;
    private static int height = 720;

    public static void createDisplay(int width, int height, int fpsCap, String title){

        if(width != 0) DisplayManager.width = width;
        if(height != 0) DisplayManager.height = height;

        ContextAttribs attribs = new ContextAttribs(3,2).withForwardCompatible(true).withProfileCore(true);

        try{

            Display.setDisplayMode(new DisplayMode(DisplayManager.width,  DisplayManager.height));
            Display.setTitle(title);
            Display.setResizable(true);
            Display.create(new PixelFormat(), attribs);


        }catch(LWJGLException e){ e.printStackTrace(); }

        glViewport(0, 0, DisplayManager.width,  DisplayManager.height);

    }

    public static void updateDisplay(){
        Display.update();
        if(Display.wasResized()){
            width = Display.getWidth();
            height = Display.getHeight();
            glViewport(0, 0, width, height);
        }
    }

    public static void closeDisplay(){
        Display.destroy();
    }

    public static void setTitle(String title){
        Display.setTitle(title);
    }
}
