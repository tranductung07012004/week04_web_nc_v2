package project.week03.logging.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.week03.logging.dto.FilmDTO;
import project.week03.logging.dto.FilmRequestDTO;
import project.week03.logging.entity.Film;
import project.week03.logging.entity.Language;
import project.week03.logging.exception.ResourceNotFoundException;
import project.week03.logging.repository.FilmRepository;
import project.week03.logging.repository.LanguageRepository;
import project.week03.logging.service.FilmService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FilmServiceImpl implements FilmService {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private LanguageRepository languageRepository;

    // CREATE
    @Override
    public FilmDTO createFilm(FilmRequestDTO filmRequest) {
        Film film = convertToEntity(filmRequest);
        Film savedFilm = filmRepository.save(film);
        return convertToDTO(savedFilm);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FilmDTO> getAllFilms(Pageable pageable) {
        return filmRepository.findAll(pageable)
                .map(this::convertToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public FilmDTO getFilmById(Short id) {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Film not found with id: " + id));
        return convertToDTO(film);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FilmDTO> searchFilms(String title) {
        return filmRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // UPDATE
    @Override
    public FilmDTO updateFilm(Short id, FilmRequestDTO filmRequest) {
        Film existingFilm = filmRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Film not found with id: " + id));

        updateFilmFromRequest(existingFilm, filmRequest);
        Film updatedFilm = filmRepository.save(existingFilm);
        return convertToDTO(updatedFilm);
    }

    // DELETE
    @Override
    public void deleteFilm(Short id) {
        if (!filmRepository.existsById(id)) {
            throw new ResourceNotFoundException("Film not found with id: " + id);
        }
        filmRepository.deleteById(id);
    }

    // Helper methods
    private Film convertToEntity(FilmRequestDTO dto) {
        Film film = new Film();
        film.setTitle(dto.getTitle());
        film.setDescription(dto.getDescription());
        film.setReleaseYear(dto.getReleaseYear());
        film.setRentalDuration(dto.getRentalDuration());
        film.setRentalRate(dto.getRentalRate());
        film.setLength(dto.getLength());
        film.setReplacementCost(dto.getReplacementCost());
        film.setRating(dto.getRating());
        film.setSpecialFeatures(dto.getSpecialFeatures());

        // Set language
        Language language = languageRepository.findById(dto.getLanguageId())
                .orElseThrow(() -> new ResourceNotFoundException("Language not found with id: " + dto.getLanguageId()));
        film.setLanguage(language);

        // Set original language if provided
        if (dto.getOriginalLanguageId() != null) {
            Language originalLanguage = languageRepository.findById(dto.getOriginalLanguageId())
                    .orElseThrow(() -> new ResourceNotFoundException("Original language not found with id: " + dto.getOriginalLanguageId()));
            film.setOriginalLanguage(originalLanguage);
        }

        return film;
    }

    private FilmDTO convertToDTO(Film film) {
        FilmDTO dto = new FilmDTO();
        dto.setFilmId(film.getFilmId());
        dto.setTitle(film.getTitle());
        dto.setDescription(film.getDescription());
        dto.setReleaseYear(film.getReleaseYear());
        dto.setLanguageName(film.getLanguage() != null ? film.getLanguage().getName() : null);
        dto.setOriginalLanguageName(film.getOriginalLanguage() != null ? film.getOriginalLanguage().getName() : null);
        dto.setRentalDuration(film.getRentalDuration());
        dto.setRentalRate(film.getRentalRate());
        dto.setLength(film.getLength());
        dto.setReplacementCost(film.getReplacementCost());
        dto.setRating(film.getRating());
        dto.setSpecialFeatures(film.getSpecialFeatures());
        dto.setLastUpdate(film.getLastUpdate());
        return dto;
    }

    private void updateFilmFromRequest(Film film, FilmRequestDTO dto) {
        film.setTitle(dto.getTitle());
        film.setDescription(dto.getDescription());
        film.setReleaseYear(dto.getReleaseYear());
        film.setRentalDuration(dto.getRentalDuration());
        film.setRentalRate(dto.getRentalRate());
        film.setLength(dto.getLength());
        film.setReplacementCost(dto.getReplacementCost());
        film.setRating(dto.getRating());
        film.setSpecialFeatures(dto.getSpecialFeatures());

        // Update language
        Language language = languageRepository.findById(dto.getLanguageId())
                .orElseThrow(() -> new ResourceNotFoundException("Language not found with id: " + dto.getLanguageId()));
        film.setLanguage(language);

        // Update original language
        if (dto.getOriginalLanguageId() != null) {
            Language originalLanguage = languageRepository.findById(dto.getOriginalLanguageId())
                    .orElseThrow(() -> new ResourceNotFoundException("Original language not found with id: " + dto.getOriginalLanguageId()));
            film.setOriginalLanguage(originalLanguage);
        } else {
            film.setOriginalLanguage(null);
        }
    }
}