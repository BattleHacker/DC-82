package dc82.model;

public class Path {

    public HexCoord a;
    public HexCoord b;
    public int distance;

    public Path() {}

    public Path(HexCoord a, HexCoord b) {
        this.a = a;
        this.b = b;
        this.distance = a.distanceTo(b);
    }

    public boolean connects(HexCoord hex) {
        return a.equals(hex) || b.equals(hex);
    }

    public HexCoord other(HexCoord hex) {
        if (a.equals(hex)) return b;
        if (b.equals(hex)) return a;
        return null;
    }
}
