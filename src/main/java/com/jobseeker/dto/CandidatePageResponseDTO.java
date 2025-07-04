package com.jobseeker.dto;

import java.util.List;

public class CandidatePageResponseDTO {
    private List<CandidateResponseDTO> content;
    private long totalElements;
    private int totalPages;
    
    public CandidatePageResponseDTO() {}
    
    public CandidatePageResponseDTO(List<CandidateResponseDTO> content, long totalElements, int totalPages) {
        this.content = content;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }
    
    public List<CandidateResponseDTO> getContent() {
        return content;
    }
    
    public void setContent(List<CandidateResponseDTO> content) {
        this.content = content;
    }
    
    public long getTotalElements() {
        return totalElements;
    }
    
    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }
    
    public int getTotalPages() {
        return totalPages;
    }
    
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}