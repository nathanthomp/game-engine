public final class Manager {
    private Scene currentScene;

    public Manager(Scene initialScene) {
        this.currentScene = initialScene;
    }

    public Scene getCurrentScene() {
        return this.currentScene;
    }
}
