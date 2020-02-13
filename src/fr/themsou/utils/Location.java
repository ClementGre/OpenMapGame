package fr.themsou.utils;

import org.lwjgl.util.vector.Vector3f;

public class Location {

    float x;
    float y;
    float z;

    float yaw = 0;
    float pitch = 0;
    float roll = 0;

    boolean hasYawPitch = false;
    boolean hasRoll = false;

    public Location(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Location(float x, float y, float z, float yaw, float pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        hasYawPitch = true;
        checkRotation();
    }
    public Location(float x, float y, float z, float yaw, float pitch, float roll) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.roll = roll;
        hasYawPitch = true;
        hasRoll = true;
        checkRotation();
    }

    public void increasePosition(float dx, float dy, float dz){
        x += dx;
        y += dy;
        z += dz;
    }

    public void increaseRotation(float yaw, float pitch, float roll){
        this.yaw += yaw;
        this.pitch += pitch;
        this.roll += roll;
        checkRotation();
    }

    public float getX() {
        return x;
    }
    public void setX(float x) {
        this.x = x;
    }
    public float getY() {
        return y;
    }
    public void setY(float y) {
        this.y = y;
    }
    public float getZ() {
        return z;
    }
    public void setZ(float z) {
        this.z = z;
    }
    public void addYaw(float yaw){
        this.yaw += yaw;
        checkRotation();
    }
    public float getYaw() {
        return yaw;
    }
    public void setYaw(float yaw) {
        this.yaw = yaw;
        checkRotation();
    }
    public void addPitch(float pitch){
        this.pitch += pitch;
        checkRotation();
    }
    public float getPitch() {
        return pitch;
    }
    public void setPitch(float pitch) {
        this.pitch = pitch;
        checkRotation();
    }
    public void addRoll(float roll){
        this.roll += roll;
        checkRotation();
    }
    public float getRoll() {
        return roll;
    }
    public void setRoll(float roll) {
        this.roll = roll;
        checkRotation();
    }
    public boolean isHasRoll() {
        return hasRoll;
    }
    public void setHasRoll(boolean hasRoll) {
        this.hasRoll = hasRoll;
    }
    public boolean isHasYawPitch() {
        return hasYawPitch;
    }
    public void setHasYawPitch(boolean hasYawPitch) {
        this.hasYawPitch = hasYawPitch;
    }

    public void checkRotation(){

        if(yaw > 360) yaw -= 360;
        else if(yaw < 0) yaw += 360;

        if(pitch > 360) pitch -= 360;
        else if(pitch < 0) pitch += 360;

        if(roll > 360) roll -= 360;
        else if(roll < 0) roll += 360;
    }

    public Vector3f toVector() {
        return new Vector3f(x, y, z);
    }

    public Location clone(){
        Location cloned = new Location(x, y, z, yaw, pitch, roll);
        cloned.setHasYawPitch(hasYawPitch);
        cloned.setHasRoll(hasRoll);
        return cloned;
    }
}
