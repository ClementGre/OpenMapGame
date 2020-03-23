package fr.themsou.constants;


public class Angle {

    public static final Angle TOP_LEFT = new Angle(true, true);
    public static final Angle BOTTOM_LEFT = new Angle(false, true);
    public static final Angle TOP_RIGHT = new Angle(true, false);
    public static final Angle BOTTOM_RIGHT = new Angle(false, false);

    private boolean top;
    private boolean left;

    private Angle(boolean top, boolean left){
        this.top = top;
        this.left = left;
    }

    public boolean isTop() {
        return top;
    }
    public void setTop(boolean top) {
        this.top = top;
    }
    public boolean isLeft() {
        return left;
    }
    public void setLeft(boolean left) {
        this.left = left;
    }
}
