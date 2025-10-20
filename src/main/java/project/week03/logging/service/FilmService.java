package project.week03.logging.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.week03.logging.dto.FilmDTO;
import project.week03.logging.dto.FilmRequestDTO;
import project.week03.logging.entity.Film;

import java.math.BigDecimal;
import java.util.List;

public interface FilmService {

    // CREATE
    FilmDTO createFilm(FilmRequestDTO filmRequest);

    // READ
    List<FilmDTO> getAllFilms();
    Page<FilmDTO> getAllFilms(Pageable pageable);
    FilmDTO getFilmById(Short id);
    List<FilmDTO> searchFilms(String title);
    Page<FilmDTO> searchFilms(String title, Pageable pageable);
    List<FilmDTO> findFilmsWithFilters(String title, Integer year, String rating, Byte languageId);

    // UPDATE
    FilmDTO updateFilm(Short id, FilmRequestDTO filmRequest);

    // DELETE
    void deleteFilm(Short id);

    // Additional methods
    List<FilmDTO> findByReleaseYear(Integer year);
    List<FilmDTO> findByRating(String rating);
    List<FilmDTO> findByRentalRateRange(BigDecimal minRate, BigDecimal maxRate);
    List<FilmDTO> findByLengthRange(Short minLength, Short maxLength);
}