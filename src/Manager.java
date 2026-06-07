import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

public final class Manager {
    private static class Change {
        private enum Action {
            FORWARD,
            BACKWARD,
            RESET
        }

        private Action changeAction;
        private Scene requestedScene;

        private Change(Action changeAction) {
            this.changeAction = changeAction;
            this.requestedScene = null;
        }

        private Change(Action changeAction, Scene requestedScene) {
            this.changeAction = changeAction;
            this.requestedScene = requestedScene;
        }
    }

    private final Stack<Scene> sceneStack = new Stack<Scene>();
    private final Queue<Change> changes = new ArrayDeque<>();

    public Scene getCurrentScene() {
        return this.sceneStack.peek();
    }

    public void requestForward(Scene newScene) {
        this.changes.add(new Change(Change.Action.FORWARD, newScene));
    }

    public void requestBackward() {
        this.changes.add(new Change(Change.Action.BACKWARD));
    }

    public void requestReset() {
        this.changes.add(new Change(Change.Action.RESET));
    }

    public void clear() {
        // TODO: Make a destroy method (exit all scenes)
    }

    public void processChanges() {
        while (!this.changes.isEmpty()) {
            Change change = this.changes.poll();
            switch (change.changeAction) {
                case Change.Action.FORWARD: {
                    if (!this.sceneStack.empty()) {
                        Scene currentScene = this.sceneStack.peek();
                        currentScene.pause();
                    }
                    this.sceneStack.push(change.requestedScene);
                    change.requestedScene.onEnter(this);
                    break;
                }
                case Change.Action.BACKWARD: {
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
                case Change.Action.RESET: {
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
