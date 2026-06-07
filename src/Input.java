import java.util.Arrays;

public class Input {
    public static final int KEY_W = 87;
    public static final int KEY_A = 65;
    public static final int KEY_S = 83;
    public static final int KEY_D = 68;
    public static final int KEY_ESCAPE = 27;

    public static final int MOUSE_LEFT = 1;
    public static final int MOUSE_RIGHT = 0;

    private static Input instance;

    private static Input getInstance() {
        if (instance == null) {
            instance = new Input();
        }
        return instance;
    }

    public static void release() {
        Input input = getInstance();
        Arrays.fill(input.pressed, false);
        Arrays.fill(input.released, false);
        Arrays.fill(input.mousePressed, false);
        Arrays.fill(input.mouseReleased, false);

        input.mouseDeltaX = 0;
        input.mouseDeltaY = 0;
    }

    public static void keyDown(int key) {
        Input input = getInstance();
        if (!input.down[key]) {
            input.pressed[key] = true;
        }
        input.down[key] = true;
    }

    public static void keyUp(int key) {
        Input input = getInstance();
        if (input.down[key]) {
            input.released[key] = true;
        }
        input.down[key] = false;
    }

    public static void mouseDown(int button) {
        Input input = getInstance();
        if (!input.mouseDown[button]) {
            input.mousePressed[button] = true;
        }
        input.mouseDown[button] = true;
    }

    public static void mouseUp(int button) {
        Input input = getInstance();
        if (input.mouseDown[button]) {
            input.mouseReleased[button] = true;
        }
        input.mouseDown[button] = false;
    }

    public static boolean isDown(int key) {
        Input input = getInstance();
        return input.down[key];
    }

    public static boolean wasPressed(int key) {
        Input input = getInstance();
        return input.pressed[key];
    }

    public static boolean wasReleased(int key) {
        Input input = getInstance();
        return input.released[key];
    }

    public static boolean isMouseDown(int button) {
        Input input = getInstance();
        return input.mouseDown[button];
    }

    public static boolean wasMousePressed(int button) {
        Input input = getInstance();
        return input.mousePressed[button];
    }

    public static boolean wasMouseReleased(int button) {
        Input input = getInstance();
        return input.mouseReleased[button];
    }

    public static void mouseMove(int x, int y) {
        Input input = getInstance();
        input.mouseDeltaX = x - input.mouseX;
        input.mouseDeltaY = y - input.mouseY;
        input.mouseX = x;
        input.mouseY = y;
    }

    public static int getMouseX() {
        Input input = getInstance();
        return input.mouseX;
    }

    public static int getMouseY() {
        Input input = getInstance();
        return input.mouseY;
    }

    public static int getMouseDeltaX() {
        Input input = getInstance();
        return input.mouseDeltaX;
    }

    public static int getMouseDeltaY() {
        Input input = getInstance();
        return input.mouseDeltaY;
    }

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
}
