import java.util.Arrays;

/**
 * Represents the view logic.
 * Can be extended to include WireframeRenderer, DebugRenderer, NormalRenderer
 * 
 * Responsibilites include rendering entities and text
 * 
 * Methods:
 * - render(Scene scene): renders the given scene
 * - renderText(String text, int scale, int x, int y): renders text at the given position and scale
 * - renderRectangle(int x, int y, int width, int height, int argb, boolean filled): renders a rectangle at the given position and size with the given color
 */
public class Renderer {
    private final Surface surface;
    private final int[] pixels;
    private final float[] depths;

    public Renderer(Surface surface) {
        this.surface = surface;
        this.pixels = surface.getPixels();
        this.depths = new float[this.pixels.length];
    }

    /**
     * Renderer must have render method
     * 
     * this.clear()
     * scene.render(this) -> Scene must have render method
     * Extra stuff here
     */
    public void render(Scene scene, int rendersPerSecond) {
        this.clear();

        scene.render(this);

        int startY = 10;
        int scale = 1;
        int padding = 2;

        this.renderText("0123456789", scale, 10, startY);

        startY = startY + (Text.GLYTH_LENGTH * scale) + padding;
        scale++;
        Text.Size size = Text.Size.LARGE;
        this.renderText("ABCDEFGHIJKLMNOPQRSTUVWXYZ", scale, 10, startY);

        startY = startY + (Text.GLYTH_LENGTH * scale) + padding;
        scale--;

        this.renderText("FPS: " + rendersPerSecond, scale, 10, 38);

        this.surface.repaint();
    }

    public void renderText(String text, int scale, int startX, int startY) {
        for (int i = 0; i < text.length(); i++) {
            char character = text.charAt(i);
            int[] glyph = Text.getGlyph(character, scale);

            for (int j = 0; j < glyph.length; j++) {
                if (glyph[j] == 1) {
                    int gx = j % (Text.GLYTH_LENGTH * scale);
                    int gy = j / (Text.GLYTH_LENGTH * scale);
                    drawPixel(startX + i * (Text.GLYTH_LENGTH * scale) + gx, startY + gy, 0xFFFFFFFF);
                }
            }
        }
    }

    public void renderRectangle(int x, int y, int width, int height, int color, boolean fill) {
        if (fill) {
            for (int j = y; j < y + height; j++) {
                for (int i = x; i < x + width; i++) {
                    drawPixel(i, j, color);
                }
            }
        } else {
            for (int i = x; i < x + width; i++) {
                drawPixel(i, y, color);
                drawPixel(i, y + height - 1, color);
            }
            for (int j = y; j < y + height; j++) {
                drawPixel(x, j, color);
                drawPixel(x + width - 1, j, color);
            }
        }
    }

    private void clear() {
        Arrays.fill(this.pixels, 0xFF000000); // opaque black
        Arrays.fill(this.depths, Float.POSITIVE_INFINITY);
    }

    public boolean isBackface(Geometry.TransformedTriangle triangle) {;
            float area = (triangle.b.x - triangle.a.x) * (triangle.c.y - triangle.a.y) -
                        (triangle.b.y - triangle.a.y) * (triangle.c.x - triangle.a.x);
            return area >= 0;
    }

    public void rasterize(Geometry.TransformedTriangle triangle, int argb) {
        int minX = Math.max(0, (int) Math.floor(Math.min(triangle.a.x, Math.min(triangle.b.x, triangle.c.x))));
        int maxX = Math.min(Game.WIDTH - 1,
                (int) Math.ceil(Math.max(triangle.a.x, Math.max(triangle.b.x, triangle.c.x))));

        int minY = Math.max(0, (int) Math.floor(Math.min(triangle.a.y, Math.min(triangle.b.y, triangle.c.y))));
        int maxY = Math.min(Game.HEIGHT - 1,
                (int) Math.ceil(Math.max(triangle.a.y, Math.max(triangle.b.y, triangle.c.y))));

        float area = edge(triangle.a.x, triangle.a.y, triangle.b.x, triangle.b.y, triangle.c.x, triangle.c.y);

        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                float w0 = edge(triangle.b.x, triangle.b.y, triangle.c.x, triangle.c.y, x, y);
                float w1 = edge(triangle.c.x, triangle.c.y, triangle.a.x, triangle.a.y, x, y);
                float w2 = edge(triangle.a.x, triangle.a.y, triangle.b.x, triangle.b.y, x, y);

                if (w0 >= 0 && w1 >= 0 && w2 >= 0) {
                    w0 /= area;
                    w1 /= area;
                    w2 /= area;

                    float depth = w0 * triangle.a.z + w1 * triangle.b.z + w2 * triangle.c.z;
                    int index = y * Game.WIDTH + x;

                    if (depth < this.depths[index]) {
                        this.depths[index] = depth;
                        this.pixels[index] = argb;
                    }
                }
            }
        }
    }

    public void drawWireframe(Geometry.TransformedTriangle t, int color) {
        drawLineDepth(t.a.x, t.a.y, t.a.z,
                t.b.x, t.b.y, t.b.z, color);

        drawLineDepth(t.b.x, t.b.y, t.b.z,
                t.c.x, t.c.y, t.c.z, color);

        drawLineDepth(t.c.x, t.c.y, t.c.z,
                t.a.x, t.a.y, t.a.z, color);
    }

    public void drawLine(int x0, int y0, int x1, int y1, int color) {
        float dx = x1 - x0;
        float dy = y1 - y0;

        int steps = (int) Math.max(Math.abs(dx), Math.abs(dy));
        if (steps == 0) {
            drawPixel(x0, y0, color);
            return;
        }

        float sx = dx / steps;
        float sy = dy / steps;

        float x = x0;
        float y = y0;

        for (int i = 0; i <= steps; i++) {
            drawPixel((int) x, (int) y, color);
            x += sx;
            y += sy;
        }

    }

    private void drawLineDepth(float x0, float y0, float z0,
            float x1, float y1, float z1,
            int color) {

        float dx = x1 - x0;
        float dy = y1 - y0;
        float dz = z1 - z0;

        int steps = (int) Math.max(Math.abs(dx), Math.abs(dy));
        if (steps == 0) {
            return;
        }

        float sx = dx / steps;
        float sy = dy / steps;
        float sz = dz / steps;

        float x = x0;
        float y = y0;
        float z = z0;

        for (int i = 0; i <= steps; i++) {
            drawPixelDepth((int) x, (int) y, z, color);
            x += sx;
            y += sy;
            z += sz;
        }
    }

    private void drawPixel(int x, int y, int color) {
        if (x < 0 || x >= Game.WIDTH || y < 0 || y >= Game.HEIGHT) return;
        pixels[y * Game.WIDTH + x] = color;
    }

    private void drawPixelDepth(int x, int y, float depth, int color) {
        if (x < 0 || x >= Game.WIDTH || y < 0 || y >= Game.HEIGHT) {
            return;
        }
        
        int index = y * Game.WIDTH + x;
        if (depth < this.depths[index]) {
            this.depths[index] = depth;
            this.pixels[index] = color;
        }
    }

    private float edge(float ax, float ay, float bx, float by, float px, float py) {
        return (px - ax) * (by - ay) - (py - ay) * (bx - ax);
    }
}
