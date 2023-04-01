package edu.kit.scholarizer.controllers.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the ErrorMessage class.
 *
 * @author Pablo Schmeiser
 * @version 1.0
 */
class ErrorMessageTest {
    private static final String TEST_STRING = "Test";
    private ErrorMessage errorMessage;

    @BeforeEach
    void setUp() {
        this.errorMessage = null;
    }

    @Test void standardConstructorTest () {
        this.errorMessage = new ErrorMessage();
        assertNotNull(this.errorMessage);
    }

    @Test void constructorTest () {
        this.errorMessage = new ErrorMessage(TEST_STRING);
        assertNotNull(this.errorMessage);
    }

    @Test
    void getErrorTest() {
        this.errorMessage = new ErrorMessage(TEST_STRING);
        assertEquals(TEST_STRING, this.errorMessage.getError());
    }

    @Test
    void setErrorTest() {
        this.errorMessage = new ErrorMessage();
        this.errorMessage.setError(TEST_STRING);
        assertEquals(TEST_STRING, this.errorMessage.getError());
    }
}
