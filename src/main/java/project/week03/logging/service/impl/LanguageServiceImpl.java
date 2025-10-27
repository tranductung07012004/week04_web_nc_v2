package project.week03.logging.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.week03.logging.dto.LanguageDTO;
import project.week03.logging.dto.LanguageRequestDTO;
import project.week03.logging.entity.Language;
import project.week03.logging.exception.ResourceNotFoundException;
import project.week03.logging.repository.FilmRepository;
import project.week03.logging.repository.LanguageRepository;
import project.week03.logging.service.LanguageService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LanguageServiceImpl implements LanguageService {

    @Autowired
    private LanguageRepository languageRepository;
    
    @Autowired
    private FilmRepository filmRepository;

    // CREATE
    @Override
    public LanguageDTO createLanguage(LanguageRequestDTO languageRequest) {
        // Check if language name already exists
        if (languageRepository.findByName(languageRequest.getName()).isPresent()) {
            throw new IllegalArgumentException("Language with name '" + languageRequest.getName() + "' already exists");
        }
        
        Language language = convertToEntity(languageRequest);
        Language savedLanguage = languageRepository.save(language);
        return convertToDTO(savedLanguage);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LanguageDTO> getAllLanguages(Pageable pageable) {
        return languageRepository.findAll(pageable)
                .map(this::convertToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public LanguageDTO getLanguageById(Byte id) {
        Language language = languageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Language not found with id: " + id));
        return convertToDTO(language);
    }


    // DELETE - PREVENT DELETE STRATEGY
    @Override
    public void deleteLanguage(Byte id) {
        Language language = languageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Language not found with id: " + id));

        // Check if language is being used by any films
        long filmCount = countFilmsUsingLanguage(id);
        if (filmCount > 0) {
            throw new IllegalStateException(
                String.format("Cannot delete language '%s' because it is being used by %d film(s). " +
                            "Please reassign or delete those films first.", 
                            language.getName(), filmCount)
            );
        }

        languageRepository.deleteById(id);
    }

    // Helper method to count films using language
    @Transactional(readOnly = true)
    private long countFilmsUsingLanguage(Byte languageId) {
        Language language = languageRepository.findById(languageId)
                .orElseThrow(() -> new ResourceNotFoundException("Language not found with id: " + languageId));
        
        // Count films using this language as primary language
        long primaryUsage = filmRepository.findByLanguage(language).size();
        
        // Count films using this language as original language
        long originalUsage = filmRepository.findAll().stream()
                .filter(film -> film.getOriginalLanguage() != null && 
                               film.getOriginalLanguage().getLanguageId().equals(languageId))
                .count();
        
        return primaryUsage + originalUsage;
    }

    // Helper methods
    private Language convertToEntity(LanguageRequestDTO dto) {
        Language language = new Language();
        language.setName(dto.getName());
        return language;
    }

    private LanguageDTO convertToDTO(Language language) {
        LanguageDTO dto = new LanguageDTO();
        dto.setLanguageId(language.getLanguageId());
        dto.setName(language.getName());
        dto.setLastUpdate(language.getLastUpdate());
        return dto;
    }
}
