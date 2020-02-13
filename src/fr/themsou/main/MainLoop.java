package fr.themsou.main;

import fr.themsou.entities.Entity;
import fr.themsou.entities.Light;
import fr.themsou.entities.Sky;
import fr.themsou.models.RawModel;
import fr.themsou.models.textures.TerrainTexture;
import fr.themsou.models.textures.TerrainTexturePack;
import fr.themsou.models.textures.TextureSample;
import fr.themsou.models.TexturedModel;
import fr.themsou.models.objConverter.OBJFileLoader;
import fr.themsou.renderEngine.MasterRenderer;
import fr.themsou.entities.Terrain;
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

    public void setup(){

        sky = new Sky(new Vector3f(0.0f, 1f,1f), new Light(new Vector3f(800, 200, 200), new Vector3f(0.9f, 0.86f, 0.66f)));
        TexturedModel sun = OBJFileLoader.loadOBJToTexturedModel("Sun", -1);
        sun.getTexture().setReflectivity(0); sun.getTexture().setReflectivity(0);
        entities.add(new Entity(sun, sky.getSun().getPosition(), 0, 0, 0, 20));


        RawModel treeModel = OBJFileLoader.loadOBJToRawModel("Tree");
        RawModel highTreeModel = OBJFileLoader.loadOBJToRawModel("HighTree");
        RawModel grassModel = OBJFileLoader.loadOBJToRawModel("Grass");
        RawModel fernModel = OBJFileLoader.loadOBJToRawModel("Fern");

        TexturedModel tree = new TexturedModel(treeModel, Main.loader.loadModelTexture("Tree", null, TextureSample.SIMPLEOBJ_SAMPLE));
        TexturedModel highTree = new TexturedModel(highTreeModel, Main.loader.loadModelTexture("HighTree", null, TextureSample.SIMPLEOBJ_SAMPLE));
        TexturedModel grass = new TexturedModel(grassModel, Main.loader.loadModelTexture("Grass", null, TextureSample.TRANSPARENT_SAMPLE));
        TexturedModel fern = new TexturedModel(fernModel, Main.loader.loadModelTexture("Fern", null, TextureSample.TRANSPARENT_SAMPLE));

        TerrainTexture blendMapTexture = Main.loader.loadTerrainTexture("1", "blendMap.png");
        TerrainTexture backgroundTexture = Main.loader.loadTerrainTexture("1", "background.png");
        TerrainTexture rTexture = Main.loader.loadTerrainTexture("1", "r.png");
        TerrainTexture gTexture = Main.loader.loadTerrainTexture("1", "g.png");
        TerrainTexture bTexture = Main.loader.loadTerrainTexture("1", "b.png");
        TerrainTexturePack terrainTexturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);

        for(int i = 0; i < 16; i++){
            int indexX = i/4; int indexZ = i%4;
            int xStart = indexX*100; int zStart = indexZ*100;

            Random r = new Random();
            for(int k = 0; k <= 150; k++){
                Entity entity = new Entity(tree, new Vector3f(xStart + r.nextFloat() * 100, 0, zStart + r.nextFloat() * 100), 0, r.nextFloat() * 360, 0, 1f);
                entities.add(entity);
            }
            for(int k = 0; k <= 150; k++){
                Entity entity = new Entity(highTree, new Vector3f(xStart + r.nextFloat() * 100, 0, zStart + r.nextFloat() * 100), 0, r.nextFloat() * 360, 0, 0.15f);
                entities.add(entity);
            }
            for(int k = 0; k <= 150; k++){
                Entity entity = new Entity(grass, new Vector3f(xStart + r.nextFloat() * 100, 0, zStart + r.nextFloat() * 100), 0, r.nextFloat() * 360, 0, 0.4f);
                entities.add(entity);
            }
            for(int k = 0; k <= 200; k++){
                Entity entity = new Entity(fern, new Vector3f(xStart + r.nextFloat() * 100, 0, zStart + r.nextFloat() * 100), 0, r.nextFloat() * 360, 0, 0.2f);
                entities.add(entity);
            }

            Terrain terrain = new Terrain(indexX, indexZ, Main.loader, blendMapTexture, terrainTexturePack);
            terrains.add(terrain);
        }

        DisplayManager.camera.setPosition(new Vector3f(100, 1.5f, 50));
    }
    public void close(){
        renderer.cleanUp();
        Main.onCloseDisplay();
    }

    public void updateInputs(){

        if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) Mouse.setGrabbed(false);
        if(Mouse.isButtonDown(0)) Mouse.setGrabbed(true);
        if(Mouse.isGrabbed()){
            DisplayManager.camera.updateInputs();
        }

    }
    public void updateLogic(){

        DisplayManager.setTitle("OpenMapGame - " + DisplayManager.camera.getPosition() +
                " [" + ((int)DisplayManager.camera.getPitch()) + ";" + ((int)DisplayManager.camera.getYaw()) + "] - "
                + fps + "fps - " + tps + "tps - " + ips + "ips");


    }
    public void updateRender(){

        for(Terrain terrain : terrains){
            renderer.processTerrain(terrain);
        }
        for(Entity entity : entities){
            renderer.processEntity(entity);
        }
        renderer.render(DisplayManager.camera);

        DisplayManager.updateDisplay();

    }
}
