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
        this.camera.move(this.input);

        if (this.input.isDown(Input.KEY_ESCAPE)) {
            this.manager.requestForward(new PauseScene(this.manager, this.input));
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
        renderer.drawLine(Game.WIDTH / 2 - 10, Game.HEIGHT / 2,
                Game.WIDTH / 2 + 10, Game.HEIGHT / 2,
                0xFFFFFFFF);

        renderer.drawLine(Game.WIDTH / 2, Game.HEIGHT / 2 - 10,
                Game.WIDTH / 2, Game.HEIGHT / 2 + 10,
                0xFFFFFFFF);
    }
}
