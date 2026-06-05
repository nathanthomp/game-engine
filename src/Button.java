public class Button extends Widget {
    private final Runnable runnable;

    protected boolean hovered = false;
    protected boolean pressed = false;

    public Button(int x, int y, int width, int height, Runnable runnable) {
        super(x, y, width, height);
        this.runnable = runnable;
    }

    public void update(Input input) {
        if (!super.visible) {
            return;
        }

        this.hovered = this.contains(input.getMouseX(), input.getMouseY());

        boolean mouseDown = input.isMouseDown(Input.MOUSE_LEFT);
        boolean mousePressed = input.wasMousePressed(Input.MOUSE_LEFT);
        boolean mouseReleased = input.wasMouseReleased(Input.MOUSE_LEFT);

        if (this.hovered && mousePressed) {
            this.pressed = true;
        }

        if (this.pressed && this.hovered && mouseReleased) {
            this.runnable.run();
        }

        if (!mouseDown) {
            this.pressed = false;
        }
    }

    @Override
    public void render(Renderer renderer) {
        if (this.visible) {
            int color = 0xFFFF00FF;
            if (this.hovered) {
                color = 0xFFFF0F0F;
            }
            renderer.renderRectangle(super.x, super.y, super.width, super.height, color, true);
        }
    }

    private boolean contains(int mouseX, int mouseY) {
        return mouseX >= super.x && mouseX < super.x + super.width && mouseY >= super.y
                && mouseY < super.y + super.height;
    }
}
