package dc82.model;

import java.util.*;

public class MapState {

    public Map<HexCoord, Milestone> milestones;
    public List<Path> allPaths;
    public HexCoord current;

    public MapState() {
        milestones = new HashMap<>();
        allPaths = new ArrayList<>();
    }

    public Milestone currentMilestone() {
        return milestones.get(current);
    }

    public void setCurrent(HexCoord hex) {
        Milestone prev = currentMilestone();
        if (prev != null) {
            prev.isCurrent = false;
        }
        this.current = hex;
        Milestone next = milestones.get(hex);
        if (next != null) {
            next.isCurrent = true;
        }
    }

    public static MapState createStartingState() {
        MapState state = new MapState();
        HexCoord origin = HexCoord.ORIGIN;
        Milestone first = new Milestone(origin, "Starting Point");
        state.milestones.put(origin, first);
        state.setCurrent(origin);
        return state;
    }
}
