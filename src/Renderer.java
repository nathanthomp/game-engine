import java.util.Arrays;

/**
 * Represents the view logic.
 * Can be extended to include WireframeRenderer, DebugRenderer, NormalRenderer
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

    public void render(Scene scene) {
        this.clear();

        Matrix viewMatrix = scene.getCamera().getViewMatrix();
        Matrix projectionMatrix = scene.getCamera().getProjectionMatrix();

        for (Entity entity : scene.getEntities()) {
            if (entity instanceof Renderable renderable) {
                Matrix modelMatrix = entity.getTransform().getModelMatrix();
                Matrix mvpMatrix = projectionMatrix.multiply(viewMatrix).multiply(modelMatrix);

                Geometry.Vertex[] transformedVertices = new Geometry.Vertex[renderable.getMesh().vertices.length];
                for (int i = 0; i < renderable.getMesh().vertices.length; i++) {
                    Geometry.Vertex vertex = renderable.getMesh().vertices[i];

                    // 1. Clip-space getPosition()
                    Geometry.Vertex clip = mvpMatrix.multiply(vertex);

                    // 2. Perspective divide → NDC
                    float ndcX = clip.x / clip.w;
                    float ndcY = clip.y / clip.w;
                    float ndcZ = clip.z / clip.w;

                    // 3. Viewport transform → screen space
                    float screenX = (ndcX + 1f) * 0.5f * Game.WIDTH;
                    float screenY = (1f - ndcY) * 0.5f * Game.HEIGHT;

                    transformedVertices[i] = new Geometry.Vertex(screenX, screenY, ndcZ);
                }

                for (Geometry.Triangle triangle : renderable.getMesh().triangles) {
                    Geometry.TransformedTriangle transformedTriangle = new Geometry.TransformedTriangle(
                            transformedVertices[triangle.a],
                            transformedVertices[triangle.b],
                            transformedVertices[triangle.c]);

                    if (!this.isBackface(transformedTriangle)) {
                        this.rasterize(transformedTriangle, triangle.color);
                        this.drawWireframe(transformedTriangle, 0xFFFFFFFF);
                    }
                }
            }
        }
        this.surface.repaint();
    }

    private void clear() {
        Arrays.fill(this.pixels, 0xFF000000); // opaque black
        Arrays.fill(this.depths, Float.POSITIVE_INFINITY);
    }

    private boolean isBackface(Geometry.TransformedTriangle triangle) {;
            float area = (triangle.b.x - triangle.a.x) * (triangle.c.y - triangle.a.y) -
                        (triangle.b.y - triangle.a.y) * (triangle.c.x - triangle.a.x);
            return area >= 0;
    }

    private void rasterize(Geometry.TransformedTriangle triangle, int argb) {
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

    private void drawWireframe(Geometry.TransformedTriangle t, int color) {
        drawLineDepth(t.a.x, t.a.y, t.a.z,
                t.b.x, t.b.y, t.b.z, color);

        drawLineDepth(t.b.x, t.b.y, t.b.z,
                t.c.x, t.c.y, t.c.z, color);

        drawLineDepth(t.c.x, t.c.y, t.c.z,
                t.a.x, t.a.y, t.a.z, color);
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
