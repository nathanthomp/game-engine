public class Text extends Widget {
    private final String text;

    public Text(int x, int y, int width, int height, String text) {
        super(x, y, width, height);
        this.text = text;
    }

    @Override
    public void update(Input input) {
    }

    @Override
    public void render(Renderer renderer) {
        renderer.renderText(this.text, Font.Size.MEDIUM, this.x, this.y);
    }
}
