package shaders;

import fr.themsou.utils.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

public abstract class ShaderProgram {

    private int programID;
    private int vertexShaderID;
    private int fragmentShaderID;

    private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

    public ShaderProgram(String vertexFile, String fragmentFile){ // Setup le shaderProgramm avec le vertexShader et le FragmentShader

        vertexShaderID = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
        fragmentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);

        programID = GL20.glCreateProgram();
        GL20.glAttachShader(programID, vertexShaderID);
        GL20.glAttachShader(programID, fragmentShaderID);

        bindAttributes();

        GL20.glLinkProgram(programID);
        GL20.glValidateProgram(programID);

        getAllUniformLocations();

    }

////////////////// BIND DES SLOT DE VBO A DES NOMS DE VARIABLES DE SHADER /////////////////

    protected abstract void bindAttributes(); // Demande à la sous-classe de bind les donnés des VBO
    protected void bindAttribute(int attribute, String variableName){ // Bind les donnés d'un VBO sur un nom de variable de shader
        GL20.glBindAttribLocation(programID, attribute, variableName);
    }

//////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////// GESTION DES VARIABLES UNIFORM /////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////


    protected abstract void getAllUniformLocations(); // Demande à la sous-classe de setup ses id de variables uniform (Appelé dans le constructeur de ShaderProgram)
    protected int getUniformLocation(String uniformName){ // Renvoie l'id d'un nom de variable uniform
        return GL20.glGetUniformLocation(programID, uniformName);
    }

/////////////// CHARGER DES VALEURS DANS DES VARIABLES UNIFORM DE SHADERS (à partir de l'id de la variable) ///////////////

    protected void loadFloat(int location, float value){
        GL20.glUniform1f(location, value);
    }
    protected void loadVector(int location, Vector3f vector){
        GL20.glUniform3f(location, vector.getX(), vector.getY(), vector.getZ());
    }
    protected void loadBoolean(int location, boolean value){
        float toLoad = value ? 1 : 0;
        GL20.glUniform1f(location, toLoad);
    }
    protected void loadMatrix(int location, Matrix4f matrix){
        matrix.store(matrixBuffer);
        matrixBuffer.flip();
        GL20.glUniformMatrix4(location, false, matrixBuffer);
    }

//////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////


    private static int loadShader(String file, int type){ // Charge un fichier de shader, le compile et renvoie son ID

        StringBuilder shaderSource = new StringBuilder();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while((line = reader.readLine())!=null){
                shaderSource.append(line).append("//\n");
            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
            System.exit(-1);
        }
        int shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);
        if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS )== GL11.GL_FALSE){
            System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
            System.err.println("Could not compile shader!");
            System.exit(-1);
        }
        return shaderID;
    }

////////////////////////////// GESTION GENERALE DU SHADER /////////////////////////////

    public void start(){
        GL20.glUseProgram(programID);
    } // Bind le Shader (Appelé lors du rendu)
    public void stop(){
        GL20.glUseProgram(0);
    } // Unbind le shader

    public void cleanUP(){ // Supprime les shaders du program et supprime aussi le program
        stop();
        GL20.glDetachShader(programID, vertexShaderID);
        GL20.glDetachShader(programID, fragmentShaderID);
        GL20.glDeleteShader(vertexShaderID);
        GL20.glDeleteShader(fragmentShaderID);
        GL20.glDeleteProgram(programID);
    }

}
