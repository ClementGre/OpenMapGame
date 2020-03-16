package fr.themsou.entities.livingEntity;

import fr.themsou.entities.Entity;
import fr.themsou.entities.livingEntity.ocularEntity.OcularEntity;
import fr.themsou.main.MainLoopManager;
import fr.themsou.models.TexturedModel;
import fr.themsou.utils.Location;
import org.lwjgl.util.vector.Vector3f;

public class LivingEntity extends Entity {

    public float viewPitch = 0;
    public final static float DEFAULT_WALK_SPEED = 5.6f / MainLoopManager.IPS;
    public final static float DEFAULT_JUMP_COOLDOWN = 0.1f * MainLoopManager.IPS;

    public LivingEntity(TexturedModel model, Location location, float scale) {
        super(model, location, scale);
    }
    /*
     * PITCH : View height
     * 90 : down
     * 0 & 360 : horizontal
     * 270 : up
     *
     * YAW : View Rotation
     * 0 & 360 : forward
     * 180 : backward
     */

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

        float cosY = (float)Math.cos(Math.toRadians(-super.location.getYaw()+180));
        float sinP = (float)Math.sin(Math.toRadians(viewPitch));
        float sinY = (float) Math.sin(Math.toRadians(-location.getYaw()+180));

        r.setX(cosY);
        r.setY(sinP);
        r.setZ(sinY);
        return r;
    }

    public void moveForward(float lenght){
        Vector3f move = getForward();
        location.increasePosition(-move.getX() * lenght, 0, -move.getZ() * lenght);
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
        if(this instanceof OcularEntity){
            if(((OcularEntity) this).getViewMode() != 0){
                if(this.viewPitch > 70 && this.viewPitch < 180) this.viewPitch = 70;
            }
        }

        if(viewPitch > 360) viewPitch -= 360;
        else if(viewPitch < 0) viewPitch += 360;

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

    public float getViewPitch() {
        return viewPitch;
    }

    public void setViewPitch(float viewPitch) {
        this.viewPitch = viewPitch;
    }
}
