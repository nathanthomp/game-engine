public abstract class Widget {
    protected int x, y, width, height;
    protected boolean visible = true;
    protected boolean hovered = false;
    protected boolean pressed = false;

    public Widget(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract void update(Input input);
    public abstract void render(Renderer renderer);
}
