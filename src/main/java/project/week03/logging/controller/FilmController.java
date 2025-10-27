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

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<FilmDTO> createFilm(@Valid @RequestBody FilmRequestDTO filmRequest) {
        System.out.println("DEBUG: FilmController.createFilm() called - Authorization passed!");
        FilmDTO createdFilm = filmService.createFilm(filmRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFilm);
    }

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

    @GetMapping("/{id}")
    public ResponseEntity<FilmDTO> getFilmById(@PathVariable Short id) {
        FilmDTO film = filmService.getFilmById(id);
        return ResponseEntity.ok(film);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<FilmDTO> updateFilm(@PathVariable Short id,
                                              @Valid @RequestBody FilmRequestDTO filmRequest) {
        FilmDTO updatedFilm = filmService.updateFilm(id, filmRequest);
        return ResponseEntity.ok(updatedFilm);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteFilm(@PathVariable Short id) {
        filmService.deleteFilm(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<FilmDTO>> searchFilms(@RequestParam String title) {
        List<FilmDTO> films = filmService.searchFilms(title);
        return ResponseEntity.ok(films);
    }
}