package dc82.util;

import dc82.model.HexCoord;
import dc82.model.MapState;
import dc82.model.Milestone;
import dc82.model.Path;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MapGeneratorTest {

    @Test
    void startNewMapHasCurrent() {
        MapState state = MapState.createStartingState();
        MapGenerator.generateNeighbors(state);
        assertNotNull(state.currentMilestone());
    }

    @Test
    void startNewMapCreatesPaths() {
        MapState state = MapState.createStartingState();
        MapGenerator.generateNeighbors(state);
        assertFalse(state.allPaths.isEmpty());
    }

    @Test
    void firstMilestoneHasFourNeighbors() {
        MapState state = MapState.createStartingState();
        MapGenerator.generateNeighbors(state);
        Milestone first = state.currentMilestone();
        assertEquals(4, first.paths.size());
    }

    @Test
    void noCrossingPaths() {
        MapState state = MapState.createStartingState();
        MapGenerator.generateNeighbors(state);
        for (Path p1 : state.allPaths) {
            for (Path p2 : state.allPaths) {
                if (p1 == p2) continue;
                if (segmentsCross(p1, p2)) {
                    fail("Crossing paths: " + p1 + " and " + p2);
                }
            }
        }
    }

    private static boolean segmentsCross(Path p1, Path p2) {
        if (p1.connects(p2.a) || p1.connects(p2.b)) return false;
        float o1 = orient(p1.a, p1.b, p2.a);
        float o2 = orient(p1.a, p1.b, p2.b);
        float o3 = orient(p2.a, p2.b, p1.a);
        float o4 = orient(p2.a, p2.b, p1.b);
        return o1 * o2 < 0f && o3 * o4 < 0f;
    }

    private static float orient(HexCoord p, HexCoord q, HexCoord r) {
        return (q.pixelX() - p.pixelX()) * (r.pixelY() - p.pixelY())
             - (q.pixelY() - p.pixelY()) * (r.pixelX() - p.pixelX());
    }
}
