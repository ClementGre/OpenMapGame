package fr.themsou.main;

import fr.themsou.renderEngine.DisplayManager;

public class Main {

    public static  void main(String args[]){

        DisplayManager.createDisplay(0, 0, 0, "Open Map Game");

        MainLoop.startMainLoop();

    }
}
