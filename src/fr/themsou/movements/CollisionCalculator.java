package fr.themsou.movements;

import fr.themsou.entities.objectEntity.Terrain;
import fr.themsou.main.Main;

public class CollisionCalculator {

    public static boolean canAppear(){

        return true;
    }

    public static float getTerrainHeight(float x, float z){

        for(Terrain terrain : Main.mainLoop.terrains){
            if(terrain.isIn(x, z)){
                return terrain.getHeight(x, z);
            }
        }
        return 0;
    }

}
