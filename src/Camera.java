public class Camera {
    private static final float DEFAULT_FOV = 120.0f;

    private Transform.Position position = new Transform.Position(0, 0, 0);

    private float fov = (float) Math.toRadians(DEFAULT_FOV);
    private float near = 0.1f;
    private float far = 1000f;
    private float aspect = (float) Game.WIDTH / (float) Game.HEIGHT;

    private float yaw = 0f;
    private float pitch = 0f;

    private final float lookSpeed = 0.002f;
    private final float moveSpeed = 0.1f;

    public Transform.Position getPosition() {
        return this.position;
    }

    public Matrix getViewMatrix() {
        Matrix rotationYaw = Matrix.rotationY(-this.yaw);
        Matrix rotationX = Matrix.rotationX(-this.pitch);
        Matrix rotation = rotationX.multiply(rotationYaw);

        Matrix translation = Matrix.translation(
                new Transform.Position(-this.position.x, -this.position.y, -this.position.z));

        return rotation.multiply(translation);
    }

    public Matrix getProjectionMatrix() {
        float f = 1f / (float) Math.tan(this.fov / 2f);
        Matrix matrix = new Matrix();
        matrix.m[0] = f / this.aspect;
        matrix.m[5] = f;
        matrix.m[10] = (this.far + this.near) / (this.near - this.far);
        matrix.m[11] = -1;
        matrix.m[14] = (2f * this.far * this.near) / (this.near - this.far);
        return matrix;
    }

    public void move(Input input) {
        this.yaw += input.getMouseDeltaX() * this.lookSpeed;
        this.pitch += input.getMouseDeltaY() * this.lookSpeed;

        this.pitch = Math.max(-1.5f, Math.min(1.5f, this.pitch));

        if (input.isDown(Input.KEY_W)) {
            this.moveForward(this.moveSpeed);
        }

        if (input.isDown(Input.KEY_S)) {
            this.moveBackward(this.moveSpeed);
        }

        if (input.isDown(Input.KEY_A)) {
            this.moveLeft(this.moveSpeed);
        }

        if (input.isDown(Input.KEY_D)) {
            this.moveRight(this.moveSpeed);
        }
    }

    private Transform.Position getForward() {
        float cosinePitch = (float) Math.cos(this.pitch);
        float sinePitch = (float) Math.sin(this.pitch);
        float cosineYaw = (float) Math.cos(this.yaw);
        float sineYaw = (float) Math.sin(this.yaw);

        return new Transform.Position(
                sineYaw * cosinePitch,
                -sinePitch,
                -cosineYaw * cosinePitch);
    }

    private Transform.Position getRight() {
        float cosineYaw = (float) Math.cos(this.yaw);
        float sineYaw = (float) Math.sin(this.yaw);

        return new Transform.Position(
                cosineYaw,
                0,
                sineYaw);
    }

    private Transform.Position getUp() {
        Transform.Position forwardPosition = getForward();
        Transform.Position rightPosition = getRight();

        return new Transform.Position(
                rightPosition.y * forwardPosition.z - rightPosition.z * forwardPosition.y,
                rightPosition.z * forwardPosition.x - rightPosition.x * forwardPosition.z,
                rightPosition.x * forwardPosition.y - rightPosition.y * forwardPosition.x);
    }

    private void moveForward(float distance) {
        Transform.Position forward = this.getForward();
        this.position.x += forward.x * distance;
        this.position.y += forward.y * distance;
        this.position.z += forward.z * distance;
    }

    private void moveBackward(float distance) {
        Transform.Position forward = this.getForward();
        this.position.x -= forward.x * distance;
        this.position.y -= forward.y * distance;
        this.position.z -= forward.z * distance;
    }

    private void moveRight(float distance) {
        Transform.Position right = this.getRight();
        // this.position.x += right.x * distance;
        // this.position.y += right.y * distance;
        // this.position.z += right.z * distance;
        this.position.x += right.x * distance;
        this.position.z += right.z * distance;
    }

    private void moveLeft(float distance) {
        Transform.Position right = this.getRight();
        // this.position.x -= right.x * distance;
        // this.position.y -= right.y * distance;
        // this.position.z -= right.z * distance;
        this.position.x -= right.x * distance;
        this.position.z -= right.z * distance;
    }
}
