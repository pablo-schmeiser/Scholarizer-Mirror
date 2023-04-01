package edu.kit.scholarizer.security;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

/**
 * This class generates random strings.
 * @author Tim Wolk
 * @version 1.0
 */
public class RandomString {
    private static final int DEFAULT_LENGTH = 21;

    /**
     * All uppercase letters.
     */
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * All lowercase letters.
     */
    private static final String LOWER = UPPER.toLowerCase(Locale.ROOT);

    /**
     * All digits.
     */
    private static final String DIGITS = "0123456789";

    /**
     * All letters and digits.
     */
    private static final String ALPHANUM = UPPER + LOWER + DIGITS;

    private final Random random;

    private final char[] symbols;

    private final char[] buf;

    /**
     * Creates a new random string generator.
     * @param length the length of the string to generate
     * @param random the random number generator to use
     * @param symbols the symbols to use
     */
    private RandomString(int length, Random random, String symbols) {
        if (length < 1) throw new IllegalArgumentException();
        if (symbols.length() < 2) throw new IllegalArgumentException();
        this.random = Objects.requireNonNull(random);
        this.symbols = symbols.toCharArray();
        this.buf = new char[length];
    }

    /**
     * Create an alphanumeric string generator.
     * @param length The Length of the String
     * @param random The Random Number Generator
     */
    RandomString(int length, Random random) {
        this(length, random, ALPHANUM);
    }

    /**
     * Create an alphanumeric strings from a secure generator.
     * @param length The Length of the String
     */
    public RandomString(int length) {
        this(length, new SecureRandom());
    }

    /**
     * Create session identifiers.
     */
    public RandomString() {
        this(DEFAULT_LENGTH);
    }

    /**
     * Generate a random string.
     * @return The generated String
     */
    public String nextString() {
        for (int idx = 0; idx < this.buf.length; ++idx) {
            this.buf[idx] = this.symbols[this.random.nextInt(this.symbols.length)];
        }
        return new String(this.buf);
    }

}
