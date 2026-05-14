import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

/**
 * Represents the controller layer.
 * Primary responsibility is to control the game loop and user input.
 */
public class Game extends JFrame implements ActionListener {
    public static final int WIDTH = 800, HEIGHT = 600;

    private static final String TITLE = "Golf Game";
    private static final int DELAY = 0;

    private final Timer timer = new Timer(Game.DELAY, this);
    private final Input input = new Input();
    private final Surface surface = new Surface(this.input);
    private final Renderer renderer = new Renderer(this.surface);

    private Scene scene;
    private long lastTime;

    private int renders = 0;
    private float rendersPerSecondTimer = 0;
    private int rendersPerSecond = 0;

    public Game() {
        this.scene = new Scene(this.input);

        this.setTitle(Game.TITLE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.add(surface);
        this.pack();
        this.setVisible(true);

        this.lastTime = System.nanoTime();
        this.timer.start();
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

    @Override
    public void actionPerformed(ActionEvent event) {
        float deltaTime = this.computeDeltaTime();
        this.computeRendersPerSecond(deltaTime);

        this.scene.update(deltaTime);
        this.scene.render(renderer);
        this.surface.repaint();

        this.input.release();
    }

    public static void main(String[] args) {
        new Game();
    }
}
