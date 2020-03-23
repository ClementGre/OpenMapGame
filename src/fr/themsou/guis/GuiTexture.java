package fr.themsou.guis;

import fr.themsou.constants.Angle;
import fr.themsou.main.DisplayManager;
import org.lwjgl.util.vector.Vector2f;

public class GuiTexture {

    private int texture;
    private Vector2f position;
    private Vector2f scale;

    private Angle origin;
    private boolean adaptPosition;
    private boolean adaptSize;

    public GuiTexture(int texture, Vector2f position, Vector2f scale, Angle origin, boolean adaptPosition, boolean adaptSize) {
        this.texture = texture;
        this.position = position;
        this.scale = scale;
        this.origin = origin;
        this.adaptPosition = adaptPosition;
        this.adaptSize = adaptSize;
    }

    public int getTexture() {
        return texture;
    }

    public void setTexture(int texture) {
        this.texture = texture;
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public Vector2f getScale() {
        return scale;
    }

    public void setScale(Vector2f scale) {
        this.scale = scale;
    }

    public Angle getOrigin() {
        return origin;
    }

    public void setOrigin(Angle origin) {
        this.origin = origin;
    }

    public boolean isAdaptSize() {
        return adaptSize;
    }

    public void setAdaptSize(boolean adaptSize) {
        this.adaptSize = adaptSize;
    }

    public boolean isAdaptPosition() {
        return adaptPosition;
    }

    public void setAdaptPosition(boolean adaptPosition) {
        this.adaptPosition = adaptPosition;
    }
}
