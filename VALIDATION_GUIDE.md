# Vacancy Criteria Validation Guide

This document describes the validation rules implemented for vacancy criteria creation.

## Overview

When creating a vacancy, each criterion must include specific required fields based on its type. The validation ensures data integrity and prevents incomplete criteria from being saved.

## Validation Rules

### AGE Criteria
When `type` equals `"AGE"`, the following fields are **required**:
- `minimumAge`: Integer (18-100)
- `maximumAge`: Integer (18-100)
- `weight`: Integer (minimum 1)

**Example:**
```json
{
  "type": "AGE",
  "minimumAge": 25,
  "maximumAge": 45,
  "weight": 5
}
```

### SALARY_RANGE Criteria
When `type` equals `"SALARY_RANGE"`, the following fields are **required**:
- `minimumSalary`: Integer (greater than 0)
- `maximumSalary`: Integer (greater than 0)
- `weight`: Integer (minimum 1)

**Example:**
```json
{
  "type": "SALARY_RANGE",
  "minimumSalary": 5000000,
  "maximumSalary": 10000000,
  "weight": 3
}
```

### GENDER Criteria
When `type` equals `"GENDER"`, the following fields are **required**:
- `gender`: String (must be "male", "female", or "any")
- `weight`: Integer (minimum 1)

**Example:**
```json
{
  "type": "GENDER",
  "gender": "female",
  "weight": 2
}
```

## Complete Vacancy Creation Example

```json
{
  "name": "Software Engineer Position",
  "criteria": [
    {
      "type": "AGE",
      "minimumAge": 25,
      "maximumAge": 40,
      "weight": 3
    },
    {
      "type": "SALARY_RANGE",
      "minimumSalary": 8000000,
      "maximumSalary": 15000000,
      "weight": 5
    },
    {
      "type": "GENDER",
      "gender": "any",
      "weight": 1
    }
  ]
}
```

## Validation Error Messages

If validation fails, you will receive specific error messages:

### AGE Criteria Errors
- `"Minimum age is required for AGE criteria"`
- `"Maximum age is required for AGE criteria"`
- `"Minimum age must be at least 18"`
- `"Maximum age cannot exceed 100"`

### SALARY_RANGE Criteria Errors
- `"Minimum salary is required for SALARY_RANGE criteria"`
- `"Maximum salary is required for SALARY_RANGE criteria"`
- `"Minimum salary must be greater than 0"`
- `"Maximum salary must be greater than 0"`

### GENDER Criteria Errors
- `"Gender is required for GENDER criteria"`
- `"Gender must be 'male', 'female', or 'any'"`

### General Errors
- `"Vacancy name is required"`
- `"At least one criterion is required"`
- `"Weight must be at least 1"`

## Implementation Details

The validation is implemented using:
1. **Custom Validation Annotation**: `@ValidCriteria` applied to the `Criteria` base class
2. **Custom Validator**: `CriteriaValidator` that checks type-specific requirements
3. **Standard Bean Validation**: `@NotNull`, `@NotBlank`, `@Min`, `@Max`, `@Pattern` annotations
4. **Cascade Validation**: `@Valid` annotation on the criteria list in `Vacancy` entity

The validation is automatically triggered when:
- Creating a new vacancy via `POST /api/vacancies`
- Updating an existing vacancy via `PUT /api/vacancies/{id}`
- Any operation that validates the `Vacancy` entity

## Testing

Comprehensive unit tests are available in `CriteriaValidationTest.java` to verify all validation scenarios.

To run the validation tests:
```bash
mvn test -Dtest=CriteriaValidationTest
```