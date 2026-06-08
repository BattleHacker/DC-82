package dc82;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void testMainRuns() {
        assertDoesNotThrow(() -> Main.main(new String[]{}));
    }
}
