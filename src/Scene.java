public abstract class Scene {

    /**
     * Called when the scene is entered. This is where you should initialize your
     * scene and add any widgets or whatever you need to the scene.
     * 
     * @param manager The manager that is managing this scene.
     */
    public abstract void onEnter(Manager manager);

    public abstract void onExit();

    public abstract void pause();

    public abstract void resume();

    /**
     * Called every frame to update the scene. This is where you should update
     * any entities or widgets in the scene.
     * 
     * @param manager   The manager that is managing this scene.
     * @param deltaTime The time since the last frame, in seconds.
     */
    public abstract void update(float deltaTime);

    public abstract void render(Renderer renderer);
}
