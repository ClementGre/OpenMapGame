package fr.themsou.renderEngine;

import fr.themsou.entities.Camera;
import fr.themsou.entities.Entity;
import fr.themsou.entities.Light;
import fr.themsou.models.TexturedModel;
import fr.themsou.shaders.StaticShader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MasterRenderer {

    private StaticShader shader = new StaticShader();
    private Renderer renderer = new Renderer(shader);

    private HashMap<TexturedModel, List<Entity>> entities = new HashMap<>();


    public void render(Light sun, Camera camera){

        renderer.prepare();
        shader.start();
        shader.loadLight(sun);
        shader.loadViewMatrix(camera);

        renderer.render(entities);

        shader.stop();
        entities.clear();

    }

    public void processEntity(Entity entity){
        TexturedModel entityModel = entity.getModel();
        List<Entity> batch = entities.get(entityModel);

        if(batch != null){
            batch.add(entity);
        }else{
            batch = new ArrayList<>();
            batch.add(entity);
            entities.put(entityModel, batch);
        }
    }

    public void cleanUp(){
        shader.cleanUP();
    }

}
