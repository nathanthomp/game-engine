public class Transform {
    public static class Position {
        public float x, y, z;

        public Position(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    public static class Rotation {
        public float pitch, yaw, roll;

        public Rotation(float pitch, float yaw, float roll) {
            this.pitch = pitch;
            this.yaw = yaw;
            this.roll = roll;
        }
    }

    public static class Scale {
        public float width, height, depth;

        public Scale(float width, float height, float depth) {
            this.width = width;
            this.height = height;
            this.depth = depth;
        }
    }

    private final Position position;
    private final Rotation rotation;
    private final Scale scale;

    public Transform(Position position, Rotation rotation, Scale scale) {
        this.position = position; // Could be overriden
        this.rotation = rotation; // Should be 0, 0, 0 for no rotation
        this.scale = scale; // Should be 1, 1, 1 for no scaling
    }

    public Matrix getModelMatrix() {
        Matrix t = Matrix.translation(position);
        Matrix r = Matrix.rotation(rotation);
        Matrix s = Matrix.scale(scale);
        return t.multiply(r).multiply(s);
    }
}
