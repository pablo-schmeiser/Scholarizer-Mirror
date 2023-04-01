package edu.kit.scholarizer.security;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import javax.validation.ConstraintValidator;

import java.lang.annotation.Annotation;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the email validator.
 * @author Pablo Schmeiser
 * @version 1.0
 */
class EmailValidatorTest {
    private static final String TEST_MAIL = "test@test.com";

    private ConstraintValidator validator = null;

    @BeforeEach
    void setUp() {
        validator = new EmailValidator();
    }

    @AfterEach
    void tearDown() {
        validator = null;
    }

    @Test
    void isConstraintValidator() {
        assertTrue(this.validator instanceof ConstraintValidator);
    }

    @Test
    void initialize() {
        assertDoesNotThrow(() -> this.validator.initialize(null));
    }

    @Test
    void isEmailValid() {
        assertTrue(this.validator.isValid(TEST_MAIL, null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"test", "test@test", "test@test.", "@test.com", "test@.com", "test.com", "test@", "test@.",
        "@test", "@test.", ".com", "@test.com", "@test@.com", "test@test..com", "test@test@.com", "test@@test.com"})
    @EmptySource
    @NullSource
    void isNonEmailInvalid(String email) {
        assertFalse(this.validator.isValid(email, null));
    }

}
