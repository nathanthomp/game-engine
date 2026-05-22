public abstract class Entity {
    private static int nextId = 0;

    public static int getNextId() {
        return nextId++;
    }

    private final int id;

    public Entity() {
        this.id = Entity.getNextId();
    }

    public abstract Transform getTransform();

    public int getId() {
        return this.id;
    }
}
