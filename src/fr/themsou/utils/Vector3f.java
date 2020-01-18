package fr.themsou.utils;

public class Vector3f {

    private float x, y, z;

    public Vector3f(){
        this(0, 0, 0);
    }

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Vector3f(Vector3f vector) {
        this.x = vector.x;
        this.y = vector.y;
        this.z = vector.z;
    }

    public float lenght(){
        return (float)Math.sqrt(x * x + y * y + z * z);
    }
    public Vector3f normalize(){
        x /= lenght();
        y /= lenght();
        z /= lenght();

        return this;
    }
    public Vector3f add(Vector3f vector){

        this.x += vector.getX();
        this.y += vector.getY();
        this.z += vector.getZ();

        return this;
    }
    public Vector3f sub(Vector3f vector){

        this.x -= vector.getX();
        this.y -= vector.getY();
        this.z -= vector.getZ();

        return this;
    }
    public Vector3f mul(Vector3f vector){

        this.x *= vector.getX();
        this.y *= vector.getY();
        this.z *= vector.getZ();

        return this;
    }
    public Vector3f div(Vector3f vector){

        this.x /= vector.getX();
        this.y /= vector.getY();
        this.z /= vector.getZ();

        return this;
    }

    public float getX() {
        return x;
    }
    public void setX(float x) {
        this.x = x;
    }
    public Vector3f addX(float x){
        this.x += x;
        return this;
    }
    public Vector3f subX(float x){
        this.x -= x;
        return this;
    }

    public float getY() {
        return y;
    }
    public void setY(float y) {
        this.y = y;
    }
    public Vector3f addY(float y){
        this.y += y;
        return this;
    }
    public Vector3f subY(float y){
        this.y -= y;
        return this;
    }

    public float getZ() {
        return z;
    }
    public void setZ(float z) {
        this.z = z;
    }
    public Vector3f addZ(float z){
        this.z += z;
        return this;
    }
    public Vector3f subZ(float z){
        this.z -= z;
        return this;
    }

    public String toString(){

        return this.x + " " + this.y + " " + this.z;
    }

}
