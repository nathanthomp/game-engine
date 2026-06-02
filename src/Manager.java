import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

public final class Manager {
    private static class Change {
        private enum Type {
            FORWARD,
            BACKWARD,
            RESET
        }

        private Type changeType;
        private Scene requestedScene;

        private Change(Type changeType) {
            this.changeType = changeType;
            this.requestedScene = null;
        }

        private Change(Type changeType, Scene requestedScene) {
            this.changeType = changeType;
            this.requestedScene = requestedScene;
        }
    }

    private final Stack<Scene> sceneStack = new Stack<Scene>();
    private final Queue<Change> changes = new ArrayDeque<>();

    public Scene getCurrentScene() {
        return this.sceneStack.peek();
    }

    public void requestForward(Scene newScene) {
        this.changes.add(new Change(Change.Type.FORWARD, newScene));
    }

    public void requestBackward() {
        this.changes.add(new Change(Change.Type.BACKWARD));
    }

    public void requestReset() {
        this.changes.add(new Change(Change.Type.RESET));
    }

    public void clear() {
        // TODO: Make a destroy method (exit all scenes)
    }

    public void processChanges() {
        while (!this.changes.isEmpty()) {
            Change change = this.changes.poll();
            switch (change.changeType) {
                case Change.Type.FORWARD: {
                    if (!this.sceneStack.empty()) {
                        Scene currentScene = this.sceneStack.peek();
                        currentScene.pause();
                    }
                    this.sceneStack.push(change.requestedScene);
                    change.requestedScene.onEnter();
                    break;
                }
                case Change.Type.BACKWARD: {
                    if (!this.sceneStack.empty()) {
                        Scene currentScene = this.sceneStack.pop();
                        currentScene.onExit();
                        if (!this.sceneStack.empty()) {
                            Scene newCurrentScene = this.sceneStack.peek();
                            newCurrentScene.resume();
                        } else {
                            // There are no scenes left??
                        }
                    }
                    break;
                }
                case Change.Type.RESET: {
                    while (this.sceneStack.size() > 1) {
                        Scene currentScene = this.sceneStack.pop();
                        currentScene.onExit();
                    }
                    this.sceneStack.peek().resume();
                    break;
                }
            }
        }
    }
}
