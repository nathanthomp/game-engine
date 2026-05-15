public class Matrix {
    /**
     * [ m00 m01 m02 m03 ]
     * [ m04 m05 m06 m07 ]
     * [ m08 m09 m10 m11 ]
     * [ m12 m13 m14 m15 ]
     */
    public float[] m = new float[16];

    public Matrix() {
        m[0] = 1;
        m[5] = 1;
        m[10] = 1;
        m[15] = 1;
    }

    public static Matrix translation(Transform.Position position) {
        Matrix matrix = new Matrix();
        matrix.m[12] = position.x;
        matrix.m[13] = position.y;
        matrix.m[14] = position.z;
        return matrix;
    }

    public static Matrix scale(Transform.Scale scale) {
        Matrix matrix = new Matrix();
        matrix.m[0]  = scale.width;
        matrix.m[5]  = scale.height;
        matrix.m[10] = scale.depth;
        return matrix;
    }

    public static Matrix rotation(Transform.Rotation rotation) {
        Matrix rx = rotationX(rotation.pitch);
        Matrix ry = rotationY(rotation.yaw);
        Matrix rz = rotationZ(rotation.roll);
        return rz.multiply(ry).multiply(rx);
    }

    public static Matrix rotationX(float angle) {
        Matrix matrix = new Matrix();
        float cosine = (float)Math.cos(angle);
        float sine = (float)Math.sin(angle);
        matrix.m[5] = cosine;
        matrix.m[6] = sine;
        matrix.m[9] = -sine;
        matrix.m[10] = cosine;
        return matrix;
    }

    public static Matrix rotationY(float angle) {
        Matrix matrix = new Matrix();
        float cosine = (float)Math.cos(angle);
        float sine = (float)Math.sin(angle);
        matrix.m[0] = cosine;
        matrix.m[2] = -sine;
        matrix.m[8] = sine;
        matrix.m[10] = cosine;
        return matrix;
    }

    public static Matrix rotationZ(float angle) {
        Matrix matrix = new Matrix();
        float cosine = (float)Math.cos(angle);
        float sine = (float)Math.sin(angle);
        matrix.m[0] = cosine;
        matrix.m[1] = sine;
        matrix.m[4] = -sine;
        matrix.m[5] = cosine;
        return matrix;
    }

    public Matrix multiply(Matrix other) {
        Matrix matrix = new Matrix();
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                matrix.m[col + row*4] =
                    m[row*4]   * other.m[col]     +
                    m[row*4+1] * other.m[col+4]   +
                    m[row*4+2] * other.m[col+8]   +
                    m[row*4+3] * other.m[col+12];
            }
        }
        return matrix;
    }

    public Geometry.Vertex multiply(Geometry.Vertex vertex) {
        float x = vertex.x, y = vertex.y, z = vertex.z;

        float nx = m[0]*x + m[4]*y + m[8]*z  + m[12];
        float ny = m[1]*x + m[5]*y + m[9]*z  + m[13];
        float nz = m[2]*x + m[6]*y + m[10]*z + m[14];
        float nw = m[3]*x + m[7]*y + m[11]*z + m[15];

        return new Geometry.Vertex(nx, ny, nz, nw);
    }
}
