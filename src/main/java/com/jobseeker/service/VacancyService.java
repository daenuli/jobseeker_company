package com.jobseeker.service;

import com.jobseeker.entity.Vacancy;
import com.jobseeker.exception.DuplicateResourceException;
import com.jobseeker.exception.ResourceNotFoundException;
import com.jobseeker.repository.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VacancyService {
    
    private final VacancyRepository vacancyRepository;
    
    @Autowired
    public VacancyService(VacancyRepository vacancyRepository) {
        this.vacancyRepository = vacancyRepository;
    }
    
    /**
     * Create a new vacancy
     * @param vacancy the vacancy to create
     * @return the created vacancy
     * @throws DuplicateResourceException if vacancy name already exists
     */
    @CacheEvict(value = {"vacancies", "vacancy-rankings"}, allEntries = true)
    public Vacancy createVacancy(Vacancy vacancy) {
        // Check if vacancy name already exists
        if (vacancyRepository.existsByName(vacancy.getName())) {
            throw new DuplicateResourceException("Vacancy with name '" + vacancy.getName() + "' already exists");
        }
        
        // Ensure default weights are set for criteria that don't have them
        if (vacancy.getCriteria() != null) {
            vacancy.getCriteria().forEach(criteria -> {
                if (criteria.getWeight() == null) {
                    criteria.setWeight(1);
                }
            });
        }
        
        return vacancyRepository.save(vacancy);
    }
    
    /**
     * Get all vacancies
     * @return list of all vacancies
     */
    @Cacheable(value = "vacancies")
    public List<Vacancy> getAllVacancies() {
        return vacancyRepository.findAll();
    }
    
    /**
     * Get all vacancies with pagination
     * @param pageable pagination information
     * @return page of vacancies
     */
    public Page<Vacancy> getAllVacancies(Pageable pageable) {
        return vacancyRepository.findAll(pageable);
    }
    
    /**
     * Get vacancy by ID
     * @param id the vacancy ID
     * @return the vacancy
     * @throws ResourceNotFoundException if vacancy not found
     */
    public Vacancy getVacancyById(String id) {
        return vacancyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vacancy not found with id: " + id));
    }
    
    /**
     * Update an existing vacancy
     * @param id the vacancy ID
     * @param vacancyDetails the updated vacancy details
     * @return the updated vacancy
     * @throws ResourceNotFoundException if vacancy not found
     * @throws DuplicateResourceException if vacancy name already exists for another vacancy
     */
    @CacheEvict(value = {"vacancies", "vacancy-rankings"}, allEntries = true)
    public Vacancy updateVacancy(String id, Vacancy vacancyDetails) {
        Vacancy vacancy = vacancyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vacancy not found with id: " + id));
        
        // Check if the new name already exists for a different vacancy
        if (!vacancy.getName().equals(vacancyDetails.getName()) && 
            vacancyRepository.existsByName(vacancyDetails.getName())) {
            throw new DuplicateResourceException("Vacancy with name '" + vacancyDetails.getName() + "' already exists");
        }
        
        // Update vacancy fields
        vacancy.setName(vacancyDetails.getName());
        
        // Update criteria with default weights if not provided
        if (vacancyDetails.getCriteria() != null) {
            vacancyDetails.getCriteria().forEach(criteria -> {
                if (criteria.getWeight() == null) {
                    criteria.setWeight(1);
                }
            });
            vacancy.setCriteria(vacancyDetails.getCriteria());
        }
        
        return vacancyRepository.save(vacancy);
    }
    
    /**
     * Delete a vacancy
     * @param id the vacancy ID
     * @throws ResourceNotFoundException if vacancy not found
     */
    @CacheEvict(value = {"vacancies", "vacancy-rankings"}, allEntries = true)
    public void deleteVacancy(String id) {
        Vacancy vacancy = vacancyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vacancy not found with id: " + id));
        
        vacancyRepository.delete(vacancy);
    }
    
    /**
     * Find vacancies by name (case-insensitive search)
     * @param name the name to search for
     * @return list of matching vacancies
     */
    public List<Vacancy> findByNameContaining(String name) {
        return vacancyRepository.findByNameContainingIgnoreCase(name);
    }
    
    /**
     * Find vacancies by name with pagination (case-insensitive search)
     * @param name the name to search for
     * @param pageable pagination information
     * @return page of matching vacancies
     */
    public Page<Vacancy> findByNameContaining(String name, Pageable pageable) {
        return vacancyRepository.findByNameContainingIgnoreCase(name, pageable);
    }
}