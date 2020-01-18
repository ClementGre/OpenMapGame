package fr.themsou.main;

import fr.themsou.renderEngine.DisplayManager;
import fr.themsou.renderEngine.Loader;
import fr.themsou.renderEngine.RawModel;
import fr.themsou.renderEngine.Renderer;

public class Main {

    public static Loader loader = new Loader();
    public static Renderer renderer = new Renderer();
    public static  RawModel model;

    public static  void main(String args[]){

        DisplayManager.createDisplay(0, 0, 0, "Open Map Game");

        float[] vertices = {
                -0.5f, 0.5f, 0f,//v0
                -0.5f, -0.5f, 0f,//v1
                0.5f, -0.5f, 0f,//v2
                0.5f, 0.5f, 0f,//v3
        };
        int[] indices = {
                0, 1, 3, //top left triangle (v0, v1, v3)
                3, 1, 2 //bottom right triangle (v3, v1, v2)
        };
        model = loader.loadToVAO(vertices, indices);

        MainLoop.startMainLoop();

    }

    public static void onCloseDisplay(){
        loader.cleanUP();
        DisplayManager.closeDisplay();
    }
}
