package org.licesys.license.validator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;

public class RequestValidator {

    private final static Validator validator =
            Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * Evaluates all Bean Validation annotations on the subject.
     */
    public static <T> void validate(T t) {
        Set<ConstraintViolation<T>> violations = validator.validate(t);

        if (!violations.isEmpty()) {
            String message = violations.stream().map(ConstraintViolation::getMessage).findFirst().get();
            throw new ConstraintViolationException(message, violations);
        }

    }
}
