package com.jobseeker.dto;

public class CandidateRankingDTO {
    private int rank;
    private String candidateId;
    private String candidateName;
    private String candidateEmail;
    private int score;
    
    // Default constructor
    public CandidateRankingDTO() {}
    
    // Constructor with all fields
    public CandidateRankingDTO(int rank, String candidateId, String candidateName, String candidateEmail, int score) {
        this.rank = rank;
        this.candidateId = candidateId;
        this.candidateName = candidateName;
        this.candidateEmail = candidateEmail;
        this.score = score;
    }
    
    // Getters and Setters
    public int getRank() {
        return rank;
    }
    
    public void setRank(int rank) {
        this.rank = rank;
    }
    
    public String getCandidateId() {
        return candidateId;
    }
    
    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }
    
    public String getCandidateName() {
        return candidateName;
    }
    
    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }
    
    public String getCandidateEmail() {
        return candidateEmail;
    }
    
    public void setCandidateEmail(String candidateEmail) {
        this.candidateEmail = candidateEmail;
    }
    
    public int getScore() {
        return score;
    }
    
    public void setScore(int score) {
        this.score = score;
    }
}