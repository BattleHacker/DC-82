package dc82;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void testClassLoads() {
        assertDoesNotThrow(() -> Class.forName("dc82.Main"));
    }
}
