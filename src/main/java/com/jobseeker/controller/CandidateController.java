package com.jobseeker.controller;

import com.jobseeker.dto.CandidateResponseDTO;
import com.jobseeker.dto.CandidatePageResponseDTO;
import com.jobseeker.dto.MessageResponseDTO;
import com.jobseeker.entity.Candidate;
import com.jobseeker.service.CandidateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidates")
@CrossOrigin(origins = "*")
public class CandidateController {
    
    private final CandidateService candidateService;
    
    @Autowired
    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }
    
    /**
     * Create a new candidate
     * POST /api/candidates
     */
    @PostMapping
    public ResponseEntity<Candidate> createCandidate(@Valid @RequestBody Candidate candidate) {
        Candidate createdCandidate = candidateService.createCandidate(candidate);
        return new ResponseEntity<>(createdCandidate, HttpStatus.CREATED);
    }
    
    /**
     * Get all candidates with pagination and optional name search
     * GET /api/candidates?page=0&size=10&sortBy=name&sortDir=asc&name=keyword
     */
    @GetMapping
    public ResponseEntity<CandidatePageResponseDTO> getAllCandidates(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String name) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<Candidate> candidatesPage;
        if (name != null && !name.trim().isEmpty()) {
            candidatesPage = candidateService.findByNameContaining(name.trim(), pageable);
        } else {
            candidatesPage = candidateService.getAllCandidates(pageable);
        }
        
        List<CandidateResponseDTO> responseDTOs = candidatesPage
                .map(CandidateResponseDTO::new)
                .getContent();
        
        CandidatePageResponseDTO response = new CandidatePageResponseDTO(
                responseDTOs,
                candidatesPage.getTotalElements(),
                candidatesPage.getTotalPages()
        );
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    /**
     * Get candidate by ID
     * GET /api/candidates/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<CandidateResponseDTO> getCandidateById(@PathVariable String id) {
        Candidate candidate = candidateService.getCandidateById(id);
        CandidateResponseDTO candidateResponseDTO = new CandidateResponseDTO(candidate);
        return new ResponseEntity<>(candidateResponseDTO, HttpStatus.OK);
    }
    
    /**
     * Update candidate
     * PUT /api/candidates/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Candidate> updateCandidate(
            @PathVariable String id,
            @Valid @RequestBody Candidate candidateDetails) {
        Candidate updatedCandidate = candidateService.updateCandidate(id, candidateDetails);
        return new ResponseEntity<>(updatedCandidate, HttpStatus.OK);
    }
    
    /**
     * Delete candidate
     * DELETE /api/candidates/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> deleteCandidate(@PathVariable String id) {
        candidateService.deleteCandidate(id);
        MessageResponseDTO response = new MessageResponseDTO("Candidate deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
}