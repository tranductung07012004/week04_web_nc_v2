package project.week03.logging.dto;

import lombok.Data;

@Data
public class AuthResponseDTO {
    
    private String message;
    private String username;
    private String email;
    
    // Constructor for success responses
    public AuthResponseDTO(String message, String username, String email) {
        this.message = message;
        this.username = username;
        this.email = email;
    }
    
    // Constructor for error responses
    public AuthResponseDTO(String message) {
        this.message = message;
        this.username = null;
        this.email = null;
    }
}
