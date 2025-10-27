package project.week03.logging.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LanguageRequestDTO {
    
    @NotBlank(message = "Language name is required")
    @Size(max = 20, message = "Language name must not exceed 20 characters")
    private String name;
    
    public LanguageRequestDTO() {}
}
