package dc82.util;

import java.util.Random;

public class RandomUtil {

    private static final Random RAND = new Random();

    private static final String[] C = {
        "b","c","d","f","g","h","j","k","l","m","n","p","r","s","t","v","w","z",
        "br","cr","dr","fr","gr","kr","pr","str","tr"
    };

    private static final String[] V = {
        "a","e","i","o","u","ae","ea","io","ou"
    };

    public static Random get() {
        return RAND;
    }

    public static String randomName(int minSyllables, int maxSyllables) {
        int len = minSyllables + RAND.nextInt(maxSyllables - minSyllables + 1);
        var sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(C[RAND.nextInt(C.length)]);
            sb.append(V[RAND.nextInt(V.length)]);
        }
        String name = sb.toString();
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }
}
