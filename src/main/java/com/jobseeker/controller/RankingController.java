package com.jobseeker.controller;

import com.jobseeker.dto.CandidateRankingDTO;
import com.jobseeker.service.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ranking")
@CrossOrigin(origins = "*")
public class RankingController {
    
    private final RankingService rankingService;
    
    @Autowired
    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }
    
    /**
     * Rank all candidates for a specific vacancy
     * @param vacancyId the vacancy ID
     * @return list of ranked candidates
     */
    @GetMapping("/vacancy/{vacancyId}/candidates")
    public ResponseEntity<List<CandidateRankingDTO>> rankCandidatesForVacancy(@PathVariable String vacancyId) {
        List<CandidateRankingDTO> rankedCandidates = rankingService.rankCandidatesForVacancy(vacancyId);
        return ResponseEntity.ok(rankedCandidates);
    }
}