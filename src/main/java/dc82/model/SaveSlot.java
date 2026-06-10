package dc82.model;

public class SaveSlot {
    public String id;
    public String displayName;
    public long createdAt;
    public long updatedAt;
    public GameState gameState = new GameState();

    public SaveSlot() {}

    public SaveSlot(String id, String displayName) {
        this.id = id;
        this.displayName = displayName;
        long now = System.currentTimeMillis();
        this.createdAt = now;
        this.updatedAt = now;
    }
}
