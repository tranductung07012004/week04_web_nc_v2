package project.week03.logging.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.week03.logging.dto.LanguageDTO;
import project.week03.logging.dto.LanguageRequestDTO;

import java.util.List;

public interface LanguageService {
    
    // CREATE
    LanguageDTO createLanguage(LanguageRequestDTO languageRequest);
    
    // READ
    Page<LanguageDTO> getAllLanguages(Pageable pageable);
    LanguageDTO getLanguageById(Byte id);
    
    // DELETE
    void deleteLanguage(Byte id);
}
