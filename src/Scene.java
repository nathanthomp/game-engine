public class Scene {
    /**
     * Represents the model. Specifically what exists which could include the world
     * state and data.
     * Can later be extended via MenuScene, GameplayScene, PausedScene
     * 
     * Collection of renderables
     * Camera
     */

    private float x = 100;
    private float y = 100;
    private float speed = 200; // pixels per second

    private final Input input;

    public Scene(Input input) {
        this.input = input;
    }

    public void update(float deltaTime) {
        /**
         * Model logic for updating world state
         */
        float dx = 0, dy = 0;

        if (this.input.isDown(87)) { // W
            dy -= 1;
        }
        if (this.input.isDown(83)) { // S
            dy += 1;
        }
        if (this.input.isDown(65)) { // A
            dx -= 1;
        }
        if (this.input.isDown(68)) { // D
            dx += 1;
        }

        float length = (float) Math.sqrt(dx * dx + dy * dy);

        if (length != 0) {
            dx /= length;
            dy /= length;
        }

        x += dx * speed * deltaTime;
        y += dy * speed * deltaTime;
    }

    public void render(Renderer renderer) {
        renderer.clear();
        renderer.drawRectangle((int) x, (int) y, 40, 40, 0xFFFF0000);
    }
}
