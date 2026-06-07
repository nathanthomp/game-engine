import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Represents the view layer.
 * Will not be extendable (99% chance).
 */
public final class Surface extends JFrame {

    private final class Display extends JPanel implements KeyListener, MouseListener, MouseMotionListener {
        private final BufferedImage frameBuffer;

        public Display() {
            this.frameBuffer = new BufferedImage(Game.WIDTH, Game.HEIGHT, BufferedImage.TYPE_INT_ARGB);

            this.setPreferredSize(new Dimension(Game.WIDTH, Game.HEIGHT));
            this.setDoubleBuffered(true);
            this.setFocusable(true);
            this.requestFocusInWindow();

            this.addKeyListener(this);
            this.addMouseListener(this);
            this.addMouseMotionListener(this);
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            graphics.drawImage(this.frameBuffer, 0, 0, null);
        }

        @Override
        public void keyPressed(KeyEvent event) {
            Input.keyDown(event.getKeyCode());
        }

        @Override
        public void keyReleased(KeyEvent event) {
            Input.keyUp(event.getKeyCode());
        }

        @Override
        public void keyTyped(KeyEvent event) {
        }

        @Override
        public void mouseClicked(MouseEvent event) {
        }

        @Override
        public void mousePressed(MouseEvent event) {
            Input.mouseDown(event.getButton());
        }

        @Override
        public void mouseReleased(MouseEvent event) {
            Input.mouseUp(event.getButton());
        }

        @Override
        public void mouseEntered(MouseEvent event) {
        }

        @Override
        public void mouseExited(MouseEvent event) {
        }

        @Override
        public void mouseDragged(MouseEvent event) {
            Input.mouseMove(event.getX(), event.getY());
        }

        @Override
        public void mouseMoved(MouseEvent event) {
            Input.mouseMove(event.getX(), event.getY());
        }
    }

    private final Display display;

    public Surface(String title) {
        this.display = new Display();

        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.add(this.display);
        this.pack();
        this.setVisible(true);
    }

    public int[] getPixels() {
        return ((DataBufferInt) this.display.frameBuffer.getRaster().getDataBuffer()).getData();
    }
}
