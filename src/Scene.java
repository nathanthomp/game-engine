public class Scene {
    /**
     * Represents the model. Specifically what exists which could include the world state and data.
     * Can later be extended via MenuScene, GameplayScene, PausedScene
     * 
     * Collection of renderables
     * Camera
     */

    public void update(float deltaTime) {
        /**
         * Model logic for updating world state
         */
    }

    public void render(Renderer renderer) {
        renderer.clear();
        renderer.drawBorder();
    }
}
