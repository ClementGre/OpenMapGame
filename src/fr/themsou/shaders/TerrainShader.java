package fr.themsou.shaders;

import fr.themsou.entities.objectEntity.Sky;
import fr.themsou.utils.Location;
import fr.themsou.utils.Maths;
import org.lwjgl.util.vector.Matrix4f;

public class TerrainShader extends  ShaderProgram {

    private static final String VERTEX_FILE = "src/fr/themsou/shaders/terrainVertexShader.glsl";
    private static final String FRAGMENT_FILE = "src/fr/themsou/shaders/terrainFragmentShader.glsl";

    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_lightPosition;
    private int location_lightColour;
    private int location_shineDamper;
    private int location_reflectivity;
    private int location_skyColour;
    private int location_blendMapTexture;
    private int location_backgroundTexture;
    private int location_rTexture;
    private int location_gTexture;
    private int location_bTexture;

    public TerrainShader(){
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    // Définis pour tous les VAO utilisés avec ce shader, des variables GLSL qui correspondent a un slot du VAO.
    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoord");
        super.bindAttribute(2, "normal");
    }

    @Override
    protected void getAllUniformLocations() { // setup les id des variables uniform

        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_lightPosition = super.getUniformLocation("lightPosition");
        location_lightColour = super.getUniformLocation("lightColour");
        location_shineDamper = super.getUniformLocation("shineDamper");
        location_reflectivity = super.getUniformLocation("reflectivity");
        location_skyColour = super.getUniformLocation("skyColour");
        location_blendMapTexture = super.getUniformLocation("blendMapTexture");
        location_backgroundTexture = super.getUniformLocation("backgroundTexture");
        location_rTexture = super.getUniformLocation("rTexture");
        location_gTexture = super.getUniformLocation("gTexture");
        location_bTexture = super.getUniformLocation("bTexture");
    }

/////////////// Charge les variables dans les variable uniform dont on a définis les ID plus haut ///////////////

    public void loadTransformationMatrix(Matrix4f matrix){
        super.loadMatrix(location_transformationMatrix, matrix);
    }
    public void loadProjectionMatrix(Matrix4f matrix){
        super.loadMatrix(location_projectionMatrix, matrix);
    }
    public void loadViewMatrix(Location location){
        super.loadMatrix(location_viewMatrix, Maths.createViewMatrix(location));
    }

    public void loadSky(Sky sky){
        super.load3dVector(location_lightColour, sky.getSun().getColour());
        super.load3dVector(location_lightPosition, sky.getSun().getLocation().toVector());
        super.load3dVector(location_skyColour, sky.getColour());
    }

    public void loadShineVariables(float damper, float reflectivity){
        super.loadFloat(location_shineDamper, damper);
        super.loadFloat(location_reflectivity, reflectivity);
    }

    public void loadTextures(){
        super.loadInt(location_blendMapTexture, 0);
        super.loadInt(location_backgroundTexture, 1);
        super.loadInt(location_rTexture, 2);
        super.loadInt(location_gTexture, 3);
        super.loadInt(location_bTexture, 4);
    }
}
