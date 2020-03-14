package fr.themsou.entities;

import fr.themsou.models.TexturedModel;
import fr.themsou.utils.Location;
import org.lwjgl.util.vector.Vector3f;

public class LivingEntity extends Entity{

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
}
