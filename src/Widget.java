/**
 * Widgets need a Position (x, y) and Size (width, height) to be rendered and
 * interacted with. We can either pass these values to each widget or we can
 * create a container widget that holds other widgets and provides them with a
 * relative position and size. For example, we could create a container widget
 * that fills the entire screen and then position the resume and exit buttons
 * within that container. This would allow us to easily move and resize the
 * entire pause menu by simply changing the position and size of the container,
 * rather than having to update the position and size of each individual widget.
 * 
 * Positioning:
 * - public Widget(position) -> Absolute position
 * - public Widget(container) -> Relative position
 * - public Widget(container, alignment) -> Relative position
 * 
 * Sizing:
 * - public Widget(int width, int height)
 * 
 * Together:
 * - public Widget(position, width, height)
 * - public Widget(container, width, height)
 * - public Widget(container, alignment, width, height)
 */
public abstract class Widget {
    protected final int x, y;
    protected final int width, height;

    protected boolean visible = true;

    public Widget(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Widget(Widget container, int width, int height) {
        this.x = container.getX();
        this.y = container.getY();
        this.width = width;
        this.height = height;
    }

    public Widget(Widget container, Alignment alignment, int width, int height) {
        this.x = alignment.calculateX(container, width, height);
        this.y = alignment.calculateY(container, width, height);
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public abstract void update();

    public abstract void render(Renderer renderer);
}
