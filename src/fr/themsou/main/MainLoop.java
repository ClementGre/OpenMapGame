package fr.themsou.main;

import fr.themsou.entities.Entity;
import fr.themsou.entities.Light;
import fr.themsou.models.TexturedModel;
import fr.themsou.renderEngine.DisplayManager;
import fr.themsou.renderEngine.MasterRenderer;
import fr.themsou.renderEngine.OBJLoader;
import fr.themsou.terrains.Terrain;
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
    public Light light;

    public void setup(){

        light = new Light(new Vector3f(800, 200, 200), new Vector3f(0.9f, 0.86f, 0.66f));
        DisplayManager.camera.setPosition(new Vector3f(100, 1.5f, 50));

        TexturedModel sun = OBJLoader.loadObjTexturedModel("Sun", Main.loader, 0, 0);
        entities.add(new Entity(sun, light.getPosition(), 0, 0, 0, 20));

        TexturedModel tree = OBJLoader.loadObjTexturedModel("Tree", Main.loader);
        TexturedModel highTree = OBJLoader.loadObjTexturedModel("HighTree", Main.loader);

        TexturedModel grass = OBJLoader.loadObjTexturedModel("Grass", Main.loader, 40, 0.2f);
        grass.getTexture().setUseFakeLightning(true);
        grass.getTexture().setHasTransparency(true);
        TexturedModel fern = OBJLoader.loadObjTexturedModel("Fern", Main.loader, 40, 0.2f);
        fern.getTexture().setUseFakeLightning(true);
        fern.getTexture().setHasTransparency(true);

        Random r = new Random();
        for(int i = 0; i <= 150; i++){
            Entity entity = new Entity(tree, new Vector3f(10 + r.nextFloat() * 180, 0, 10 + r.nextFloat() * 80), 0, r.nextFloat() * 360, 0, 1f);
            entities.add(entity);
        }
        for(int i = 0; i <= 150; i++){
            Entity entity = new Entity(highTree, new Vector3f(10 + r.nextFloat() * 180, 0, 10 + r.nextFloat() * 80), 0, r.nextFloat() * 360, 0, 0.15f);
            entities.add(entity);
        }
        for(int i = 0; i <= 150; i++){
            Entity entity = new Entity(grass, new Vector3f(10 + r.nextFloat() * 180, 0, 10 + r.nextFloat() * 80), 0, r.nextFloat() * 360, 0, 0.4f);
            entities.add(entity);
        }
        for(int i = 0; i <= 200; i++){
            Entity entity = new Entity(fern, new Vector3f(10 + r.nextFloat() * 180, 0, 10 + r.nextFloat() * 80), 0, r.nextFloat() * 360, 0, 0.2f);
            entities.add(entity);
        }

        Terrain terrain = new Terrain(0, 0, Main.loader, Main.loader.loadTexture("grass.png"));
        Terrain terrain2 = new Terrain(1, 0, Main.loader, Main.loader.loadTexture("grass.png"));
        terrains.add(terrain);
        terrains.add(terrain2);

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
        renderer.render(light, DisplayManager.camera);

        DisplayManager.updateDisplay();

    }
}
