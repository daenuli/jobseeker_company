package com.jobseeker.validation;

import com.jobseeker.entity.*;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validator for Criteria objects
 * Ensures that required fields are present based on criteria type
 */
public class CriteriaValidator implements ConstraintValidator<ValidCriteria, Criteria> {

    @Override
    public void initialize(ValidCriteria constraintAnnotation) {
        // No initialization needed
    }

    @Override
    public boolean isValid(Criteria criteria, ConstraintValidatorContext context) {
        if (criteria == null) {
            return true; // Let @NotNull handle null validation
        }

        boolean isValid = true;
        context.disableDefaultConstraintViolation();

        if (criteria instanceof AgeCriteria) {
            AgeCriteria ageCriteria = (AgeCriteria) criteria;
            if (ageCriteria.getMinimumAge() == null) {
                context.buildConstraintViolationWithTemplate("Minimum age is required for AGE criteria")
                        .addPropertyNode("minimumAge")
                        .addConstraintViolation();
                isValid = false;
            }
            if (ageCriteria.getMaximumAge() == null) {
                context.buildConstraintViolationWithTemplate("Maximum age is required for AGE criteria")
                        .addPropertyNode("maximumAge")
                        .addConstraintViolation();
                isValid = false;
            }
        } else if (criteria instanceof SalaryRangeCriteria) {
            SalaryRangeCriteria salaryCriteria = (SalaryRangeCriteria) criteria;
            if (salaryCriteria.getMinimumSalary() == null) {
                context.buildConstraintViolationWithTemplate("Minimum salary is required for SALARY_RANGE criteria")
                        .addPropertyNode("minimumSalary")
                        .addConstraintViolation();
                isValid = false;
            }
            if (salaryCriteria.getMaximumSalary() == null) {
                context.buildConstraintViolationWithTemplate("Maximum salary is required for SALARY_RANGE criteria")
                        .addPropertyNode("maximumSalary")
                        .addConstraintViolation();
                isValid = false;
            }
        } else if (criteria instanceof GenderCriteria) {
            GenderCriteria genderCriteria = (GenderCriteria) criteria;
            if (genderCriteria.getGender() == null || genderCriteria.getGender().trim().isEmpty()) {
                context.buildConstraintViolationWithTemplate("Gender is required for GENDER criteria")
                        .addPropertyNode("gender")
                        .addConstraintViolation();
                isValid = false;
            } else {
                String gender = genderCriteria.getGender().toLowerCase();
                if (!gender.equals("male") && !gender.equals("female") && !gender.equals("any")) {
                    context.buildConstraintViolationWithTemplate("Gender must be 'male', 'female', or 'any'")
                            .addPropertyNode("gender")
                            .addConstraintViolation();
                    isValid = false;
                }
            }
        }

        return isValid;
    }
}