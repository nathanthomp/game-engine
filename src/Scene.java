import java.util.ArrayList;
import java.util.List;

/**
 * Represents the model.
 * Can later be extended via MenuScene, GameplayScene, PausedScene
 */
public class Scene {
    private final Input input;

    private final Camera camera = new Camera();
    private final List<Entity> entities = new ArrayList<>();

    public Scene(Input input) {
        this.input = input;
        this.entities.add(new Cube(0, 0, -5));
        this.entities.add(new Cube(0, 5, -5));
        this.entities.add(new Cube(-5, 0, -5));
        this.entities.add(new Cube(0, -5, -5));
        this.entities.add(new Cube(5, 0, -5));
    }

    public Camera getCamera() {
        return this.camera;
    }

    public List<Entity> getEntities() {
        return this.entities;
    }

    /**
     * Model logic for updating world state.
     * Move camera, objects, animations, physics
     */
    public void update(float deltaTime) {

        // float lookSpeed = 0.02f;
        float lookSpeed = 0.002f;
        float moveSpeed = 0.1f;

        camera.yaw += input.getMouseDeltaX() * lookSpeed;
        camera.pitch += input.getMouseDeltaY() * lookSpeed;

        // if (input.isDown(37))
        //     camera.yaw -= lookSpeed; // Left arrow
        // if (input.isDown(39))
        //     camera.yaw += lookSpeed; // Right arrow
        // if (input.isDown(38))
        //     camera.pitch += lookSpeed; // Up arrow
        // if (input.isDown(40))
        //     camera.pitch -= lookSpeed; // Down arrow

        camera.pitch = Math.max(-1.5f, Math.min(1.5f, camera.pitch));

        Transform.Position forward = camera.getForward();
        Transform.Position right = camera.getRight();

        // W = forward
        if (input.isDown(87)) {
            camera.getPosition().x += forward.x * moveSpeed;
            camera.getPosition().y += forward.y * moveSpeed;
            camera.getPosition().z += forward.z * moveSpeed;
        }

        // S = backward
        if (input.isDown(83)) {
            camera.getPosition().x -= forward.x * moveSpeed;
            camera.getPosition().y -= forward.y * moveSpeed;
            camera.getPosition().z -= forward.z * moveSpeed;
        }

        // A = strafe left
        if (input.isDown(65)) {
            camera.getPosition().x -= right.x * moveSpeed;
            camera.getPosition().z -= right.z * moveSpeed;
        }

        // D = strafe right
        if (input.isDown(68)) {
            camera.getPosition().x += right.x * moveSpeed;
            camera.getPosition().z += right.z * moveSpeed;
        }
    }
}
