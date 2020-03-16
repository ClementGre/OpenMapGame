package fr.themsou.renderEngine;

import fr.themsou.entities.Entity;
import fr.themsou.main.Main;
import fr.themsou.models.TexturedModel;
import fr.themsou.shaders.StaticShader;
import fr.themsou.shaders.TerrainShader;
import fr.themsou.entities.objectEntity.Terrain;
import fr.themsou.utils.Location;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MasterRenderer {

    private static final float FOV = 70;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000f;
    private Matrix4f projectionMatrix;

    private StaticShader shader = new StaticShader();
    private EntityRenderer renderer;

    private TerrainShader terrainShader = new TerrainShader();
    private TerrainRenderer terrainRenderer;

    private HashMap<TexturedModel, List<Entity>> entities = new HashMap<>();
    private List<Terrain> terrains = new ArrayList<>();

    public MasterRenderer(){

        createProjectionMatrix();
        renderer = new EntityRenderer(shader, projectionMatrix);
        terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
    }

    public static void enableCulling(){

        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
    }
    public static void disableCulling(){
        GL11.glDisable(GL11.GL_CULL_FACE);
    }

    public void render(Location renderLocation){

        prepare();

        // EntityRenderer

        shader.start();
        shader.loadSky(Main.mainLoop.sky);
        shader.loadViewMatrix(renderLocation);

        renderer.render(entities);

        shader.stop();
        entities.clear();

        // TerrainRenderer

        terrainShader.start();
        terrainShader.loadSky(Main.mainLoop.sky);
        terrainShader.loadViewMatrix(renderLocation);

        terrainRenderer.render(terrains);

        terrainShader.stop();
        terrains.clear();

    }

    public void prepare(){

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(Main.mainLoop.sky.getColour().x, Main.mainLoop.sky.getColour().y, Main.mainLoop.sky.getColour().z, 1);
    }

    public void processEntity(Entity entity){ // Ajoute une entity dans la liste des entités à rendre
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
    public void processTerrain(Terrain terrain){ // Ajoute un terrain dans la liste des terrains à rendre
        terrains.add(terrain);
    }

    private void createProjectionMatrix(){ // Initialise la matrice de ptojection

        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_lenght = FAR_PLANE - NEAR_PLANE;

        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_lenght);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_lenght);
        projectionMatrix.m33 = 0;

    }

    public void cleanUp(){ // Supprime les shaderss de la mémoire
        shader.cleanUP();
        terrainShader.cleanUP();
    }

}
