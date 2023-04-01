package edu.kit.scholarizer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScholarizerApplicationTest {
    @Test
    void constructorTest() {
        assertDoesNotThrow(ScholarizerApplication::new);
        ScholarizerApplication app = new ScholarizerApplication();
        assertNotNull(app);
        assertInstanceOf(ScholarizerApplication.class, app);
    }

    @Test
    void mainTest() {
        assertDoesNotThrow(() -> ScholarizerApplication.main(new String[]{}));
    }
}
