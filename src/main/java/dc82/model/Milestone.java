package dc82.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Milestone {

    private static final String[] C = {"b","c","d","f","g","h","j","k","l","m","n","p","r","s","t","v","w","z","br","cr","dr","fr","gr","kr","pr","str","tr"};
    private static final String[] V = {"a","e","i","o","u","ae","ea","io","ou"};
    private static final Random RAND = new Random();

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
        int len = 5 + RAND.nextInt(8);
        var sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            if (i % 2 == 0) {
                sb.append(C[RAND.nextInt(C.length)]);
            } else {
                sb.append(V[RAND.nextInt(V.length)]);
            }
        }
        String name = sb.toString();
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }
}
