package edu.kit.scholarizer.security;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the random string generator.
 * @author Pablo Schmeiser
 * @version 1.0
 */
class RandomStringTest {
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String ALPHANUM = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int DEFAULT_LENGTH = 21;

    @Test
    void defaultConstructorTest() {
        assertDoesNotThrow(() -> new RandomString());
        RandomString rs = new RandomString();
        RandomString rs2 = new RandomString(DEFAULT_LENGTH);
        RandomString rs3 = new RandomString(DEFAULT_LENGTH, new SecureRandom());
        assertNotNull(rs);
        assertDoesNotThrow(rs::nextString);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, DEFAULT_LENGTH, 100})
    void validLengthConstructorTest(int length) {
        assertDoesNotThrow(() -> new RandomString(length));
        RandomString rs = new RandomString(length);
        RandomString rs2 = new RandomString(length, new SecureRandom());
        assertNotNull(rs);
        assertDoesNotThrow(rs::nextString);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -1 * DEFAULT_LENGTH, -100})
    void invalidLengthConstructorTest(int length) {
        assertThrows(IllegalArgumentException.class, () -> new RandomString(length));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, DEFAULT_LENGTH, 100})
    void validLengthAndRandomConstructorTest(int length) {
        assertDoesNotThrow(() -> new RandomString(length, new SecureRandom()));
        RandomString rs = new RandomString(length, new SecureRandom());
        assertNotNull(rs);
        assertDoesNotThrow(rs::nextString);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -1 * DEFAULT_LENGTH, -100})
    void invalidLengthAndValidRandomConstructorTest(int length) {
        assertThrows(IllegalArgumentException.class, () -> new RandomString(length, new SecureRandom()));
    }

    @Test
    void validLengthAndNullRandomConstructorTest() {
        assertThrows(IllegalArgumentException.class, () -> new RandomString(0, null));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, DEFAULT_LENGTH, 100})
    void nextStringWithLength(int length) {
        RandomString rs = new RandomString(length);
        RandomString rs2 = new RandomString(length, new SecureRandom());
        assertDoesNotThrow(rs::nextString);
        String s = rs.nextString();
        assertDoesNotThrow(rs2::nextString);
        String s2 = rs2.nextString();
        assertNotNull(s);
        assertNotNull(s2);
        assertEquals(length, s.length());
        assertEquals(length, s2.length());
    }

    @Test
    void nextStringWithDefaultLength() {
        RandomString rs = new RandomString();
        assertDoesNotThrow(rs::nextString);
        String s = rs.nextString();
        assertNotNull(s);
        assertEquals(DEFAULT_LENGTH, s.length());
    }
}
