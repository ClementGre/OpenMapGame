package models;

import fr.themsou.textures.ModelTexture;

public class TexturedModel {

    private RawModel rawModel;
    private ModelTexture texture;

    public TexturedModel(RawModel model, ModelTexture texture){ // Les TexturedModel sonts une fusion d'un RawModel et d'un ModelTexture
        this.rawModel = model;
        this.texture = texture;

    }

    public RawModel getRawModel() {
        return rawModel;
    }
    public ModelTexture getTexture() {
        return texture;
    }
}
