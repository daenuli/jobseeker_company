package com.jobseeker.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.jobseeker.validation.ValidCriteria;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = AgeCriteria.class, name = "AGE"),
    @JsonSubTypes.Type(value = GenderCriteria.class, name = "GENDER"),
    @JsonSubTypes.Type(value = SalaryRangeCriteria.class, name = "SALARY_RANGE")
})
@ValidCriteria
public abstract class Criteria {
    
    @NotNull(message = "Criteria type is required")
    private CriteriaType type;
    
    @Min(value = 1, message = "Weight must be at least 1")
    private Integer weight = 1;
    
    public Criteria() {}
    
    public Criteria(CriteriaType type, Integer weight) {
        this.type = type;
        this.weight = weight != null ? weight : 1;
    }
    
    public CriteriaType getType() {
        return type;
    }
    
    public void setType(CriteriaType type) {
        this.type = type;
    }
    
    public Integer getWeight() {
        return weight;
    }
    
    public void setWeight(Integer weight) {
        this.weight = weight != null ? weight : 1;
    }
}