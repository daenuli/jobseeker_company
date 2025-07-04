package com.jobseeker.dto;

import java.util.List;

public class VacancyPageResponseDTO {
    private List<VacancyResponseDTO> content;
    private long totalElements;
    private int totalPages;


    public VacancyPageResponseDTO() {}
    
    public VacancyPageResponseDTO(List<VacancyResponseDTO> content, long totalElements, int totalPages) {
        this.content = content;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public List<VacancyResponseDTO> getContent() {
        return content;
    }

    public void setContent(List<VacancyResponseDTO> content) {
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
