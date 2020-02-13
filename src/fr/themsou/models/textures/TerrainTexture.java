package fr.themsou.models.textures;

public class TerrainTexture {

    private int textureID;
    private float shineDumper = 0;
    private float reflectivity = 0;

    public TerrainTexture(int id){
        this.textureID = id;
    }

    public int getID(){
        return this.textureID;
    }
    public float getShineDumper() {
        return shineDumper;
    }
    public void setShineDumper(float shineDumper) {
        this.shineDumper = shineDumper;
    }
    public float getReflectivity() {
        return reflectivity;
    }
    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }
}
