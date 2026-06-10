package dc82.controller;

import dc82.model.HexCoord;
import dc82.model.MapState;
import dc82.model.Milestone;
import dc82.model.Path;
import dc82.util.MapGenerator;

import java.util.Collection;
import java.util.List;

public class MapController {

    private MapState mapState;

    public void startNewMap() {
        mapState = MapState.createStartingState();
        generateForCurrent();
    }

    public void travelTo(HexCoord target) {
        if (mapState == null) return;
        mapState.setCurrent(target);
        if (!mapState.currentMilestone().visited) {
            generateForCurrent();
        }
    }

    public boolean hasMapState() {
        return mapState != null;
    }

    public Milestone getCurrentMilestone() {
        return mapState != null ? mapState.currentMilestone() : null;
    }

    public Collection<Milestone> getAllMilestones() {
        return mapState != null ? mapState.milestones.values() : List.of();
    }

    public List<Path> getAllPaths() {
        return mapState != null ? mapState.allPaths : List.of();
    }

    public HexCoord getCurrentHex() {
        return mapState != null ? mapState.current : null;
    }

    public Milestone getMilestoneAt(HexCoord hex) {
        return mapState != null ? mapState.milestones.get(hex) : null;
    }

    private void generateForCurrent() {
        MapGenerator.generateNeighbors(mapState);
        Milestone current = mapState.currentMilestone();
        if (current != null) {
            current.visited = true;
        }
    }
}
