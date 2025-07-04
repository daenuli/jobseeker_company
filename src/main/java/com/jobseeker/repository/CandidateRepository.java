package com.jobseeker.repository;

import com.jobseeker.entity.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateRepository extends MongoRepository<Candidate, String> {
    
    /**
     * Find candidate by email address
     * @param email the email to search for
     * @return Optional containing the candidate if found
     */
    Optional<Candidate> findByEmail(String email);
    
    /**
     * Check if a candidate exists with the given email
     * @param email the email to check
     * @return true if candidate exists, false otherwise
     */
    boolean existsByEmail(String email);
    
    /**
     * Find candidate by email excluding a specific id (useful for updates)
     * @param email the email to search for
     * @param id the id to exclude from search
     * @return Optional containing the candidate if found
     */
    Optional<Candidate> findByEmailAndIdNot(String email, String id);
    
    /**
     * Find candidates by name (case-insensitive)
     * @param name the name to search for
     * @return List of candidates matching the name
     */
    List<Candidate> findByNameContainingIgnoreCase(String name);
    
    /**
     * Find candidates by name (case-insensitive) with pagination
     * @param name the name to search for
     * @param pageable pagination information
     * @return Page of candidates matching the name
     */
    Page<Candidate> findByNameContainingIgnoreCase(String name, Pageable pageable);
}