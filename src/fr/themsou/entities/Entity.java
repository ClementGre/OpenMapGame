package fr.themsou.entities;

import fr.themsou.models.TexturedModel;
import fr.themsou.utils.Location;
import org.lwjgl.util.vector.Vector3f;

public class Entity {

    private TexturedModel model;
    private Location location;
    private float viewPitch = 0;
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

    // LOCATION

    public void moveForward(float lenght){
        Vector3f move = getForward();
        location.increasePosition(move.getX() * lenght, 0, move.getZ() * lenght);
    }
    public void moveSide(float lenght){
        Vector3f move = getSide();
        location.increasePosition(move.getX() * lenght, 0, move.getZ() * lenght);
    }
    public void moveHeight(float lenght){
        location.increasePosition(0, lenght, 0);
    }
    public void moveView(float yaw, float pitch, float roll){
        location.increaseRotation(yaw, 0, roll);
        this.viewPitch += pitch;

        if(this.viewPitch < 270 && this.viewPitch > 180) this.viewPitch = 270;
        else if(this.viewPitch > 90 && this.viewPitch < 180) this.viewPitch = 90;
        if(viewPitch > 360) viewPitch -= 360;
        else if(viewPitch < 0) viewPitch += 360;

    }

    public Vector3f getForward(){

        Vector3f r = new Vector3f();

        float cosY = (float) Math.cos(Math.toRadians((-location.getYaw()+180) + 90));
        float sinP = (float) Math.sin(Math.toRadians(viewPitch));
        float sinY = (float) Math.sin(Math.toRadians((-location.getYaw()+180) + 90));

        r.setX(cosY);
        r.setY(sinP);
        r.setZ(sinY);
        return r;
    }
    public Vector3f getSide(){

        Vector3f r = new Vector3f();

        float cosY = (float)Math.cos(Math.toRadians(-location.getYaw()+180));
        float sinP = (float)Math.sin(Math.toRadians(viewPitch));
        float sinY = (float) Math.sin(Math.toRadians(-location.getYaw()+180));

        r.setX(cosY);
        r.setY(sinP);
        r.setZ(sinY);
        return r;
    }

    public Location getViewLocation(){

        if(viewMode == 0){
            Vector3f back = getForward();
            Location newLoc = location.clone();
            newLoc.setPitch(viewPitch);
            newLoc.setYaw(-location.getYaw()+180);
            newLoc.increasePosition(-back.getX()/3, 2, -back.getZ()/3);
            return newLoc;
        }
        if(viewMode == 1){
            Vector3f back = getForward();
            Location newLoc = location.clone();
            newLoc.setYaw(-location.getYaw()+180);
            newLoc.setPitch(viewPitch);
            newLoc.increasePosition(back.getX() * 5, 2+ back.getY() * 5, back.getZ() * 5);

            return newLoc;
        }
        if(viewMode == 2){

            Vector3f back = getForward();
            Location newLoc = location.clone();
            newLoc.increasePosition(-back.getX() * 5, 2 + back.getY() * 5, -back.getZ() * 5);
            newLoc.setYaw(180-location.getYaw()+180);
            newLoc.setPitch(viewPitch);
            return newLoc;
        }
        return null;

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
