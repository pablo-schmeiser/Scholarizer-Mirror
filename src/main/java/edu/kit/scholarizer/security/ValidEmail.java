package edu.kit.scholarizer.security;


import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * This interface is used to validate an email address.
 *
 * @see EmailValidator
 * @author Pablo Schmeiser
 * @version 1.0
 */
@Target({TYPE, FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Documented
@NotEmpty
@NotNull
@NotBlank
public @interface ValidEmail {
    /**
     * Constant for the default error message if an email address is not valid.
     */
    /* package-private */ String INVALID_EMAIL_ADDRESS_MESSAGE = "Invalid email address";

    /**
     * The default error message.
     * @return the default error message
     */
    String message() default INVALID_EMAIL_ADDRESS_MESSAGE;

    /**
     * The default groups.
     * @return the default groups
     */
    Class<?>[] groups() default {};

    /**
     * The default payload.
     * @return the default payload
     */
    Class<? extends Payload>[] payload() default {};
}
