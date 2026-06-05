import java.util.ArrayList;
import java.util.List;

public final class MenuScene extends Scene {
    private final List<Widget> widgets = new ArrayList<Widget>();

    public MenuScene(Manager manager, Input input) {
        super(manager, input);

        int startGameButtonWidth = 500;
        int startGameButtonHeight = 50;
        int startGameButtonX = (Game.WIDTH / 2) - (startGameButtonWidth / 2); // 150
        int startGameButtonY = (Game.HEIGHT / 2) - (startGameButtonHeight / 2); // 275

        Button startGameButton = new Button(startGameButtonX, startGameButtonY, startGameButtonWidth,
                startGameButtonHeight, (() -> manager.requestForward(new PlayScene(manager, input))));
        this.widgets.add(startGameButton);

        Text startGameText = new Text(startGameButton, Alignment.CENTER_CENTER, "START GAME", Font.Size.MEDIUM);
        this.widgets.add(startGameText);
    }

    @Override
    public void onEnter() {
        System.out.println("Entering MenuScene");
    }

    @Override
    public void onExit() {
        System.out.println("Exiting MenuScene");
    }

    @Override
    public void pause() {
        System.out.println("Pausing MenuScene");
    }

    @Override
    public void resume() {
        System.out.println("Resuming MenuScene");
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
