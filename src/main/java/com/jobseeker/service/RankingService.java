package com.jobseeker.service;

import com.jobseeker.dto.CandidateRankingDTO;
import com.jobseeker.entity.*;
import com.jobseeker.exception.ResourceNotFoundException;
import com.jobseeker.util.PerformanceMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class RankingService {
    
    private final CandidateService candidateService;
    private final VacancyService vacancyService;
    private final PerformanceMonitor performanceMonitor;
    
    @Autowired
    public RankingService(CandidateService candidateService, VacancyService vacancyService, PerformanceMonitor performanceMonitor) {
        this.candidateService = candidateService;
        this.vacancyService = vacancyService;
        this.performanceMonitor = performanceMonitor;
    }
    
    /**
     * Rank all candidates for a specific vacancy
     * @param vacancyId the vacancy ID to rank candidates for
     * @return list of ranked candidates sorted by score in descending order
     * @throws ResourceNotFoundException if vacancy not found
     */
    @Cacheable(value = "vacancy-rankings", key = "#vacancyId")
    public List<CandidateRankingDTO> rankCandidatesForVacancy(String vacancyId) {
        long startTime = performanceMonitor.startTimer();
        
        // Get the vacancy and its criteria
        Vacancy vacancy = vacancyService.getVacancyById(vacancyId);
        
        // Get all candidates
        List<Candidate> candidates = candidateService.getAllCandidates();
        
        // Calculate scores for each candidate
        List<CandidateRankingDTO> candidateScores = new ArrayList<>();
        
        for (Candidate candidate : candidates) {
            int totalScore = calculateCandidateScore(candidate, vacancy.getCriteria());
            candidateScores.add(new CandidateRankingDTO(
                0, // rank will be set later
                candidate.getId(),
                candidate.getName(),
                candidate.getEmail(),
                totalScore
            ));
        }
        
        // Sort by score in descending order, then by name for tie-breaking
        candidateScores.sort(Comparator
            .comparingInt(CandidateRankingDTO::getScore).reversed()
            .thenComparing(CandidateRankingDTO::getCandidateName));
        
        // Assign ranks
        IntStream.range(0, candidateScores.size())
            .forEach(i -> candidateScores.get(i).setRank(i + 1));
        
        // Log performance metrics
        long executionTime = performanceMonitor.logExecutionTime("rankCandidatesForVacancy", startTime);
        performanceMonitor.logDatabaseQuery("ranking", candidates.size(), executionTime);
        
        return candidateScores;
    }
    
    /**
     * Calculate the total score for a candidate based on vacancy criteria
     * @param candidate the candidate to score
     * @param criteria the list of criteria to match against
     * @return the total score
     */
    private int calculateCandidateScore(Candidate candidate, List<Criteria> criteria) {
        int totalScore = 0;
        
        for (Criteria criterion : criteria) {
            int criterionScore = 0;
            
            switch (criterion.getType()) {
                case AGE:
                    criterionScore = calculateAgeScore(candidate, (AgeCriteria) criterion);
                    break;
                case GENDER:
                    criterionScore = calculateGenderScore(candidate, (GenderCriteria) criterion);
                    break;
                case SALARY_RANGE:
                    criterionScore = calculateSalaryScore(candidate, (SalaryRangeCriteria) criterion);
                    break;
            }
            
            totalScore += criterionScore;
        }
        
        return totalScore;
    }
    
    /**
     * Calculate age score for a candidate
     * @param candidate the candidate
     * @param ageCriteria the age criteria
     * @return the score (weight if matches, 0 if not)
     */
    private int calculateAgeScore(Candidate candidate, AgeCriteria ageCriteria) {
        int candidateAge = Period.between(candidate.getBirthDate(), LocalDate.now()).getYears();
        
        if (candidateAge >= ageCriteria.getMinimumAge() && candidateAge <= ageCriteria.getMaximumAge()) {
            return ageCriteria.getWeight();
        }
        
        return 0;
    }
    
    /**
     * Calculate gender score for a candidate
     * @param candidate the candidate
     * @param genderCriteria the gender criteria
     * @return the score (weight if matches, 0 if not)
     */
    private int calculateGenderScore(Candidate candidate, GenderCriteria genderCriteria) {
        String criteriaGender = genderCriteria.getGender().toLowerCase();
        String candidateGender = candidate.getGender().toLowerCase();
        
        // If criteria is "any" or matches candidate's gender
        if ("any".equals(criteriaGender) || criteriaGender.equals(candidateGender)) {
            return genderCriteria.getWeight();
        }
        
        return 0;
    }
    
    /**
     * Calculate salary score for a candidate
     * @param candidate the candidate
     * @param salaryRangeCriteria the salary range criteria
     * @return the score (weight if matches, 0 if not)
     */
    private int calculateSalaryScore(Candidate candidate, SalaryRangeCriteria salaryRangeCriteria) {
        Integer candidateSalary = candidate.getCurrentSalary();
        
        if (candidateSalary >= salaryRangeCriteria.getMinimumSalary() && 
            candidateSalary <= salaryRangeCriteria.getMaximumSalary()) {
            return salaryRangeCriteria.getWeight();
        }
        
        return 0;
    }
}