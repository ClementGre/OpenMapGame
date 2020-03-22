package fr.themsou.main;

import fr.themsou.entities.*;
import fr.themsou.entities.livingEntity.ocularEntity.Player;
import fr.themsou.entities.objectEntity.Light;
import fr.themsou.entities.objectEntity.Sky;
import fr.themsou.entities.objectEntity.Terrain;
import fr.themsou.models.RawModel;
import fr.themsou.models.textures.TerrainTexture;
import fr.themsou.models.textures.TerrainTexturePack;
import fr.themsou.models.textures.TextureSample;
import fr.themsou.models.TexturedModel;
import fr.themsou.models.objConverter.OBJFileLoader;
import fr.themsou.renderEngine.MasterRenderer;
import fr.themsou.utils.Location;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainLoop extends MainLoopManager {

    public List<Entity> entities = new ArrayList<>();
    public List<Terrain> terrains = new ArrayList<>();

    public MasterRenderer renderer = new MasterRenderer();
    public Sky sky;

    public Player player;

    public void setup(){

        TexturedModel sun = OBJFileLoader.loadOBJToTexturedModel("Sun", -1, 1);
        sun.getTexture().setReflectivity(0); sun.getTexture().setReflectivity(0);
        sky = new Sky(new Vector3f(0.0f, 1f,1f), new Light(sun, new Location(800, 200, 200), new Vector3f(0.9f, 0.86f, 0.66f), 40f));
        entities.add(sky.getSun());

        RawModel playerModel = OBJFileLoader.loadOBJToRawModel("Player");
        TexturedModel playerTexturedModel = new TexturedModel(playerModel, Main.loader.loadModelTexture("Player", null, TextureSample.SIMPLEOBJ_SAMPLE, 1));

        player = new Player(playerTexturedModel, new Location(100, 0f, 50), 0.2f);
        entities.add(player);

        RawModel treeModel = OBJFileLoader.loadOBJToRawModel("Tree");
        RawModel highTreeModel = OBJFileLoader.loadOBJToRawModel("HighTree");
        RawModel grassModel = OBJFileLoader.loadOBJToRawModel("Grass");
        RawModel fernModel = OBJFileLoader.loadOBJToRawModel("Fern");

        TexturedModel tree = new TexturedModel(treeModel, Main.loader.loadModelTexture("Tree", null, TextureSample.SIMPLEOBJ_SAMPLE, 1));
        TexturedModel highTree = new TexturedModel(highTreeModel, Main.loader.loadModelTexture("HighTree", null, TextureSample.SIMPLEOBJ_SAMPLE, 2));
        TexturedModel grass = new TexturedModel(grassModel, Main.loader.loadModelTexture("Grass", null, TextureSample.TRANSPARENT_SAMPLE, 4));
        TexturedModel fern = new TexturedModel(fernModel, Main.loader.loadModelTexture("Fern", null, TextureSample.TRANSPARENT_SAMPLE, 2));

        RawModel boxModel = OBJFileLoader.loadOBJToRawModel("Box");
        TexturedModel box = new TexturedModel(boxModel, Main.loader.loadModelTexture("Box", null, TextureSample.SIMPLEOBJ_SAMPLE, 1));

        TerrainTexture blendMapTexture = Main.loader.loadTerrainTexture("1", "blendMap.png");
        String heightMapTexture = Main.loader.getTerrainFileName("1", "heightMap.png");
        TerrainTexture backgroundTexture = Main.loader.loadTerrainTexture("1", "background.png");
        TerrainTexture rTexture = Main.loader.loadTerrainTexture("1", "r.png");
        TerrainTexture gTexture = Main.loader.loadTerrainTexture("1", "g.png");
        TerrainTexture bTexture = Main.loader.loadTerrainTexture("1", "b.png");
        TerrainTexturePack terrainTexturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);

        for(int i = 0; i < 16; i++){
            int indexX = i/4; int indexZ = i%4;
            int xStart = indexX*100; int zStart = indexZ*100;

            Terrain terrain = new Terrain(indexX, indexZ, Main.loader, blendMapTexture, heightMapTexture, terrainTexturePack);
            terrains.add(terrain);

            Random r = new Random();
            for(int k = 0; k <= 50; k++){
                float x = xStart + r.nextFloat() * 100;
                float z = zStart + r.nextFloat() * 100;
                Entity entity = new Entity(tree, new Location(x, terrain.getHeight(x, z), z, r.nextFloat() * 360, 0, 0), 1f);
                entities.add(entity);
            }
            for(int k = 0; k <= 50; k++){
                float x = xStart + r.nextFloat() * 100;
                float z = zStart + r.nextFloat() * 100;
                Entity entity = new Entity(highTree, r.nextInt(4), new Location(x, terrain.getHeight(x, z), z, r.nextFloat() * 360, 0, 0), 0.15f);
                entities.add(entity);
            }
            for(int k = 0; k <= 50; k++){
                float x = xStart + r.nextFloat() * 100;
                float z = zStart + r.nextFloat() * 100;
                Entity entity = new Entity(grass, r.nextInt(9), new Location(x, terrain.getHeight(x, z), z, r.nextFloat() * 360, 0, 0), 0.4f);
                entities.add(entity);
            }
            for(int k = 0; k <= 50; k++){
                float x = xStart + r.nextFloat() * 100;
                float z = zStart + r.nextFloat() * 100;
                Entity entity = new Entity(fern, r.nextInt(4), new Location(x, terrain.getHeight(x, z), z, r.nextFloat() * 360, 0, 0), 0.2f);
                entities.add(entity);
            }
            for(int k = 0; k <= 2; k++){
                float x = xStart + r.nextFloat() * 100;
                float z = zStart + r.nextFloat() * 100;
                Entity entity = new Entity(box, new Location(x, terrain.getHeight(x, z)+1, z, r.nextFloat() * 360, 0, 0), 1f);
                entities.add(entity);
            }
        }

    }
    public void close(){
        renderer.cleanUp();
        Main.onCloseDisplay();
    }

    public void updateInputs(){

        if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) Mouse.setGrabbed(false);
        if(Mouse.isButtonDown(0)) Mouse.setGrabbed(true);
        if(Mouse.isGrabbed()){
            player.updateInputs();
        }

    }
    public void updateLogic(){

            DisplayManager.setTitle("OpenMapGame - " +
                    "[" + ((int)player.getLocation().getX()) + ";" + ((int)player.getLocation().getY()) + ";" + ((int)player.getLocation().getZ()) + "]" +
                    " [" + ((int)player.getLocation().getYaw()) + ";" + ((int)player.getViewPitch()) + ";" + ((int)player.getLocation().getRoll()) + "] - "
                    + fps + "fps - " + tps + "tps - " + ips + "ips - ViewMode=" + player.getViewMode());

    }
    public void updateRender(){

        for(Terrain terrain : terrains){
            renderer.processTerrain(terrain);
        }
        for(Entity entity : entities){
            renderer.processEntity(entity);
        }

        renderer.render(player.getViewLocation());

        DisplayManager.updateDisplay();



    }
}
