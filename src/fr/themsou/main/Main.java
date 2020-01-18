package fr.themsou.main;

import fr.themsou.renderEngine.DisplayManager;
import fr.themsou.renderEngine.Loader;
import fr.themsou.textures.ModelTexture;
import models.RawModel;
import fr.themsou.renderEngine.Renderer;
import models.TexturedModel;
import shaders.StaticShader;

public class Main {

    public static Loader loader = new Loader();
    public static Renderer renderer = new Renderer();
    public static TexturedModel texturedModel;
    public static StaticShader shader;

    public static  void main(String args[]){

        DisplayManager.createDisplay(0, 0, 0, "Open Map Game");

        shader = new StaticShader();



        float[] vertices = {
                0f, 0.5f, 0f,//v0
                0f, 0f, 0f,//v1
                0.5f, 0f, 0f,//v2
                0.5f, 0.5f, 0f,//v3
        };
        float[] textureCoords = {
                0, 0,
                0, 1,
                1, 1,
                1, 0,
        };
        int[] indices = {
                0, 1, 3, //top left triangle (v0, v1, v3)
                3, 1, 2 //bottom right triangle (v3, v1, v2)
        };

        RawModel model = loader.loadToVAO(vertices, textureCoords, indices);
        ModelTexture texture = new ModelTexture(loader.loadTexture("planks.png"));
        texturedModel = new TexturedModel(model, texture);

        MainLoop.startMainLoop();

    }

    public static void onCloseDisplay(){
        shader.cleanUP();
        loader.cleanUP();
        DisplayManager.closeDisplay();
    }
}
