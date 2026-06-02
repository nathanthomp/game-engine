import java.util.ArrayList;
import java.util.List;

public final class PlayScene extends Scene {
    private final Camera camera = new Camera();
    private final List<Entity> entities = new ArrayList<Entity>();

    public PlayScene(Manager manager, Input input) {
        super(manager, input);
        this.entities.add(new Cube(0, 0, -5));
        this.entities.add(new Cube(0, 5, -5));
        this.entities.add(new Cube(-5, 0, -5));
        this.entities.add(new Cube(0, -5, -5));
        this.entities.add(new Cube(5, 0, -5));
    }

    @Override
    public void onEnter() {
        System.out.println("Entering PlayScene");
    }

    @Override
    public void onExit() {
        System.out.println("Exiting PlayScene");
    }

    @Override
    public void pause() {
        System.out.println("Pausing PlayScene");
    }

    @Override
    public void resume() {
        System.out.println("Resuming PlayScene");
    }

    @Override
    public void update(float deltaTime) {
        float lookSpeed = 0.002f;
        float moveSpeed = 0.1f;

        camera.yaw += this.input.getMouseDeltaX() * lookSpeed;
        camera.pitch += this.input.getMouseDeltaY() * lookSpeed;

        camera.pitch = Math.max(-1.5f, Math.min(1.5f, camera.pitch));

        Transform.Position forward = camera.getForward();
        Transform.Position right = camera.getRight();

        if (this.input.isDown(27)) {
            // Add a PauseScene
            this.manager.requestForward(new PauseScene(this.manager, this.input));
        }

        // W = forward
        if (this.input.isDown(87)) {
            camera.getPosition().x += forward.x * moveSpeed;
            camera.getPosition().y += forward.y * moveSpeed;
            camera.getPosition().z += forward.z * moveSpeed;
        }

        // S = backward
        if (this.input.isDown(83)) {
            camera.getPosition().x -= forward.x * moveSpeed;
            camera.getPosition().y -= forward.y * moveSpeed;
            camera.getPosition().z -= forward.z * moveSpeed;
        }

        // A = strafe left
        if (this.input.isDown(65)) {
            camera.getPosition().x -= right.x * moveSpeed;
            camera.getPosition().z -= right.z * moveSpeed;
        }

        // D = strafe right
        if (this.input.isDown(68)) {
            camera.getPosition().x += right.x * moveSpeed;
            camera.getPosition().z += right.z * moveSpeed;
        }
    }

    @Override
    public void render(Renderer renderer) {
        Matrix viewMatrix = this.camera.getViewMatrix();
        Matrix projectionMatrix = this.camera.getProjectionMatrix();

        for (Entity entity : this.entities) {
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
                        // renderer.drawWireframe(transformedTriangle, 0xFFFFFFFF);
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
}
