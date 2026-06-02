public class Text extends Widget {
    private final String text;
    private final Font.Size fontSize;

    public Text(int x, int y, String text, Font.Size fontSize) {
        super(x, y);
        this.text = text;
        this.fontSize = fontSize;
    }

    @Override
    public void update(Input input) {
    }

    @Override
    public void render(Renderer renderer) {
        renderer.renderText(this.text, this.fontSize, this.x, this.y);
    }
}
