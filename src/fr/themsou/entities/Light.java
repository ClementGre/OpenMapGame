package fr.themsou.entities;

import fr.themsou.models.TexturedModel;
import fr.themsou.utils.Location;
import org.lwjgl.util.vector.Vector3f;

public class Light extends Entity{

    private Vector3f colour;

    public Light(TexturedModel model, Location location, Vector3f colour, float scale) {
        super(model, location, scale);
        this.colour = colour;
    }
    public Vector3f getColour() {
        return colour;
    }
    public void setColour(Vector3f colour) {
        this.colour = colour;
    }
}
