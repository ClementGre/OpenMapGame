package fr.themsou.entities;

import fr.themsou.main.MainLoopManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

    private Vector3f position = new Vector3f(0, 0, 0);
    private float pitch;
    private float yaw;
    private float roll;

    private float total = 0;

    public Camera(Vector3f position) {
        this.setPosition(position);
    }

    public Vector3f getForward(){

        Vector3f r = new Vector3f();

        float cosY = (float) Math.cos(Math.toRadians(yaw + 90));
        float sinY = (float) Math.sin(Math.toRadians(yaw + 90));

        r.setX(cosY);
        r.setZ(sinY);
        return r;
    }
    public Vector3f getRight(){

        Vector3f r = new Vector3f();

        float cosY = (float)Math.cos(Math.toRadians(yaw));
        float sinY = (float)Math.sin(Math.toRadians(yaw));

        r.setX(cosY);
        r.setZ(sinY);
        return r;
    }


    public void updateInputs() {

        // POSITION

        float speed = 5.6f / MainLoopManager.IPS;
        float sensibility = 5;

        pitch += (-(Mouse.getDY() / sensibility));
        yaw += (Mouse.getDX() / sensibility);
        if(pitch > 90) pitch = 90;
        else if(pitch < -90) pitch = -90;
        if(yaw > 180) yaw -= 360;
        if(yaw < -180) yaw += 360;

        if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)){
            speed *= 10;
        }if(Keyboard.isKeyDown(Keyboard.KEY_E)){
            speed /= 10;
        }if(Keyboard.isKeyDown(Keyboard.KEY_Z) || Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP)){
            position.x -= getForward().getX() * speed;
            position.z -= getForward().getZ() * speed;

        }if(Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
            position.x += getForward().getX() * speed;
            position.z += getForward().getZ() * speed;

        }if(Keyboard.isKeyDown(Keyboard.KEY_Q) || Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
            position.x -= getRight().getX() * speed;
            position.z -= getRight().getZ() * speed;

        }if(Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
            position.x += getRight().getX() * speed;
            position.z += getRight().getZ() * speed;

        }if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
            position.y -= speed;;

        }if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
            position.y += speed;

        }

        // MACRO

        if(Keyboard.isKeyDown(Keyboard.KEY_RCONTROL) && Keyboard.isKeyDown(Keyboard.KEY_C)){
            position = new Vector3f(100, 1.5f, 50);

        }

    }
    public Vector3f getPosition() {
        return position;
    }
    public void setPosition(Vector3f position) {
        this.position = position;
    }
    public float getPitch() {
        return pitch;
    }
    public void setPitch(float pitch) {
        this.pitch = pitch;
    }
    public float getYaw() {
        return yaw;
    }
    public void setYaw(float yaw) {
        this.yaw = yaw;
    }
    public float getRoll() {
        return roll;
    }
    public void setRoll(float roll) {
        this.roll = roll;
    }
}
