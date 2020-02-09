package fr.themsou.shaders;

import fr.themsou.entities.Camera;
import fr.themsou.entities.Sky;
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
    }

/////////////// Charge les variables dans les variable uniform dont on a définis les ID plus haut ///////////////

    public void loadTransformationMatrix(Matrix4f matrix){
        super.loadMatrix(location_transformationMatrix, matrix);
    }
    public void loadProjectionMatrix(Matrix4f matrix){
        super.loadMatrix(location_projectionMatrix, matrix);
    }
    public void loadViewMatrix(Camera camera){
        super.loadMatrix(location_viewMatrix, Maths.createViewMatrix(camera));
    }

    public void loadSky(Sky sky){
        super.loadVector(location_lightColour, sky.getSun().getColour());
        super.loadVector(location_lightPosition, sky.getSun().getPosition());
        super.loadVector(location_skyColour, sky.getColour());
    }

    public void loadShineVariables(float damper, float reflectivity){
        super.loadFloat(location_shineDamper, damper);
        super.loadFloat(location_reflectivity, reflectivity);
    }
}
