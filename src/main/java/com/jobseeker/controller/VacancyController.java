package com.jobseeker.controller;

import com.jobseeker.dto.MessageResponseDTO;
import com.jobseeker.dto.VacancyResponseDTO;
import com.jobseeker.entity.Vacancy;
import com.jobseeker.dto.VacancyMapper;
import com.jobseeker.dto.VacancyPageResponseDTO;
import com.jobseeker.service.VacancyService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vacancies")
@CrossOrigin(origins = "*")
public class VacancyController {
    
    private final VacancyService vacancyService;
    
    @Autowired
    public VacancyController(VacancyService vacancyService) {
        this.vacancyService = vacancyService;
    }
    
    /**
     * Create a new vacancy
     * POST /api/vacancies
     */
    @PostMapping
    public ResponseEntity<VacancyResponseDTO> createVacancy(@Valid @RequestBody Vacancy vacancy) {
        Vacancy createdVacancy = vacancyService.createVacancy(vacancy);
        VacancyResponseDTO responseDTO = VacancyMapper.toResponseDTO(createdVacancy);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
    
    /**
     * Get all vacancies with pagination and optional name search
     * GET /api/vacancies?page=0&size=10&sortBy=name&sortDir=asc&name=keyword
     */
    @GetMapping
    public ResponseEntity<VacancyPageResponseDTO> getAllVacancies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String name) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<Vacancy> vacanciesPage;
        if (name != null && !name.trim().isEmpty()) {
            vacanciesPage = vacancyService.findByNameContaining(name.trim(), pageable);
        } else {
            vacanciesPage = vacancyService.getAllVacancies(pageable);
        }
        
        List<VacancyResponseDTO> responseDTOs = vacanciesPage
                .map(VacancyMapper::toResponseDTO)
                .getContent();
                
        VacancyPageResponseDTO response = new VacancyPageResponseDTO(
                responseDTOs,
                vacanciesPage.getTotalElements(),
                vacanciesPage.getTotalPages()
        );
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    /**
     * Get vacancy by ID
     * GET /api/vacancies/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<VacancyResponseDTO> getVacancyById(@PathVariable String id) {
        Vacancy vacancy = vacancyService.getVacancyById(id);
        VacancyResponseDTO responseDTO = VacancyMapper.toResponseDTO(vacancy);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    
    /**
     * Update vacancy
     * PUT /api/vacancies/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<VacancyResponseDTO> updateVacancy(
            @PathVariable String id,
            @Valid @RequestBody Vacancy vacancyDetails) {
        Vacancy updatedVacancy = vacancyService.updateVacancy(id, vacancyDetails);
        VacancyResponseDTO responseDTO = VacancyMapper.toResponseDTO(updatedVacancy);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    
    /**
     * Delete vacancy
     * DELETE /api/vacancies/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> deleteVacancy(@PathVariable String id) {
        vacancyService.deleteVacancy(id);
        MessageResponseDTO response = new MessageResponseDTO("Vacancy deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    /**
     * Search vacancies by name with pagination
     * GET /api/vacancies/search?name=keyword&page=0&size=10&sort=name,asc
     */
    @GetMapping("/search")
    public ResponseEntity<Page<VacancyResponseDTO>> searchVacanciesByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<Vacancy> vacancies = vacancyService.findByNameContaining(name, pageable);
        Page<VacancyResponseDTO> responseDTOs = vacancies
                .map(VacancyMapper::toResponseDTO);
        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }
    
}