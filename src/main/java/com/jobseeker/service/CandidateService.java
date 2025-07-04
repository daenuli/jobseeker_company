package com.jobseeker.service;

import com.jobseeker.entity.Candidate;
import com.jobseeker.exception.EmailAlreadyExistsException;
import com.jobseeker.exception.ResourceNotFoundException;
import com.jobseeker.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CandidateService {
    
    private final CandidateRepository candidateRepository;
    
    @Autowired
    public CandidateService(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }
    
    /**
     * Create a new candidate
     * @param candidate the candidate to create
     * @return the created candidate
     * @throws EmailAlreadyExistsException if email already exists
     */
    @CacheEvict(value = {"candidates", "vacancy-rankings"}, allEntries = true)
    public Candidate createCandidate(Candidate candidate) {
        // Check if email already exists
        if (candidateRepository.existsByEmail(candidate.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists: " + candidate.getEmail());
        }
        
        return candidateRepository.save(candidate);
    }
    
    /**
     * Get all candidates
     * @return list of all candidates
     */
    @Cacheable(value = "candidates")
    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }
    
    /**
     * Get all candidates with pagination
     * @param pageable pagination information
     * @return page of candidates
     */
    public Page<Candidate> getAllCandidates(Pageable pageable) {
        return candidateRepository.findAll(pageable);
    }
    
    /**
     * Get candidate by ID
     * @param id the candidate ID
     * @return the candidate
     * @throws ResourceNotFoundException if candidate not found
     */
    public Candidate getCandidateById(String id) {
        return candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found with id: " + id));
    }
    
    /**
     * Update an existing candidate
     * @param id the candidate ID
     * @param candidateDetails the updated candidate details
     * @return the updated candidate
     * @throws ResourceNotFoundException if candidate not found
     * @throws EmailAlreadyExistsException if email already exists for another candidate
     */
    @CacheEvict(value = {"candidates", "vacancy-rankings"}, allEntries = true)
    public Candidate updateCandidate(String id, Candidate candidateDetails) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found with id: " + id));
        
        // Check if email is being changed and if it already exists for another candidate
        if (!candidate.getEmail().equals(candidateDetails.getEmail())) {
            Optional<Candidate> existingCandidate = candidateRepository.findByEmailAndIdNot(candidateDetails.getEmail(), id);
            if (existingCandidate.isPresent()) {
                throw new EmailAlreadyExistsException("Email already exists: " + candidateDetails.getEmail());
            }
        }
        
        // Update candidate fields
        candidate.setName(candidateDetails.getName());
        candidate.setEmail(candidateDetails.getEmail());
        candidate.setBirthDate(candidateDetails.getBirthDate());
        candidate.setGender(candidateDetails.getGender());
        candidate.setCurrentSalary(candidateDetails.getCurrentSalary());
        
        return candidateRepository.save(candidate);
    }
    
    /**
     * Delete a candidate
     * @param id the candidate ID
     * @throws ResourceNotFoundException if candidate not found
     */
    @CacheEvict(value = {"candidates", "vacancy-rankings"}, allEntries = true)
    public void deleteCandidate(String id) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found with id: " + id));
        
        candidateRepository.delete(candidate);
    }
    
    /**
     * Find candidate by email
     * @param email the email to search for
     * @return Optional containing the candidate if found
     */
    public Optional<Candidate> findByEmail(String email) {
        return candidateRepository.findByEmail(email);
    }
    
    /**
     * Find candidates by name containing the given string (case-insensitive)
     * @param name the name to search for
     * @return list of candidates matching the name
     */
    public List<Candidate> findByNameContaining(String name) {
        return candidateRepository.findByNameContainingIgnoreCase(name);
    }
    
    /**
     * Find candidates by name containing the given string (case-insensitive) with pagination
     * @param name the name to search for
     * @param pageable pagination information
     * @return page of candidates matching the name
     */
    public Page<Candidate> findByNameContaining(String name, Pageable pageable) {
        return candidateRepository.findByNameContainingIgnoreCase(name, pageable);
    }
}