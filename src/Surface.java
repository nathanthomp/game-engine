import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Represents the view layer.
 * Will not be extendable (99% chance).
 */
public final class Surface extends JFrame implements KeyListener {

    private final class Display extends JPanel {
        private final BufferedImage frameBuffer;

        public Display() {
            this.setPreferredSize(new Dimension(Game.WIDTH, Game.HEIGHT));
            this.frameBuffer = new BufferedImage(Game.WIDTH, Game.HEIGHT, BufferedImage.TYPE_INT_ARGB);
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            graphics.drawImage(this.frameBuffer, 0, 0, null);
        }
    }

    private final Input input;
    private final Display display = new Display();

    public Surface(String title, Input input) {
        this.input = input;

        this.setFocusable(true);
        this.requestFocusInWindow();

        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.add(this.display);
        this.pack();
        this.setVisible(true);

        this.addKeyListener(this);
    }

    public BufferedImage getFramebuffer() {
        return this.display.frameBuffer;
    }

    @Override
    public void keyPressed(KeyEvent event) {
        this.input.keyDown(event.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent event) {
        this.input.keyUp(event.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent event) {
    }
}
