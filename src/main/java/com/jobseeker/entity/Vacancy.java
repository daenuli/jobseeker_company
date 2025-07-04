package com.jobseeker.entity;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document(collection = "vacancies")
public class Vacancy {
    
    @Id
    private String id;
    
    @NotBlank(message = "Vacancy name is required")
    @Size(min = 2, max = 200, message = "Vacancy name must be between 2 and 200 characters")
    @Indexed(unique = true)
    private String name;
    
    @NotEmpty(message = "At least one criterion is required")
    @Valid
    private List<Criteria> criteria = new ArrayList<>();
    
    // Default constructor
    public Vacancy() {}
    
    // Constructor with name and criteria
    public Vacancy(String name, List<Criteria> criteria) {
        this.name = name;
        this.criteria = criteria != null ? criteria : new ArrayList<>();
    }
    
    // Getters and Setters
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
    
    public List<Criteria> getCriteria() {
        return criteria;
    }
    
    public void setCriteria(List<Criteria> criteria) {
        this.criteria = criteria != null ? criteria : new ArrayList<>();
    }
    
    // Helper methods
    public void addCriteria(Criteria criterion) {
        if (this.criteria == null) {
            this.criteria = new ArrayList<>();
        }
        this.criteria.add(criterion);
    }
    
    public void removeCriteria(Criteria criterion) {
        if (this.criteria != null) {
            this.criteria.remove(criterion);
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vacancy vacancy = (Vacancy) o;
        return Objects.equals(id, vacancy.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Vacancy{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", criteria=" + criteria +
                '}';
    }
}