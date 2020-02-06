package fr.themsou.main;

import org.lwjgl.opengl.Display;

public abstract class MainLoopManager {

    public static final int IPS = 200; // Input / sec (5 ms between)
    public static final int TPS = 20; // logic / sec (50 ms between)
    public static final int MAX_FPS = 140;  // Render / sec

    public final double IPS_TIME_MILLIS = 1d / ((double) IPS) * 1000;
    public final double TPS_TIME_MILLIS = 1d / ((double) TPS) * 1000;
    public final double FPS_TIME_MILLIS = 1d / ((double) MAX_FPS) * 1000;

    // COUNTERS
    public long lastInputMillis = System.nanoTime() / 1000000;
    public long inputIterationsCount = 0;
    public long ips = 0;

    public long lastLogicMillis = System.nanoTime() / 1000000;
    public long logicIterationsCount = 0;
    public long tps = 0;

    public long lastRenderMillis = System.nanoTime() / 1000000;
    public long renderIterationsCount = 0;
    public long fps = 0;

    public abstract void setup();
    public abstract void close();

    public void startMainLoop(){

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
                updateInputsManage();
                inputSteps -= IPS_TIME_MILLIS;
            }
            while(logicSteps >= TPS_TIME_MILLIS-1){
                updateLogicManage();
                logicSteps -= TPS_TIME_MILLIS;
            }

            updateRenderManage();
            sync(startTimeMillis);

        }
        // Close display
        close();
    }
    private void sync(double startTimeMillis){

        double endTime = startTimeMillis + FPS_TIME_MILLIS;
        while((System.nanoTime() / 1000000) < endTime){
            try {
                Thread.sleep(1);
            }catch(InterruptedException ignored){}
        }
    }

    public abstract void updateInputs();
    public void updateInputsManage(){

        updateInputs();

        if(inputIterationsCount >= 100){
            long nowMillis = System.nanoTime() / 1000000;
            ips = (int) (1.0 / ((nowMillis - lastInputMillis) / 100000.0) );
            lastInputMillis = nowMillis;

            inputIterationsCount = 0;
        }
        inputIterationsCount++;

    }
    public abstract void updateLogic();
    public void updateLogicManage(){

        updateLogic();

        if(logicIterationsCount >= 100){
            long nowMillis = System.nanoTime() / 1000000;
            tps = (long) (1.0 / ((nowMillis - lastLogicMillis) / 100000.0) );
            lastLogicMillis = nowMillis;

            logicIterationsCount = 0;
        }
        logicIterationsCount++;

    }
    public abstract void updateRender();
    public void updateRenderManage(){

        updateRender();

        if(renderIterationsCount >= 100){
            long nowMillis = System.nanoTime() / 1000000;
            fps = (long) (1.0 / ((nowMillis - lastRenderMillis) / 100000.0) );
            lastRenderMillis = nowMillis;

            renderIterationsCount = 0;
        }
        renderIterationsCount++;

    }

}
