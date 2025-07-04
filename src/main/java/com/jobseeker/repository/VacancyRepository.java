package com.jobseeker.repository;

import com.jobseeker.entity.Vacancy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacancyRepository extends MongoRepository<Vacancy, String> {
    
    /**
     * Find vacancies by name (case-insensitive)
     * @param name the name to search for
     * @return List of vacancies matching the name
     */
    List<Vacancy> findByNameContainingIgnoreCase(String name);
    
    /**
     * Find vacancies by name with pagination (case-insensitive)
     * @param name the name to search for
     * @param pageable pagination information
     * @return Page of vacancies matching the name
     */
    Page<Vacancy> findByNameContainingIgnoreCase(String name, Pageable pageable);
    
    /**
     * Check if a vacancy exists with the given name
     * @param name the name to check
     * @return true if vacancy exists, false otherwise
     */
    boolean existsByName(String name);
}