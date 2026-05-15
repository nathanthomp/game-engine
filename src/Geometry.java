public class Geometry {
    /**
     * meant to represent model‑space vertices
     */
    public static class Vertex {
        public final float x, y, z, w;

        public Vertex(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.w = 1f;
        }

        public Vertex(float x, float y, float z, float w) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.w = w;
        }
    }

    public static class Triangle {
        public int a, b, c;
        public int color;

        public Triangle(int a, int b, int c, int color) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.color = color;
        }
    }

    public static class TransformedTriangle {
        public final Vertex a, b, c;

        public TransformedTriangle(Vertex a, Vertex b, Vertex c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }

    public static class Mesh {
        public final Vertex[] vertices;
        public final Triangle[] triangles;

        public Mesh(Vertex[] vertices, Triangle[] triangles) {
            this.vertices = vertices;
            this.triangles = triangles;
        }
    }
}
