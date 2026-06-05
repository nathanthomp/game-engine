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

    private enum Vertical {
        TOP,
        CENTER,
        BOTTOM
    }

    private enum Horizontal {
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

    public int calculateX(Widget container, int width, int height) {
        switch (this.horizontal) {
            case LEFT:
                return container.getX();
            case CENTER:
                return container.getX() + (container.getWidth() / 2) - (width / 2);
            case RIGHT:
                return container.getX() + container.getWidth() - width;
            default:
                return 0;
        }
    }

    public int calculateY(Widget container, int width, int height) {
        switch (this.vertical) {
            case TOP:
                return container.getY();
            case CENTER:
                return container.getY() + (container.getHeight() / 2) - (height / 2);
            case BOTTOM:
                return container.getY() + container.getHeight() - height;
            default:
                return 0;
        }
    }
}
