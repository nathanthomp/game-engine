import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * Represents the view layer.
 * Will not be extendable (99% chance).
 */
public class Surface extends JPanel implements KeyListener {
    private final Input input;
    private final BufferedImage frameBuffer;

    public Surface(Input input) {
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.setPreferredSize(new Dimension(Game.WIDTH, Game.HEIGHT));
        this.addKeyListener(this);

        this.input = input;
        this.frameBuffer = new BufferedImage(Game.WIDTH, Game.HEIGHT, BufferedImage.TYPE_INT_ARGB);
    }

    public BufferedImage getFramebuffer() {
        return this.frameBuffer;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(this.frameBuffer, 0, 0, null);
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
