package dc82.model;

import java.util.ArrayList;
import java.util.List;

import dc82.util.RandomUtil;

public class Milestone {

    public HexCoord hex;
    public String displayName;
    public List<Path> paths;
    public boolean isCurrent;
    public boolean visited;

    public Milestone() {
        this.paths = new ArrayList<>();
    }

    public Milestone(HexCoord hex) {
        this(hex, randomName());
    }

    public Milestone(HexCoord hex, String displayName) {
        this.hex = hex;
        this.displayName = displayName;
        this.paths = new ArrayList<>();
    }

    public static String randomName() {
        return RandomUtil.randomName(5, 12);
    }
}
