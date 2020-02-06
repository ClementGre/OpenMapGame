package fr.themsou.main;

import fr.themsou.entities.Entity;
import fr.themsou.entities.Light;
import fr.themsou.models.TexturedModel;
import fr.themsou.renderEngine.DisplayManager;
import fr.themsou.renderEngine.MasterRenderer;
import fr.themsou.renderEngine.OBJLoader;
import fr.themsou.terrains.Terrain;
import fr.themsou.textures.ModelTexture;
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

        light = new Light(new Vector3f(-10000f, 10000f, -10000f), new Vector3f(0.9f, 0.86f, 0.66f));

        TexturedModel model = OBJLoader.loadObjTexturedModel("stall", Main.loader);
        model.getTexture().setShineDumper(20);
        model.getTexture().setReflectivity(0.7f);

        Random r = new Random();
        for(int i = 0; i <= 100; i++){
            Entity stall = new Entity(model, new Vector3f((r.nextFloat()-0.5f) * 1, (r.nextFloat()-0.5f) * 1, (r.nextFloat()-0.5f) * 1),
                    r.nextFloat() * 360, r.nextFloat() * 360, r.nextFloat() * 360, r.nextFloat()*0.5f);
            entities.add(stall);
        }

        Entity stall = new Entity(model, new Vector3f(20, 0, 20), 0, 0, 0, 1);
        entities.add(stall);

        Terrain terrain = new Terrain(0, 0, Main.loader, new ModelTexture(Main.loader.loadTexture("planks.png")));
        Terrain terrain2 = new Terrain(1, 0, Main.loader, new ModelTexture(Main.loader.loadTexture("planks.png")));
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

        if(new Random().nextInt(10) == 0){
            DisplayManager.setTitle("OpenMapGame - " + DisplayManager.camera.getPosition() +
                    " [" + ((int)DisplayManager.camera.getPitch()) + ";" + ((int)DisplayManager.camera.getYaw()) + "] - "
                    + fps + "fps - " + tps + "tps - " + ips + "ips");
        }

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
