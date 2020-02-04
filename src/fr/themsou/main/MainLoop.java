package fr.themsou.main;

import fr.themsou.entities.Entity;
import fr.themsou.entities.Light;
import fr.themsou.renderEngine.DisplayManager;
import fr.themsou.renderEngine.MasterRenderer;
import fr.themsou.renderEngine.OBJLoader;
import fr.themsou.models.TexturedModel;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainLoop {

    public static List<Entity> entities = new ArrayList<>();
    public static MasterRenderer renderer = new MasterRenderer();
    public static Light light;

    public static final int IPS = 200; // Input / sec (5 ms between)
    public static final int TPS = 20; // logic / sec (50 ms between)
    public static final int MAX_FPS = 140;  // Render / sec

    public static final double IPS_TIME_MILLIS = 1d / ((double) IPS) * 1000;
    public static final double TPS_TIME_MILLIS = 1d / ((double) TPS) * 1000;
    public static final double FPS_TIME_MILLIS = 1d / ((double) MAX_FPS) * 1000;

    // COUNTERS
    public static long lastInputMillis = System.nanoTime() / 1000000;
    public static long inputIterationsCount = 0;
    public static long ips = 0;

    public static long lastLogicMillis = System.nanoTime() / 1000000;
    public static long logicIterationsCount = 0;
    public static long tps = 0;

    public static long lastRenderMillis = System.nanoTime() / 1000000;
    public static long renderIterationsCount = 0;
    public static long fps = 0;

    public static void setup(){

        light = new Light(new Vector3f(-10000f, 10000f, -10000f), new Vector3f(0.9f, 0.86f, 0.66f));

        TexturedModel model = OBJLoader.loadObjTexturedModel("stall", Main.loader);
        model.getTexture().setShineDumper(20);
        model.getTexture().setReflectivity(0.7f);

        Random r = new Random();
        for(int i = 0; i <= 100000; i++){
            Entity stall = new Entity(model, new Vector3f((r.nextFloat()-0.5f) * 150, (r.nextFloat()-0.5f) * 150, (r.nextFloat()-0.5f) * 150),
                    r.nextFloat() * 360, r.nextFloat() * 360, r.nextFloat() * 360, r.nextFloat()*0.2f);
            entities.add(stall);
        }
    }

    public static void startMainLoop(){

        setup();
        double previousMillis = System.nanoTime() / 1000000;
        double inputSteps = 0.0;
        double logicSteps = 0.0;
        while(!Display.isCloseRequested()){

            double startTimeMillis = System.nanoTime() / 1000000;
            double elapsed = startTimeMillis - previousMillis;
            previousMillis = startTimeMillis;
            inputSteps += elapsed;
            logicSteps += elapsed;

            while(inputSteps >= IPS_TIME_MILLIS-1){
                updateInputs();
                inputSteps -= IPS_TIME_MILLIS;
            }
            while(logicSteps >= TPS_TIME_MILLIS-1){
                updateLogic();
                logicSteps -= TPS_TIME_MILLIS;
            }

            updateRender();
            sync(startTimeMillis);

        }
        // CLose display
        renderer.cleanUp();
        Main.onCloseDisplay();
    }
    private static void sync(double startTimeMillis){

        double endTime = startTimeMillis + FPS_TIME_MILLIS;
        while((System.nanoTime() / 1000000) < endTime-1){
            try {
                Thread.sleep(1);
            }catch(InterruptedException ignored){}
        }
    }

    public static void updateInputs(){

        if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) Mouse.setGrabbed(false);
        if(Mouse.isButtonDown(0)) Mouse.setGrabbed(true);
        if(Mouse.isGrabbed()){
            DisplayManager.camera.updateInputs();
        }

        // COUNTER
        if(inputIterationsCount >= 100){
            long nowMillis = System.nanoTime() / 1000000;
            ips = (int) (1.0 / ((nowMillis - lastInputMillis) / 100000.0) );
            lastInputMillis = nowMillis;

            inputIterationsCount = 0;
        }
        inputIterationsCount++;

    }
    public static void updateLogic(){

        if(new Random().nextInt(10) == 0){
            DisplayManager.setTitle("OpenMapGame - " + DisplayManager.camera.getPosition() +
                    " [" + ((int)DisplayManager.camera.getPitch()) + ";" + ((int)DisplayManager.camera.getYaw()) + "] - "
                    + fps + "fps - " + tps + "tps - " + ips + "ips");
        }

        // COUNTER
        if(logicIterationsCount >= 100){
            long nowMillis = System.nanoTime() / 1000000;
            tps = (long) (1.0 / ((nowMillis - lastLogicMillis) / 100000.0) );
            lastLogicMillis = nowMillis;

            logicIterationsCount = 0;
        }
        logicIterationsCount++;

    }
    public static void updateRender(){

        for(Entity entity : entities){
            renderer.processEntity(entity);
        }
        renderer.render(light, DisplayManager.camera);

        DisplayManager.updateDisplay();

        // COUNTER
        if(renderIterationsCount >= 100){
            long nowMillis = System.nanoTime() / 1000000;
            fps = (long) (1.0 / ((nowMillis - lastRenderMillis) / 100000.0) );
            lastRenderMillis = nowMillis;

            renderIterationsCount = 0;
        }
        renderIterationsCount++;

    }

}
