package edu.kit.scholarizer.security;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is used to validate an email address.
 * @author Pablo Schmeiser
 * @version 1.0
 */
public class EmailValidator implements ConstraintValidator<ValidEmail, String> {
    private static final String EMAIL_PATTERN = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*"
            + "@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    /**
     * This method is used to initialize the validator.
     * @param constraintAnnotation the annotation
     */
    @Override
    public void initialize(ValidEmail constraintAnnotation) {
        // intentionally left blank; this is the place to initialize the constraint annotation for any sensible
        // default values.
    }


    /**
     * This method is used to validate an email address.
     * @param email the email address
     * @param context the context
     * @return true if the email address is valid, false otherwise
     */
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return (this.validateEmail(email));
    }

    private boolean validateEmail(String email) {
        Matcher matcher;
        if (email == null) return false;
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
