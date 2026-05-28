public class Widget {
    public int x, y, width, height;
    public boolean visible = true;
    public boolean hovered = false;
    public boolean pressed = false;

    public Widget(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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
            System.out.println("Button Running");
            this.visible = false;
        }

        if (!mouseDown) {
            pressed = false;
        }
    }

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
        return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
    }
}
