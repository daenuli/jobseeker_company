package com.jobseeker.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Objects;

@Document(collection = "candidates")
@CompoundIndex(def = "{'birthDate': 1, 'gender': 1, 'currentSalary': 1}", name = "ranking_compound_idx")
@CompoundIndex(def = "{'currentSalary': 1, 'birthDate': 1}", name = "salary_age_idx")
@CompoundIndex(def = "{'gender': 1, 'currentSalary': 1}", name = "gender_salary_idx")
@CompoundIndex(def = "{'birthDate': 1, 'currentSalary': 1}", name = "age_salary_idx")
public class Candidate {

    @Id
    private String id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Indexed(unique = true)
    private String email;

    @NotNull(message = "Birth date is required")
    @Past(message = "Birth date must be in the past")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @NotBlank(message = "Gender is required")
    @Pattern(regexp = "^(male|female)$", message = "Gender must be either 'male' or 'female'")
    private String gender;

    @NotNull(message = "Current salary is required")
    @Min(value = 0, message = "Current salary must be greater than 0")
    private Integer currentSalary;

    // Default constructor
    public Candidate() {}

    // Constructor with all fields
    public Candidate(String name, String email, LocalDate birthDate, String gender, Integer currentSalary) {
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.gender = gender;
        this.currentSalary = currentSalary;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Candidate candidate = (Candidate) o;
        return Objects.equals(id, candidate.id) && Objects.equals(email, candidate.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

    @Override
    public String toString() {
        return "Candidate{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", birthDate=" + birthDate +
                ", gender='" + gender + '\'' +
                ", currentSalary=" + currentSalary +
                '}';
    }
}