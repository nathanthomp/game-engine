import java.util.ArrayList;
import java.util.List;

/**
 * Represents the model.
 * Can later be extended via MenuScene, GameplayScene, PausedScene
 */
public class Scene {
    private final Input input;

    private final Camera camera = new Camera();
    private final List<Entity> entities = new ArrayList<>();

    public Scene(Input input) {
        this.input = input;
        this.entities.add(new Cube(0, 0, -5));
        this.entities.add(new Cube(0, 5, -5));
        this.entities.add(new Cube(-5, 0, -5));
        this.entities.add(new Cube(0, -5, -5));
        this.entities.add(new Cube(5, 0, -5));
    }

    public Camera getCamera() {
        return this.camera;
    }

    public List<Entity> getEntities() {
        return this.entities;
    }

    /**
     * Model logic for updating world state.
     * Move camera, objects, animations, physics
     */
    public void update(float deltaTime) {

        // float lookSpeed = 0.02f;
        float lookSpeed = 0.002f;
        float moveSpeed = 0.1f;

        camera.yaw += input.getMouseDeltaX() * lookSpeed;
        camera.pitch += input.getMouseDeltaY() * lookSpeed;

        // if (input.isDown(37))
        //     camera.yaw -= lookSpeed; // Left arrow
        // if (input.isDown(39))
        //     camera.yaw += lookSpeed; // Right arrow
        // if (input.isDown(38))
        //     camera.pitch += lookSpeed; // Up arrow
        // if (input.isDown(40))
        //     camera.pitch -= lookSpeed; // Down arrow

        camera.pitch = Math.max(-1.5f, Math.min(1.5f, camera.pitch));

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
        Matrix viewMatrix = this.getCamera().getViewMatrix();
        Matrix projectionMatrix = this.getCamera().getProjectionMatrix();

        for (Entity entity : this.getEntities()) {
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

                    if (!renderer.isBackface(transformedTriangle)) {
                        renderer.rasterize(transformedTriangle, triangle.color);
                        renderer.drawWireframe(transformedTriangle, 0xFFFFFFFF);
                    }
                }
            }
        }

        /**
         * Rendering 2D overlays
         */
        renderer.drawLine(Game.WIDTH/2 - 10, Game.HEIGHT/2,
            Game.WIDTH/2 + 10, Game.HEIGHT/2,
            0xFFFFFFFF);

        renderer.drawLine(Game.WIDTH/2, Game.HEIGHT/2 - 10,
            Game.WIDTH/2, Game.HEIGHT/2 + 10,
            0xFFFFFFFF);

        renderer.renderRectangle(5, 5, Game.WIDTH - 10, Game.HEIGHT - 10, 0xFF00FF00, false);
    }

    public void dispose() {
        // Clean up resources if needed
    }
}
