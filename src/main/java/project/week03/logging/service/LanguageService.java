package project.week03.logging.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.week03.logging.dto.LanguageDTO;
import project.week03.logging.dto.LanguageRequestDTO;

import java.util.List;

public interface LanguageService {
    
    LanguageDTO createLanguage(LanguageRequestDTO languageRequest);
    
    Page<LanguageDTO> getAllLanguages(Pageable pageable);
    LanguageDTO getLanguageById(Byte id);
    
    void deleteLanguage(Byte id);
}
