import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/**
 * Represents the controller layer.
 * Primary responsibility is to control the game loop and user input.
 */
public final class Game implements ActionListener {
    public static final int WIDTH = 800, HEIGHT = 600;

    private static final String TITLE = "Golf Game";
    private static final int DELAY = 0;

    private final Timer timer = new Timer(Game.DELAY, this);

    private final Input input;          // Should Input be a singleton?
    private final Manager manager;
    private final Renderer renderer;

    private long lastTime;

    private int renders = 0;
    private float rendersPerSecondTimer = 0;
    private int rendersPerSecond = 0;

    // Pass in initial scene, title, width, height
    public Game() {
        Input input = new Input();

        Manager manager = new Manager();
        Scene initalScene = new MenuScene(manager, input);
        manager.requestChange(initalScene);

        Surface surface = new Surface(Game.TITLE, input);
        Renderer renderer = new Renderer(surface);

        this.input = input;
        this.manager = manager;
        this.renderer = renderer;

        // Move this to a start method if you want a menu or something
        this.lastTime = System.nanoTime();
        this.timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        float deltaTime = this.computeDeltaTime();
        this.computeRendersPerSecond(deltaTime);

        this.manager.processChanges();
        Scene currentScene = this.manager.getCurrentScene();
        currentScene.update(deltaTime);
        // currentScene.update(deltaTime, this.input, this.manager)
        this.renderer.render(currentScene, this.rendersPerSecond);

        this.input.release();
    }

    private float computeDeltaTime() {
        long currentTime = System.nanoTime();
        float delta = (currentTime - this.lastTime) / 1_000_000_000.0f;
        this.lastTime = currentTime;
        return delta;
    }

    private void computeRendersPerSecond(float deltaTime) {
        this.rendersPerSecondTimer += deltaTime;
        this.renders++;
        if (this.rendersPerSecondTimer >= 1.0f) {
            this.rendersPerSecond = this.renders;
            this.renders = 0;
            this.rendersPerSecondTimer -= 1.0f;
            System.out.println("FPS: " + rendersPerSecond);
        }
    }

    public static void main(String[] args) {
        new Game();
    }
}
