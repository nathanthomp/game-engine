public abstract class Scene {
    protected final Manager manager;
    protected final Input input;

    public Scene(Manager manager, Input input) {
        this.manager = manager;
        this.input = input;
    }

    public abstract void onEnter();
    public abstract void onExit();
    public abstract void pause();
    public abstract void resume();
    public abstract void update(float deltaTime);
    public abstract void render(Renderer renderer);
}
