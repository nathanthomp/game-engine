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

    private final class Display extends JPanel  implements KeyListener, MouseListener, MouseMotionListener {
        private final Input input;
        private final BufferedImage frameBuffer;

        public Display(Input input) {
            this.input = input;
            this.frameBuffer = new BufferedImage(Game.WIDTH, Game.HEIGHT, BufferedImage.TYPE_INT_ARGB);

            this.setPreferredSize(new Dimension(Game.WIDTH, Game.HEIGHT));
            this.setDoubleBuffered(true);

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
            this.input.keyDown(event.getKeyCode());
        }

        @Override
        public void keyReleased(KeyEvent event) {
            this.input.keyUp(event.getKeyCode());
        }

        @Override
        public void keyTyped(KeyEvent event) {
        }

        @Override
        public void mouseClicked(MouseEvent event) {
        }

        @Override
        public void mousePressed(MouseEvent event) {
            input.mouseDown(event.getButton());
        }

        @Override
        public void mouseReleased(MouseEvent event) {
            input.mouseUp(event.getButton());
        }

        @Override
        public void mouseEntered(MouseEvent event) {
        }

        @Override
        public void mouseExited(MouseEvent event) {
        }

        @Override
        public void mouseDragged(MouseEvent event) {
            input.mouseMove(event.getX(), event.getY());
        }

        @Override
        public void mouseMoved(MouseEvent event) {
            input.mouseMove(event.getX(), event.getY());
        }
    }

    private final Display display;

    public Surface(String title, Input input) {
        this.display = new Display(input);

        this.setFocusable(true);
        this.requestFocusInWindow();

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
