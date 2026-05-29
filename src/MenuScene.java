import java.util.ArrayList;
import java.util.List;

public final class MenuScene extends Scene {
    private final List<Widget> widgets = new ArrayList<Widget>();

    public MenuScene(Manager manager, Input input) {
        super(manager, input);
        // HorizontalCenter = (containerWidth / 2) - (localWidth / 2)
        // VerticalCenter = (containerHeight / 2) - (localHeight / 2)
        int width = 500;
        int height = 50;
        int x = (Game.WIDTH / 2) - (width / 2);
        int y = (Game.HEIGHT / 2) - (height / 2);
        this.widgets.add(new Widget(x, y, width, height));
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
    public void update(float deltaTime) {
        for (Widget widget : this.widgets) {
            widget.update(this.manager, this.input);
        }
    }

    @Override
    public void render(Renderer renderer) {
        for (Widget widget : this.widgets) {
            widget.render(renderer);
        }
    }  
}
