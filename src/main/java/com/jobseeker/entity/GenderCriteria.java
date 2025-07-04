package com.jobseeker.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class GenderCriteria extends Criteria {
    
    @NotBlank(message = "Gender is required")
    @Pattern(regexp = "^(male|female|any)$", message = "Gender must be 'male', 'female', or 'any'")
    private String gender;
    
    public GenderCriteria() {
        super(CriteriaType.GENDER, 1);
    }
    
    public GenderCriteria(String gender, Integer weight) {
        super(CriteriaType.GENDER, weight);
        this.gender = gender;
    }
    
    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    @Override
    public String toString() {
        return "GenderCriteria{" +
                "gender='" + gender + '\'' +
                ", weight=" + getWeight() +
                '}';
    }
}