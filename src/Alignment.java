public class Alignment {
    public static Alignment TOP_LEFT = new Alignment(Vertical.TOP, Horizontal.LEFT);
    public static Alignment TOP_CENTER = new Alignment(Vertical.TOP, Horizontal.CENTER);
    public static Alignment TOP_RIGHT = new Alignment(Vertical.TOP, Horizontal.RIGHT);
    public static Alignment CENTER_LEFT = new Alignment(Vertical.CENTER, Horizontal.LEFT);
    public static Alignment CENTER_CENTER = new Alignment(Vertical.CENTER, Horizontal.CENTER);
    public static Alignment CENTER_RIGHT = new Alignment(Vertical.CENTER, Horizontal.RIGHT);
    public static Alignment BOTTOM_LEFT = new Alignment(Vertical.BOTTOM, Horizontal.LEFT);
    public static Alignment BOTTOM_CENTER = new Alignment(Vertical.BOTTOM, Horizontal.CENTER);
    public static Alignment BOTTOM_RIGHT = new Alignment(Vertical.BOTTOM, Horizontal.RIGHT);

    public enum Vertical {
        TOP,
        CENTER,
        BOTTOM
    }

    public enum Horizontal {
        LEFT,
        CENTER,
        RIGHT
    }

    private final Vertical vertical;
    private final Horizontal horizontal;

    private Alignment(Vertical vertical, Horizontal horizontal) {
        this.vertical = vertical;
        this.horizontal = horizontal;
    }

    public Vertical getVertical() {
        return this.vertical;
    }

    public Horizontal getHorizontal() {
        return this.horizontal;
    }
}
