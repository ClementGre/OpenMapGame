package shaders;

import fr.themsou.entities.Camera;
import fr.themsou.utils.Maths;
import org.lwjgl.util.vector.Matrix4f;

public class StaticShader extends  ShaderProgram {

    private static final String VERTEX_FILE = "src/shaders/vertexShader.glsl";
    private static final String FRAGMENT_FILE = "src/shaders/fragmentShader.glsl";

    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;

    public StaticShader(){
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
    }

    @Override
    protected void getAllUniformLocations() { // setup les id des variables uniform

        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
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
}
