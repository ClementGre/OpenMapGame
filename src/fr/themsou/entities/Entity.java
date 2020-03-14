package fr.themsou.entities;

import fr.themsou.models.TexturedModel;
import fr.themsou.utils.Location;

public class Entity {

    private TexturedModel model;
    public Location location;
    public float viewPitch = 0;
    private float scale;
    public int viewMode = 0;

    public Entity(TexturedModel model, Location location, float scale) {
        this.model = model;
        this.location = location;
        this.scale = scale;
    }

    public void teleport(Location location){
        this.location.setX(location.getX());
        this.location.setY(location.getY());
        this.location.setZ(location.getZ());

        if(location.isHasYawPitch()){
            this.location.setYaw(location.getYaw());
            this.viewPitch = location.getPitch();
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
    public float getViewPitch() {
        return viewPitch;
    }
    public void setViewPitch(float viewPitch) {
        this.viewPitch = viewPitch;
    }
    public float getScale() {
        return scale;
    }
    public void setScale(float scale) {
        this.scale = scale;
    }
}
