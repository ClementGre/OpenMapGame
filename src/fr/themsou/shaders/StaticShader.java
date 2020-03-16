package fr.themsou.shaders;

import fr.themsou.entities.objectEntity.Sky;
import fr.themsou.models.textures.ModelTexture;
import fr.themsou.utils.Location;
import fr.themsou.utils.Maths;
import org.lwjgl.util.vector.Matrix4f;

public class StaticShader extends  ShaderProgram {

    private static final String VERTEX_FILE = "src/fr/themsou/shaders/vertexShader.glsl";
    private static final String FRAGMENT_FILE = "src/fr/themsou/shaders/fragmentShader.glsl";

    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_lightPosition;
    private int location_lightColour;
    private int location_shineDamper;
    private int location_reflectivity;
    private int location_useFakeLightning;
    private int location_useLightningShader;
    private int location_skyColour;

    public StaticShader(){
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
        location_useFakeLightning = super.getUniformLocation("useFakeLightning");
        location_useLightningShader = super.getUniformLocation("useLightningShader");
        location_skyColour = super.getUniformLocation("skyColour");
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
        super.loadVector(location_lightColour, sky.getSun().getColour());
        super.loadVector(location_lightPosition, sky.getSun().getLocation().toVector());
        super.loadVector(location_skyColour, sky.getColour());
    }

    public void loadModelTextureSettings(ModelTexture texture){
        super.loadBoolean(location_useFakeLightning, texture.isUseFakeLightning());
        super.loadFloat(location_shineDamper, texture.getShineDumper());
        super.loadFloat(location_reflectivity, texture.getReflectivity());
        super.loadBoolean(location_useLightningShader, !(texture.getShineDumper() == 0 && texture.getReflectivity() == 0));
    }
}
