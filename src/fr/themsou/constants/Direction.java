package fr.themsou.constants;

import fr.themsou.utils.Vector3f;

public class Direction {

    public static final Direction FORWARD = new Direction(1, 0, 0);
    public static final Direction BACKWARD = new Direction(-1, 0, 0);
    public static final Direction LEFT = new Direction(0, 0, 1);
    public static final Direction RIGHT = new Direction(0, 0, -1);
    public static final Direction UP = new Direction(0, 1, 0);
    public static final Direction DOWN = new Direction(0, -1, 0);

    private float x;
    private float y;
    private float z;

    private Direction(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3f toVector(){
        return new Vector3f(x, y, z);
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
}
