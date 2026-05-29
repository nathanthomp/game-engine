import java.util.ArrayDeque;
import java.util.Queue;

public final class Manager {

    private static class Change {
        public Scene requestedScene;

        public Change(Scene requestedScene) {
            this.requestedScene = requestedScene;
        }
    }

    // TODO: Make this a stack of scenes to implement pause/resume
    private Scene currentScene;
    private final Queue<Change> changes = new ArrayDeque<>();

    public Manager() {
    }

    public void requestChange(Scene newScene) {
        this.changes.add(new Change(newScene));
    }

    public void processChanges() {
        // If there are no changes, there is nothing to process
        if (this.changes.isEmpty()) {
            return;
        }

        // If there are changes:
        // 1. OnExit the current scene if present
        // 2. Get the first change:
        // 3. onEnter the change scene
        // 4. update the current scene to change scene

        if (this.currentScene != null) {
            this.currentScene.onExit();
        }

        Scene requestedScene = this.changes.poll().requestedScene;
        requestedScene.onEnter();

        this.currentScene = requestedScene;
    }

    public Scene getCurrentScene() {
        return this.currentScene;
    }
}
