import java.awt.image.DataBufferInt;
import java.util.Arrays;

/**
 * Represents the view logic.
 * Can be extended to include WireframeRenderer, DebugRenderer, NormalRenderer
 */
public class Renderer {
    private final int[] pixels;

    public Renderer(Surface surface) {
        this.pixels = ((DataBufferInt) surface.getFramebuffer().getRaster().getDataBuffer()).getData();
    }

    public void clear() {
        Arrays.fill(pixels, 0xFF000000); // opaque black
    }

    public void drawRectangle(int x, int y, int w, int h, int argb) {
        for (int yy = y; yy < y + h; yy++) {
            if (yy < 0 || yy >= Game.HEIGHT) {
                continue;
            }
            for (int xx = x; xx < x + w; xx++) {
                if (xx < 0 || xx >= Game.WIDTH) {
                    continue;
                }
                pixels[yy * Game.WIDTH + xx] = argb;
            }
        }
    }

    public void drawBorder() {
        for (int x = 0; x < Game.WIDTH; x++) {
            pixels[x] = 0xFFFF0000; // top border
            pixels[pixels.length - Game.WIDTH + x] = 0xFFFF0000; // bottom border
        }
        for (int y = 0; y < Game.HEIGHT; y++) {
            pixels[y * Game.WIDTH] = 0xFFFF0000; // left border
            pixels[y * Game.WIDTH + Game.WIDTH - 1] = 0xFFFF0000; // right border
        }
    }
}
