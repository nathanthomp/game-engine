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
    private final BufferedImage frameBuffer;
    
    public Surface(int width, int height) {
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.setPreferredSize(new Dimension(width, height));
        this.addKeyListener(this);

        this.frameBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
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
    public void keyTyped(KeyEvent event) {
    }

    @Override
    public void keyPressed(KeyEvent event) {
        System.out.println("Key Pressed: " + event.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent event) {
    }
}
