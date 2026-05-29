import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

public final class Manager {
    private static class Change {
        private enum Type {
            ADD,
            REMOVE,
            REPLACE
        }

        private Type changeType;
        private Scene requestedScene;

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

    public void requestAdd(Scene newScene) {
        this.changes.add(new Change(Change.Type.ADD, newScene));
    }

    public void requestRemove() {
        this.changes.add(new Change(Change.Type.REMOVE, null));
    }

    public void requestReplace(Scene newScene) {
        this.changes.add(new Change(Change.Type.REPLACE, newScene));
    }

    public void processChanges() {
        while (!this.changes.isEmpty()) {
            Change change = this.changes.poll();
            switch (change.changeType) {
                case Change.Type.ADD: {
                    if (!this.sceneStack.empty()) {
                        Scene currentScene = this.sceneStack.peek();
                        currentScene.pause();
                    }
                    this.sceneStack.push(change.requestedScene);
                    change.requestedScene.onEnter();
                    break;
                }
                case Change.Type.REMOVE: {
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
                case Change.Type.REPLACE: {
                    while (!this.sceneStack.empty()) {
                        Scene currentScene = this.sceneStack.pop();
                        currentScene.onExit();
                    }
                    this.sceneStack.push(change.requestedScene);
                    change.requestedScene.onEnter();
                    break;
                }
            }
        }
    }
}
