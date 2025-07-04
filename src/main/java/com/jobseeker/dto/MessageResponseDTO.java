package com.jobseeker.dto;

public class MessageResponseDTO {
    
    private String message;
    
    // Default constructor
    public MessageResponseDTO() {}
    
    // Constructor with message
    public MessageResponseDTO(String message) {
        this.message = message;
    }
    
    // Getter and Setter
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}