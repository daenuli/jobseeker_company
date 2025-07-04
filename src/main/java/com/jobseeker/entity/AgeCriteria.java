package com.jobseeker.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

public class AgeCriteria extends Criteria {
    
    @NotNull(message = "Minimum age is required")
    @Min(value = 18, message = "Minimum age must be at least 18")
    @Max(value = 100, message = "Minimum age cannot exceed 100")
    private Integer minimumAge;
    
    @NotNull(message = "Maximum age is required")
    @Min(value = 18, message = "Maximum age must be at least 18")
    @Max(value = 100, message = "Maximum age cannot exceed 100")
    private Integer maximumAge;
    
    public AgeCriteria() {
        super(CriteriaType.AGE, 1);
    }
    
    public AgeCriteria(Integer minimumAge, Integer maximumAge, Integer weight) {
        super(CriteriaType.AGE, weight);
        this.minimumAge = minimumAge;
        this.maximumAge = maximumAge;
    }
    
    public Integer getMinimumAge() {
        return minimumAge;
    }
    
    public void setMinimumAge(Integer minimumAge) {
        this.minimumAge = minimumAge;
    }
    
    public Integer getMaximumAge() {
        return maximumAge;
    }
    
    public void setMaximumAge(Integer maximumAge) {
        this.maximumAge = maximumAge;
    }
    
    @Override
    public String toString() {
        return "AgeCriteria{" +
                "minimumAge=" + minimumAge +
                ", maximumAge=" + maximumAge +
                ", weight=" + getWeight() +
                '}';
    }
}