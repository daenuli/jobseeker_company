package com.jobseeker.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class SalaryRangeCriteria extends Criteria {
    
    @NotNull(message = "Minimum salary is required")
    @Min(value = 0, message = "Minimum salary must be greater than 0")
    private Integer minimumSalary;
    
    @NotNull(message = "Maximum salary is required")
    @Min(value = 0, message = "Maximum salary must be greater than 0")
    private Integer maximumSalary;
    
    public SalaryRangeCriteria() {
        super(CriteriaType.SALARY_RANGE, 1);
    }
    
    public SalaryRangeCriteria(Integer minimumSalary, Integer maximumSalary, Integer weight) {
        super(CriteriaType.SALARY_RANGE, weight);
        this.minimumSalary = minimumSalary;
        this.maximumSalary = maximumSalary;
    }
    
    public Integer getMinimumSalary() {
        return minimumSalary;
    }
    
    public void setMinimumSalary(Integer minimumSalary) {
        this.minimumSalary = minimumSalary;
    }
    
    public Integer getMaximumSalary() {
        return maximumSalary;
    }
    
    public void setMaximumSalary(Integer maximumSalary) {
        this.maximumSalary = maximumSalary;
    }
    
    @Override
    public String toString() {
        return "SalaryRangeCriteria{" +
                "minimumSalary=" + minimumSalary +
                ", maximumSalary=" + maximumSalary +
                ", weight=" + getWeight() +
                '}';
    }
}