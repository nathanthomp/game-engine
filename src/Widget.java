public abstract class Widget {
    protected int x, y;
    protected boolean visible = true;
    protected boolean hovered = false;
    protected boolean pressed = false;

    public Widget(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract void update(Input input);
    public abstract void render(Renderer renderer);
}
