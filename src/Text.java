public class Text extends Widget {
    private static int calculateWidth(String text, Font.Size fontSize) {
        return text.length() * Font.GLYPH_LENGTH * fontSize.getScale();
    }

    private static int calculateHeight(Font.Size fontSize) {
        return Font.GLYPH_LENGTH * fontSize.getScale();
    }

    private final String text;
    private final Font.Size fontSize;

    public Text(int x, int y, String text, Font.Size fontSize) {
        super(x, y, Text.calculateWidth(text, fontSize), Text.calculateHeight(fontSize));
        this.text = text;
        this.fontSize = fontSize;
    }

    public Text(Widget container, String text, Font.Size fontSize) {
        super(container, Text.calculateWidth(text, fontSize), Text.calculateHeight(fontSize));
        this.text = text;
        this.fontSize = fontSize;
    }

    public Text(Widget container, Alignment alignment, String text, Font.Size fontSize) {
        super(container, alignment, Text.calculateWidth(text, fontSize), Text.calculateHeight(fontSize));
        this.text = text;
        this.fontSize = fontSize;
    }

    @Override
    public void update(Input input) {
    }

    @Override
    public void render(Renderer renderer) {
        renderer.renderText(this.text, this.fontSize, super.x, super.y);
    }
}
