package fr.themsou.models.textures;

public class ModelTexture {

    private int textureID;
    private float shineDumper = 0;
    private float reflectivity = 0;

    private boolean hasTransparency = false;
    private boolean useFakeLightning = false;

    private int numberOfRown = 1;

    public ModelTexture(int id, int sample, int numberOfRows){
        this.textureID = id;
        this.numberOfRown = numberOfRows;
        TextureSample.applySample(this, sample);
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
    public int getNumberOfRown() {
        return numberOfRown;
    }
    public void setNumberOfRown(int numberOfRown) {
        this.numberOfRown = numberOfRown;
    }
}
