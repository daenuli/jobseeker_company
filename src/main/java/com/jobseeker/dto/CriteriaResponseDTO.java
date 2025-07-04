package com.jobseeker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO for criteria response formatting
 */
public class CriteriaResponseDTO {
    
    @JsonProperty("criteriaType")
    private String criteriaType;
    
    @JsonProperty("details")
    private String details;
    
    @JsonProperty("weight")
    private Integer weight;
    
    public CriteriaResponseDTO() {}
    
    public CriteriaResponseDTO(String criteriaType, String details, Integer weight) {
        this.criteriaType = criteriaType;
        this.details = details;
        this.weight = weight;
    }
    
    public String getCriteriaType() {
        return criteriaType;
    }
    
    public void setCriteriaType(String criteriaType) {
        this.criteriaType = criteriaType;
    }
    
    public String getDetails() {
        return details;
    }
    
    public void setDetails(String details) {
        this.details = details;
    }
    
    public Integer getWeight() {
        return weight;
    }
    
    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}