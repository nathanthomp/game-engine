import java.util.ArrayList;
import java.util.List;

/**
 * Represents the model.
 * Can later be extended via MenuScene, GameplayScene, PausedScene
 */
public class Scene {

    private static class Cube implements Renderable {

        private Geometry.Mesh mesh;
        private Transform transform;

        public Cube() {
            Geometry.Vertex[] vertices = new Geometry.Vertex[] {
                    new Geometry.Vertex(-1, -1, -1), // 0
                    new Geometry.Vertex(1, -1, -1), // 1
                    new Geometry.Vertex(1, 1, -1), // 2
                    new Geometry.Vertex(-1, 1, -1), // 3

                    new Geometry.Vertex(-1, -1, 1), // 4
                    new Geometry.Vertex(1, -1, 1), // 5
                    new Geometry.Vertex(1, 1, 1), // 6
                    new Geometry.Vertex(-1, 1, 1) // 7
            };

            Geometry.Triangle[] triangles = new Geometry.Triangle[] {
                    // Front (+Z) — RED
                    new Geometry.Triangle(4, 5, 6, 0xFFFF0000),
                    new Geometry.Triangle(4, 6, 7, 0xFFFF0000),

                    // Back (–Z) — GREEN
                    new Geometry.Triangle(0, 2, 1, 0xFF00FF00),
                    new Geometry.Triangle(0, 3, 2, 0xFF00FF00),

                    // Left (–X) — BLUE
                    new Geometry.Triangle(0, 7, 3, 0xFF0000FF),
                    new Geometry.Triangle(0, 4, 7, 0xFF0000FF),

                    // Right (+X) — YELLOW
                    new Geometry.Triangle(1, 2, 6, 0xFFFFFF00),
                    new Geometry.Triangle(1, 6, 5, 0xFFFFFF00),

                    // Top (+Y) — CYAN
                    new Geometry.Triangle(3, 7, 6, 0xFF00FFFF),
                    new Geometry.Triangle(3, 6, 2, 0xFF00FFFF),

                    // Bottom (–Y) — MAGENTA
                    new Geometry.Triangle(0, 1, 5, 0xFFFF00FF),
                    new Geometry.Triangle(0, 5, 4, 0xFFFF00FF)
            };

            this.mesh = new Geometry.Mesh(vertices, triangles);

            this.transform = new Transform(
                    new Transform.Position(0, 0, -5),
                    new Transform.Rotation(0, 0, 0),
                    new Transform.Scale(1, 1, 1));
        }

        @Override
        public Geometry.Mesh getMesh() {
            return this.mesh;
        }

        @Override
        public Transform getTransform() {
            return this.transform;
        }
    }

    private final Camera camera = new Camera();
    private final List<Renderable> renderables = new ArrayList<>();
    private final Input input;

    private float speed = 0.1f;

    public Scene(Input input) {
        this.input = input;
        this.renderables.add(new Cube());
    }

    /**
     * Model logic for updating world state.
     * Move camera, objects, animations, physics
     */
    public void update(float deltaTime) {

        float lookSpeed = 0.02f;
        float moveSpeed = 0.1f;

        // ----------------------------
        // ROTATION (arrow keys ONLY)
        // ----------------------------

        if (input.isDown(37))
            camera.yaw -= lookSpeed; // Left arrow
        if (input.isDown(39))
            camera.yaw += lookSpeed; // Right arrow
        if (input.isDown(38))
            camera.pitch += lookSpeed; // Up arrow
        if (input.isDown(40))
            camera.pitch -= lookSpeed; // Down arrow

        // Clamp pitch
        camera.pitch = Math.max(-1.5f, Math.min(1.5f, camera.pitch));

        // ----------------------------
        // MOVEMENT (WASD ONLY)
        // ----------------------------

        Transform.Position forward = camera.getForward();
        Transform.Position right = camera.getRight();

        // W = forward
        if (input.isDown(87)) {
            camera.getPosition().x += forward.x * moveSpeed;
            camera.getPosition().y += forward.y * moveSpeed;
            camera.getPosition().z += forward.z * moveSpeed;
        }

        // S = backward
        if (input.isDown(83)) {
            camera.getPosition().x -= forward.x * moveSpeed;
            camera.getPosition().y -= forward.y * moveSpeed;
            camera.getPosition().z -= forward.z * moveSpeed;
        }

        // A = strafe left
        if (input.isDown(65)) {
            camera.getPosition().x -= right.x * moveSpeed;
            camera.getPosition().z -= right.z * moveSpeed;
        }

        // D = strafe right
        if (input.isDown(68)) {
            camera.getPosition().x += right.x * moveSpeed;
            camera.getPosition().z += right.z * moveSpeed;
        }
    }

    public void render(Renderer renderer) {
        renderer.clear();

        Matrix viewMatrix = this.camera.getViewMatrix();
        Matrix projectionMatrix = this.camera.getProjectionMatrix();

        for (Renderable renderable : renderables) {
            Matrix modelMatrix = renderable.getTransform().getModelMatrix();
            Matrix mvpMatrix = modelMatrix.multiply(viewMatrix).multiply(projectionMatrix);

            // 3.3 Transform ALL vertices once → screen space
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

            // 3.4 For each triangle:
            for (Geometry.Triangle triangle : renderable.getMesh().triangles) {
                Geometry.TransformedTriangle transformedTriangle = new Geometry.TransformedTriangle(
                        transformedVertices[triangle.a],
                        transformedVertices[triangle.b],
                        transformedVertices[triangle.c]);

                if (!renderer.isBackface(transformedTriangle)) {
                    renderer.rasterize(transformedTriangle, triangle.color);
                    renderer.drawWireframe(transformedTriangle, 0xFFFFFFFF);
                }
            }
        }
    }
}
