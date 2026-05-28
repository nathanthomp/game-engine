public abstract class Scene {
    protected final Input input;

    public Scene(Input input) {
        this.input = input;
    }

    public abstract void update(float deltaTime);
    public abstract void render(Renderer renderer);
    public abstract void dispose();
}
