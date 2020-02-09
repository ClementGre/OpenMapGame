package fr.themsou.entities;

import org.lwjgl.util.vector.Vector3f;

public class Sky {

    private Vector3f colour;
    private Light sun;

    public Sky(Vector3f colour, Light sun) {
        this.colour = colour;
        this.sun = sun;
    }

    public Vector3f getColour() {
        return colour;
    }
    public void setColour(Vector3f colour) {
        this.colour = colour;
    }
    public Light getSun() {
        return sun;
    }
    public void setSun(Light sun) {
        this.sun = sun;
    }
}
