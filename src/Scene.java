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
                new Geometry.Vertex( 1, -1, -1), // 1
                new Geometry.Vertex( 1,  1, -1), // 2
                new Geometry.Vertex(-1,  1, -1), // 3

                new Geometry.Vertex(-1, -1,  1), // 4
                new Geometry.Vertex( 1, -1,  1), // 5
                new Geometry.Vertex( 1,  1,  1), // 6
                new Geometry.Vertex(-1,  1,  1)  // 7
            };

            Geometry.Triangle[] triangles = new Geometry.Triangle[] {
                // Front face (z = +1)
                new Geometry.Triangle(4, 5, 6),
                new Geometry.Triangle(4, 6, 7),

                // Back face (z = -1)
                new Geometry.Triangle(0, 2, 1),
                new Geometry.Triangle(0, 3, 2),

                // Left face (x = -1)
                new Geometry.Triangle(0, 7, 3),
                new Geometry.Triangle(0, 4, 7),

                // Right face (x = +1)
                new Geometry.Triangle(1, 2, 6),
                new Geometry.Triangle(1, 6, 5),

                // Top face (y = +1)
                new Geometry.Triangle(3, 7, 6),
                new Geometry.Triangle(3, 6, 2),

                // Bottom face (y = -1)
                new Geometry.Triangle(0, 1, 5),
                new Geometry.Triangle(0, 5, 4)
            };

            this.mesh = new Geometry.Mesh(vertices, triangles);

            this.transform = new Transform(
                new Transform.Position(0, 0, 5), 
                new Transform.Rotation(0, 0, 0),
                new Transform.Scale(1, 1, 1)
            );
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
        // float dx = 0, dy = 0;

        if (this.input.isDown(87)) { // W
            Transform.Position direction = camera.getForward();
            camera.getPosition().x += direction.x * speed;
            camera.getPosition().y += direction.y * speed;
            camera.getPosition().z += direction.z * speed;
            // dy -= 1;
        }
        if (this.input.isDown(83)) { // S
            // this.camera.move(Direction.FORWARD.multiply(-speed));
            Transform.Position direction = camera.getForward();
            camera.getPosition().x -= direction.x * speed;
            camera.getPosition().y -= direction.y * speed;
            camera.getPosition().z -= direction.z * speed;
            // dy += 1;
        }
        if (this.input.isDown(65)) { // A
            Transform.Position right = camera.getRight();
            camera.getPosition().x -= right.x * speed;
            camera.getPosition().z -= right.z * speed;
            // dx -= 1;
        }
        if (this.input.isDown(68)) { // D
            Transform.Position right = camera.getRight();
            camera.getPosition().x += right.x * speed;
            camera.getPosition().z += right.z * speed;
            // dx += 1;
        }

        /**
         * if (keySpace) {
                camera.getPosition().y += speed;
            }

            if (keyCtrl) {
                camera.getPosition().y -= speed;
            }
         */

    }

    /**
        1. Clear screen (color + depth)
        2. Get camera view + projection matrices
        3. For each renderable:
            3.1 Compute model matrix
            3.2 Compute MVP = projection * view * model
            3.3 For each triangle:
                3.3.1 Transform vertices to clip space
                3.3.2 Perspective divide → NDC
                3.3.3 Viewport transform → screen space
     */
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

                // 1. Clip-space position
                Geometry.Vertex clip = mvpMatrix.multiply(vertex);

                // 2. Perspective divide → NDC
                float ndcX = clip.x / clip.w;
                float ndcY = clip.y / clip.w;
                float ndcZ = clip.z / clip.w; 

                // 3. Viewport transform → screen space
                int screenX = (int)((ndcX + 1f) * 0.5f * Game.WIDTH);
                int screenY = (int)((1f - ndcY) * 0.5f * Game.HEIGHT);

                transformedVertices[i] = new Geometry.Vertex(screenX, screenY, ndcZ);
            }

            // 3.4 For each triangle:
            for (Geometry.Triangle triangle : renderable.getMesh().triangles) {
                Geometry.TransformedTriangle transformedTriangle = new Geometry.TransformedTriangle (
                    transformedVertices[triangle.a],
                    transformedVertices[triangle.b],
                    transformedVertices[triangle.c]
                );

                if (!renderer.isBackface(transformedTriangle)) {
                    renderer.rasterize(transformedTriangle, 0xFFFF00FF);
                }
            }
        }
    }
}
