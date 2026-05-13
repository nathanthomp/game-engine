import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Game extends JFrame implements ActionListener {
    private static final String TITLE = "Golf Game";
    private static final int HEIGHT = 600;
    private static final int WIDTH = 800;
    private static final int DELAY = 16;

    private final Timer timer = new Timer(Game.DELAY, this);

    public Game() {
        this.setTitle(Game.TITLE);
        this.setSize(Game.WIDTH, Game.HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
    }

    public static void main(String[] args) {
        new Game();
    }
}
