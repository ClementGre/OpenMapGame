package fr.themsou.textures;

public class ModelTexture {

    private int textureID;
    private float shineDumper = 1;
    private float reflectivity = 0;

    private boolean hasTransparency = false;
    private boolean useFakeLightning = false;

    public ModelTexture(int id){
        this.textureID = id;
    }

    public ModelTexture(int id, boolean hasTransparency){
        this.textureID = id;
        this.hasTransparency = hasTransparency;
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
    public boolean isHasTransparency() {
        return hasTransparency;
    }
    public void setHasTransparency(boolean hasTransparency) {
        this.hasTransparency = hasTransparency;
    }
    public boolean isUseFakeLightning() {
        return useFakeLightning;
    }
    public void setUseFakeLightning(boolean useFakeLightning) {
        this.useFakeLightning = useFakeLightning;
    }
}
