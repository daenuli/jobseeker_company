package com.jobseeker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * DTO for vacancy response with formatted criteria
 */
public class VacancyResponseDTO {
    
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("criteria")
    private List<CriteriaResponseDTO> criteria;
    
    public VacancyResponseDTO() {}
    
    public VacancyResponseDTO(String id, String name, List<CriteriaResponseDTO> criteria) {
        this.id = id;
        this.name = name;
        this.criteria = criteria;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public List<CriteriaResponseDTO> getCriteria() {
        return criteria;
    }
    
    public void setCriteria(List<CriteriaResponseDTO> criteria) {
        this.criteria = criteria;
    }
}