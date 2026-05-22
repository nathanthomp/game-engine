public class Cube extends Entity implements Renderable {

    private Geometry.Mesh mesh;
    private Transform transform;

    public Cube(int x, int y, int z) {
        super();
        this.mesh = this.build();
        this.transform = new Transform(
            new Transform.Position(x, y, z),
            new Transform.Rotation(0, 0, 0),
            new Transform.Scale(1, 1, 1)
        );
    }

    public Geometry.Mesh build() {
        Geometry.Vertex[] vertices = new Geometry.Vertex[] {
            new Geometry.Vertex(-1, -1, -1), // 0
            new Geometry.Vertex(1, -1, -1), // 1
            new Geometry.Vertex(1, 1, -1), // 2
            new Geometry.Vertex(-1, 1, -1), // 3

            new Geometry.Vertex(-1, -1, 1), // 4
            new Geometry.Vertex(1, -1, 1), // 5
            new Geometry.Vertex(1, 1, 1), // 6
            new Geometry.Vertex(-1, 1, 1) // 7
        };

        Geometry.Triangle[] triangles = new Geometry.Triangle[] {
                // Front (+Z) — RED
                new Geometry.Triangle(4, 5, 6, 0xFFFF0000),
                new Geometry.Triangle(4, 6, 7, 0xFFFF0000),

                // Back (–Z) — GREEN
                new Geometry.Triangle(0, 2, 1, 0xFF00FF00),
                new Geometry.Triangle(0, 3, 2, 0xFF00FF00),

                // Left (–X) — BLUE
                new Geometry.Triangle(0, 7, 3, 0xFF0000FF),
                new Geometry.Triangle(0, 4, 7, 0xFF0000FF),

                // Right (+X) — YELLOW
                new Geometry.Triangle(1, 2, 6, 0xFFFFFF00),
                new Geometry.Triangle(1, 6, 5, 0xFFFFFF00),

                // Top (+Y) — CYAN
                new Geometry.Triangle(3, 7, 6, 0xFF00FFFF),
                new Geometry.Triangle(3, 6, 2, 0xFF00FFFF),

                // Bottom (–Y) — MAGENTA
                new Geometry.Triangle(0, 1, 5, 0xFFFF00FF),
                new Geometry.Triangle(0, 5, 4, 0xFFFF00FF)
        };

        return new Geometry.Mesh(vertices, triangles);
    }

    @Override
    public Geometry.Mesh getMesh() {
        return this.mesh;
    }

    @Override
    public Transform getTransform() {
        return this.transform;
    }
}
