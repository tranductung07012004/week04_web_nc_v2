package project.week03.logging.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.week03.logging.dto.LanguageDTO;
import project.week03.logging.dto.LanguageRequestDTO;
import project.week03.logging.service.LanguageService;

import java.util.List;

@RestController
@RequestMapping("/api/languages")
@CrossOrigin(origins = "*")
public class LanguageController {

    @Autowired
    private LanguageService languageService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER')")
    public ResponseEntity<LanguageDTO> createLanguage(@Valid @RequestBody LanguageRequestDTO languageRequest) {
        LanguageDTO createdLanguage = languageService.createLanguage(languageRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLanguage);
    }

    @GetMapping
    public ResponseEntity<List<LanguageDTO>> getAllLanguages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<LanguageDTO> languages = languageService.getAllLanguages(pageable);
        return ResponseEntity.ok(languages.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LanguageDTO> getLanguageById(@PathVariable Byte id) {
        LanguageDTO language = languageService.getLanguageById(id);
        return ResponseEntity.ok(language);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteLanguage(@PathVariable Byte id) {
        languageService.deleteLanguage(id);
        return ResponseEntity.noContent().build();
    }
}
