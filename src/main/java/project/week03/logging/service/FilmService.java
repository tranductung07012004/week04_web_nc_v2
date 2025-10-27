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
    Page<FilmDTO> getAllFilms(Pageable pageable);
    FilmDTO getFilmById(Short id);
    List<FilmDTO> searchFilms(String title);

    // UPDATE
    FilmDTO updateFilm(Short id, FilmRequestDTO filmRequest);

    // DELETE
    void deleteFilm(Short id);
}