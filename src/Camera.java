public class Camera {
    private static final float DEFAULT_FOV = 60.0f;

    private Transform.Position position = new Transform.Position(0, 0, 0);
    private Transform.Rotation rotation = new Transform.Rotation(0, 0, 0);

    private float fov = (float) Math.toRadians(DEFAULT_FOV);
    private float near = 0.1f;
    private float far = 1000f;
    private float aspect = 1.0f;

    public Transform.Position getPosition() {
        return this.position;
    }

    public Matrix getViewMatrix() {
        // View = inverse(Translation * Rotation)
        Matrix translation = Matrix.translation(
            new Transform.Position(-this.position.x, -this.position.y, -this.position.z));

        Matrix rotation = Matrix.rotation(
            new Transform.Rotation(-this.rotation.pitch, -this.rotation.yaw, -this.rotation.roll));

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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getForward'");
    }

    public Transform.Position getRight() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRight'");
    }

    // public void move(float dx, float dy, float dz) {
    //     this.position.x += dx;
    //     this.position.y += dy;
    //     this.position.z += dz;
    // }

    
}
