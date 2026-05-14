import java.util.Arrays;

public class Input {
    private final boolean[] down = new boolean[256];
    private final boolean[] pressed = new boolean[256];
    private final boolean[] released = new boolean[256];

    public void release() {
        Arrays.fill(pressed, false);
        Arrays.fill(released, false);
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

    public boolean isDown(int key) {
        return this.down[key];
    }

    public boolean wasPressed(int key) {
        return this.pressed[key];
    }

    public boolean wasReleased(int key) {
        return this.released[key];
    }
}
