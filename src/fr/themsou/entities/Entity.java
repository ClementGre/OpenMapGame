package fr.themsou.entities;

import fr.themsou.models.TexturedModel;
import fr.themsou.utils.Location;

public class Entity {

    private TexturedModel model;
    private int textureIndex = 0;

    public Location location;
    private float scale;

    public Entity(TexturedModel model, Location location, float scale){
        this.model = model;
        this.location = location;
        this.scale = scale;
    }
    public Entity(TexturedModel model, int textureIndex, Location location, float scale){
        this.model = model;
        this.location = location;
        this.scale = scale;
        this.textureIndex = textureIndex;
    }

    public float getTextureXOffset(){
        int column = textureIndex % model.getTexture().getNumberOfRown();
        return (float)column / (float)model.getTexture().getNumberOfRown();
    }
    public float getTextureYOffset(){
        int row = textureIndex / model.getTexture().getNumberOfRown();
        return (float)row / (float)model.getTexture().getNumberOfRown();
    }

    public void setLocation(Location location){
        this.location.setX(location.getX());
        this.location.setY(location.getY());
        this.location.setZ(location.getZ());

        if(location.isHasYawPitch()){
            this.location.setYaw(location.getYaw());
            this.location.setPitch(location.getPitch());
        }if(location.isHasRoll()){
            this.location.setRoll(location.getRoll());
        }
    }

    public TexturedModel getModel() {
        return model;
    }
    public void setModel(TexturedModel model) {
        this.model = model;
    }
    public Location getLocation() {
        return location;
    }
    public float getScale() {
        return scale;
    }
    public void setScale(float scale) {
        this.scale = scale;
    }
}
