package fr.themsou.models;

public class TextureSample {

    public static final int SIMPLEOBJ_SAMPLE = 0;
    public static final int TERRAIN_SAMPLE = 1;
    public static final int TRANSPARENT_SAMPLE = 2;

    public static void applySample(ModelTexture texture, int sample){

        if(sample == SIMPLEOBJ_SAMPLE){
            texture.setShineDumper(40);
            texture.setReflectivity(0.5f);

        }else if(sample == TERRAIN_SAMPLE){
            texture.setShineDumper(40);
            texture.setReflectivity(0.2f);

        }else if(sample == TRANSPARENT_SAMPLE){
            texture.setHasTransparency(true);
            texture.setUseFakeLightning(true);
            texture.setShineDumper(40);
            texture.setReflectivity(0.2f);
        }
    }

}
