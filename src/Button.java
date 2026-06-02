public class Button extends Widget {
    private final int width, height;
    private final Runnable runnable;

    public Button(int x, int y, int width, int height, Runnable runnable) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.runnable = runnable;
    }

    public void update(Input input) {
        if (!this.visible) {
            return;
        }
        
        this.hovered = this.contains(input.getMouseX(), input.getMouseY());

        boolean mouseDown = input.isMouseDown(1);
        boolean mousePressed = input.wasMousePressed(1);
        boolean mouseReleased = input.wasMouseReleased(1);

        if (hovered && mousePressed) {
            pressed = true;
        }

        if (pressed && hovered && mouseReleased) {
            this.runnable.run();
        }

        if (!mouseDown) {
            pressed = false;
        }
    }

    @Override
    public void render(Renderer renderer) {
        if (this.visible) {
            int color = 0xFFFF00FF;
            if (this.hovered) {
                color = 0xFFFF0F0F;
            }
            renderer.renderRectangle(this.x, this.y, this.width, this.height, color, true);
        }
    }

    private boolean contains(int mouseX, int mouseY) {
        return mouseX >= this.x && mouseX < this.x + this.width && mouseY >= this.y && mouseY < this.y + this.height;
    }
}
