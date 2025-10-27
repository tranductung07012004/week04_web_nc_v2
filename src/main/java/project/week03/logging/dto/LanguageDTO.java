package project.week03.logging.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class LanguageDTO {
    private Byte languageId;
    private String name;
    private LocalDateTime lastUpdate;
    
    public LanguageDTO() {}
}
