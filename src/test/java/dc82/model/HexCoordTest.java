package dc82.model;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class HexCoordTest {

    @Test
    void originDistanceToSelf() {
        assertEquals(0, HexCoord.ORIGIN.distanceTo(HexCoord.ORIGIN));
    }

    @Test
    void distanceBetweenAdjacent() {
        assertEquals(1, HexCoord.ORIGIN.distanceTo(new HexCoord(1, 0)));
        assertEquals(1, HexCoord.ORIGIN.distanceTo(new HexCoord(0, 1)));
        assertEquals(1, HexCoord.ORIGIN.distanceTo(new HexCoord(-1, 0)));
        assertEquals(1, HexCoord.ORIGIN.distanceTo(new HexCoord(0, -1)));
    }

    @Test
    void distanceSymmetry() {
        var a = new HexCoord(3, -2);
        var b = new HexCoord(-1, 4);
        assertEquals(a.distanceTo(b), b.distanceTo(a));
    }

    @Test
    void hexesInRangeMinMax() {
        List<HexCoord> range = HexCoord.ORIGIN.hexesInRange(1, 1);
        assertEquals(6, range.size());
    }

    @Test
    void hexesInRangeExcludesSelf() {
        List<HexCoord> range = HexCoord.ORIGIN.hexesInRange(1, 2);
        assertFalse(range.contains(HexCoord.ORIGIN));
    }

    @Test
    void pixelX() {
        assertEquals(0f, HexCoord.ORIGIN.pixelX());
        assertEquals(1.5f, new HexCoord(1, 0).pixelX());
        assertEquals(-3f, new HexCoord(-2, 0).pixelX());
    }

    @Test
    void lineToAdjacent() {
        List<HexCoord> line = HexCoord.ORIGIN.lineTo(new HexCoord(1, 0));
        assertEquals(2, line.size());
        assertEquals(HexCoord.ORIGIN, line.get(0));
        assertEquals(new HexCoord(1, 0), line.get(1));
    }

    @Test
    void lineToSame() {
        List<HexCoord> line = HexCoord.ORIGIN.lineTo(HexCoord.ORIGIN);
        assertEquals(1, line.size());
        assertEquals(HexCoord.ORIGIN, line.get(0));
    }
}
