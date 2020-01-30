package fr.themsou.main;

import fr.themsou.renderEngine.DisplayManager;
import fr.themsou.renderEngine.Loader;
import fr.themsou.renderEngine.Renderer;
import fr.themsou.shaders.StaticShader;

public class Main {

    public static Loader loader = new Loader(); // Classe instancié une seul fois, elle permet de créer les RawModel et les ModelTexture

    public static StaticShader shader;
    public static Renderer renderer;

    public static  void main(String args[]){

        DisplayManager.createDisplay(0, 0, 0, "Open Map Game");

        shader = new StaticShader();
        renderer = new Renderer(shader);

        MainLoop.startMainLoop();

    }

    public static void onCloseDisplay(){
        shader.cleanUP();
        loader.cleanUP();
        DisplayManager.closeDisplay();
    }
}
