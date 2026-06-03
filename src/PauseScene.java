import java.util.ArrayList;
import java.util.List;

public class PauseScene extends Scene {
    private final List<Widget> widgets = new ArrayList<Widget>();

    public PauseScene(Manager manager, Input input) {
        super(manager, input);
        int width = 500;
        int height = 50;
        int x = (Game.WIDTH / 2) - (width / 2);
        int y = (Game.HEIGHT / 2) - (height / 2);

        // Resume game button
        Button resumeGameButton = new Button(x, 120, width, height, (() -> manager.requestBackward()));
        this.widgets.add(resumeGameButton);

        Text resumeGameText = new Text(resumeGameButton, Alignment.CENTER_LEFT, "RESUME GAME", Font.Size.MEDIUM);
        this.widgets.add(resumeGameText);

        // Exit game button

        Button exitGameButton = new Button(x, 420, width, height, (() -> manager.requestReset()));
        this.widgets.add(exitGameButton);

        Text exitGameText = new Text(exitGameButton, Alignment.CENTER_RIGHT, "EXIT GAME", Font.Size.MEDIUM);
        this.widgets.add(exitGameText);
    }

    @Override
    public void onEnter() {
        System.out.println("Entering PauseScene");
    }

    @Override
    public void onExit() {
        System.out.println("Exiting PauseScene");
    }

    @Override
    public void pause() {
        System.out.println("Pausing PauseScene");
    }

    @Override
    public void resume() {
        System.out.println("Resuming PauseScene");
    }

    @Override
    public void update(float deltaTime) {
        for (Widget widget : this.widgets) {
            widget.update(this.input);
        }
    }

    @Override
    public void render(Renderer renderer) {
        for (Widget widget : this.widgets) {
            widget.render(renderer);
        }
    }
}
