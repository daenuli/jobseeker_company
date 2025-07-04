package com.jobseeker.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

/**
 * Custom validation annotation for Criteria objects
 * Validates that required fields are present based on criteria type
 */
@Documented
@Constraint(validatedBy = CriteriaValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCriteria {
    String message() default "Invalid criteria configuration";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}