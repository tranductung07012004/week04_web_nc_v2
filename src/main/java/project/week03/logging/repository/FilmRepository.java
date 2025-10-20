package project.week03.logging.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.week03.logging.entity.Film;
import project.week03.logging.entity.Language;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Short> {

    // Find by title (caseinsensitive)
    List<Film> findByTitleContainingIgnoreCase(String title);

    // Find by release year
    List<Film> findByReleaseYear(Integer year);

    // Find by rating
    List<Film> findByRating(String rating);

    // Find by language
    List<Film> findByLanguage(Language language);

    // Find by rental rate range
    @Query("SELECT f FROM Film f WHERE f.rentalRate BETWEEN :minRate AND :maxRate")
    List<Film> findByRentalRateRange(@Param("minRate") BigDecimal minRate,
                                     @Param("maxRate") BigDecimal maxRate);

    // Find by length range
    @Query("SELECT f FROM Film f WHERE f.length BETWEEN :minLength AND :maxLength")
    List<Film> findByLengthRange(@Param("minLength") Short minLength,
                                 @Param("maxLength") Short maxLength);

    // Find films with pagination
    Page<Film> findAll(Pageable pageable);

    // Find by title with pagination
    Page<Film> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    // Custom query for search
    @Query("SELECT f FROM Film f WHERE " +
            "(:title IS NULL OR LOWER(f.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
            "(:year IS NULL OR f.releaseYear = :year) AND " +
            "(:rating IS NULL OR f.rating = :rating) AND " +
            "(:languageId IS NULL OR f.language.languageId = :languageId)")
    List<Film> findFilmsWithFilters(@Param("title") String title,
                                    @Param("year") Integer year,
                                    @Param("rating") String rating,
                                    @Param("languageId") Byte languageId);
}