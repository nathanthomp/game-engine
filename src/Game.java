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
    private final Surface surface = new Surface(Game.WIDTH, Game.HEIGHT);
    private final Renderer renderer = new Renderer(surface);
    
    private Scene scene;
    private long lastTime;

    public Game() {
        this.scene = new Scene();

        this.setTitle(Game.TITLE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.add(surface);
        this.pack();
        this.setVisible(true);

        this.lastTime = System.nanoTime();
        this.timer.start();
    }

    private float computeDeltaTime() {
        long now = System.nanoTime();
        float delta = (now - lastTime) / 1_000_000_000.0f;
        lastTime = now;
        return delta;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        float deltaTime = computeDeltaTime();
        int fps = (int)(1.0f / deltaTime);
        System.out.println("FPS: " + fps);

        this.scene.update(deltaTime);
        this.scene.render(renderer);
        this.surface.repaint();
    }

    public static void main(String[] args) {
        new Game();
    }
}
