package project.week03.logging.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.week03.logging.dto.FilmDTO;
import project.week03.logging.dto.FilmRequestDTO;
import project.week03.logging.service.FilmService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/films")
@CrossOrigin(origins = "*")
public class FilmController {

    @Autowired
    private FilmService filmService;

    // 1. CREATE - Tạo phim mới
    @PostMapping
    public ResponseEntity<FilmDTO> createFilm(@Valid @RequestBody FilmRequestDTO filmRequest) {
        FilmDTO createdFilm = filmService.createFilm(filmRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFilm);
    }

    // 2. READ - Lấy danh sách tất cả phim
    @GetMapping
    public ResponseEntity<List<FilmDTO>> getAllFilms(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<FilmDTO> films = filmService.getAllFilms(pageable);
        return ResponseEntity.ok(films.getContent());
    }

    // 3. READ - Lấy phim theo ID
    @GetMapping("/{id}")
    public ResponseEntity<FilmDTO> getFilmById(@PathVariable Short id) {
        FilmDTO film = filmService.getFilmById(id);
        return ResponseEntity.ok(film);
    }

    // 4. UPDATE - Cập nhật phim
    @PutMapping("/{id}")
    public ResponseEntity<FilmDTO> updateFilm(@PathVariable Short id,
                                              @Valid @RequestBody FilmRequestDTO filmRequest) {
        FilmDTO updatedFilm = filmService.updateFilm(id, filmRequest);
        return ResponseEntity.ok(updatedFilm);
    }

    // 5. DELETE - Xóa phim
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilm(@PathVariable Short id) {
        filmService.deleteFilm(id);
        return ResponseEntity.noContent().build();
    }

    // Bonus endpoints
    @GetMapping("/search")
    public ResponseEntity<List<FilmDTO>> searchFilms(@RequestParam String title) {
        List<FilmDTO> films = filmService.searchFilms(title);
        return ResponseEntity.ok(films);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<FilmDTO>> filterFilms(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String rating,
            @RequestParam(required = false) Byte languageId) {
        List<FilmDTO> films = filmService.findFilmsWithFilters(title, year, rating, languageId);
        return ResponseEntity.ok(films);
    }

    @GetMapping("/year/{year}")
    public ResponseEntity<List<FilmDTO>> getFilmsByYear(@PathVariable Integer year) {
        List<FilmDTO> films = filmService.findByReleaseYear(year);
        return ResponseEntity.ok(films);
    }

    @GetMapping("/rating/{rating}")
    public ResponseEntity<List<FilmDTO>> getFilmsByRating(@PathVariable String rating) {
        List<FilmDTO> films = filmService.findByRating(rating);
        return ResponseEntity.ok(films);
    }

    @GetMapping("/rental-rate")
    public ResponseEntity<List<FilmDTO>> getFilmsByRentalRateRange(
            @RequestParam BigDecimal minRate,
            @RequestParam BigDecimal maxRate) {
        List<FilmDTO> films = filmService.findByRentalRateRange(minRate, maxRate);
        return ResponseEntity.ok(films);
    }
}