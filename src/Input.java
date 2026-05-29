import java.util.Arrays;

public class Input {
    private final boolean[] down = new boolean[256];
    private final boolean[] pressed = new boolean[256];
    private final boolean[] released = new boolean[256];

    private int mouseX = 0;
    private int mouseY = 0;
    private int mouseDeltaX = 0;
    private int mouseDeltaY = 0;

    private final boolean[] mouseDown = new boolean[8];
    private final boolean[] mousePressed = new boolean[8];
    private final boolean[] mouseReleased = new boolean[8];

    public void release() {
        Arrays.fill(this.pressed, false);
        Arrays.fill(this.released, false);
        Arrays.fill(this.mousePressed, false);
        Arrays.fill(this.mouseReleased, false);

        this.mouseDeltaX = 0;
        this.mouseDeltaY = 0;
    }

    public void keyDown(int key) {
        if (!this.down[key]) {
            this.pressed[key] = true;
        }
        this.down[key] = true;
    }

    public void keyUp(int key) {
        if (this.down[key]) {
            this.released[key] = true;
        }
        this.down[key] = false;
    }

    public void mouseDown(int button) {
        if (!this.mouseDown[button]) {
            this.mousePressed[button] = true;
        }
        this.mouseDown[button] = true;
    }

    public void mouseUp(int button) {
        if (this.mouseDown[button]) {
            this.mouseReleased[button] = true;
        }
        this.mouseDown[button] = false;
     }

    public boolean isDown(int key) {
        return this.down[key];
    }

    public boolean wasPressed(int key) {
        return this.pressed[key];
    }

    public boolean wasReleased(int key) {
        return this.released[key];
    }

    public boolean isMouseDown(int button) {
        return this.mouseDown[button];
    }

    public boolean wasMousePressed(int button) {
        return this.mousePressed[button];
    }

    public boolean wasMouseReleased(int button) {
        return this.mouseReleased[button];
    }

    public void mouseMove(int x, int y) {
        this.mouseDeltaX = x - this.mouseX;
        this.mouseDeltaY = y - this.mouseY;
        this.mouseX = x;
        this.mouseY = y;
    }

    public int getMouseX() {
        return this.mouseX;
    }

    public int getMouseY() {
        return this.mouseY;
    }

    public int getMouseDeltaX() {
        return this.mouseDeltaX;
    }

    public int getMouseDeltaY() {
        return this.mouseDeltaY;
    }
}
