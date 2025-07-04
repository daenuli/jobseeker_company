package com.jobseeker.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jobseeker.entity.Candidate;

import java.time.LocalDate;
import java.time.Period;

public class CandidateResponseDTO {
    
    private String id;
    private String name;
    private String email;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    
    private Integer age;
    private String gender;
    private Integer currentSalary;
    
    // Default constructor
    public CandidateResponseDTO() {}
    
    // Constructor from Candidate entity
    public CandidateResponseDTO(Candidate candidate) {
        this.id = candidate.getId();
        this.name = candidate.getName();
        this.email = candidate.getEmail();
        this.birthDate = candidate.getBirthDate();
        this.age = calculateAge(candidate.getBirthDate());
        this.gender = candidate.getGender();
        this.currentSalary = candidate.getCurrentSalary();
    }
    
    // Calculate age from birthDate
    private Integer calculateAge(LocalDate birthDate) {
        if (birthDate == null) {
            return null;
        }
        return Period.between(birthDate, LocalDate.now()).getYears();
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
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public LocalDate getBirthDate() {
        return birthDate;
    }
    
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        this.age = calculateAge(birthDate);
    }
    
    public Integer getAge() {
        return age;
    }
    
    public void setAge(Integer age) {
        this.age = age;
    }
    
    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public Integer getCurrentSalary() {
        return currentSalary;
    }
    
    public void setCurrentSalary(Integer currentSalary) {
        this.currentSalary = currentSalary;
    }
}