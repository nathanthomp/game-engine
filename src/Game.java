import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public final class Game implements ActionListener {
    public static final int WIDTH = 800, HEIGHT = 600;

    private static final int DELAY = 0;

    private final Timer timer = new Timer(Game.DELAY, this);

    private final Manager manager;
    private final Renderer renderer;

    private long lastTime;

    private int renders = 0;
    private float rendersPerSecondTimer = 0;
    private int rendersPerSecond = 0;

    public Game(String title, Scene initialScene) {
        Manager manager = new Manager();
        manager.requestForward(initialScene);

        Surface surface = new Surface(title);
        Renderer renderer = new Renderer(surface);

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
        this.renderer.render(currentScene, this.rendersPerSecond);

        Input.release();
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
        }
    }

    public static void main(String[] args) {
        new Game("Game", new MenuScene());
    }
}
