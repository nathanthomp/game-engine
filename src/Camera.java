public class Camera {
    private static final float DEFAULT_FOV = 60.0f;

    private Transform.Position position = new Transform.Position(0, 0, 0);

    private float fov = (float) Math.toRadians(DEFAULT_FOV);
    private float near = 0.1f;
    private float far = 1000f;
    private float aspect = (float) Game.WIDTH / (float) Game.HEIGHT;

    public float yaw = 0f;
    public float pitch = 0f;

    public Transform.Position getPosition() {
        return this.position;
    }

    public Matrix getViewMatrix() {
        Matrix rotY = Matrix.rotationY(-yaw);
        Matrix rotX = Matrix.rotationX(-pitch);
        Matrix rotation = rotX.multiply(rotY);

        Matrix translation = Matrix.translation(
                new Transform.Position(-position.x, -position.y, -position.z));

        return rotation.multiply(translation);
    }

    public Matrix getProjectionMatrix() {
        float f = 1f / (float) Math.tan(fov / 2f);
        Matrix matrix = new Matrix();
        matrix.m[0] = f / aspect;
        matrix.m[5] = f;
        matrix.m[10] = (this.far + this.near) / (this.near - this.far);
        matrix.m[11] = -1;
        matrix.m[14] = (2f * this.far * this.near) / (this.near - this.far);
        return matrix;
    }

    public Transform.Position getForward() {
        float cp = (float) Math.cos(pitch);
        float sp = (float) Math.sin(pitch);
        float cy = (float) Math.cos(yaw);
        float sy = (float) Math.sin(yaw);

        return new Transform.Position(
                sy * cp,
                -sp,
                -cy * cp);
    }

    public Transform.Position getRight() {
        float cy = (float) Math.cos(yaw);
        float sy = (float) Math.sin(yaw);

        return new Transform.Position(
                cy,
                0,
                sy);
    }

    public Transform.Position getUp() {
        Transform.Position f = getForward();
        Transform.Position r = getRight();

        // up = right × forward
        return new Transform.Position(
                r.y * f.z - r.z * f.y,
                r.z * f.x - r.x * f.z,
                r.x * f.y - r.y * f.x);
    }

    // public void move(float dx, float dy, float dz) {
    // this.position.x += dx;
    // this.position.y += dy;
    // this.position.z += dz;
    // }

}
