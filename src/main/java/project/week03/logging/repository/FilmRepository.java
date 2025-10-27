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

    List<Film> findByTitleContainingIgnoreCase(String title);

    List<Film> findByLanguage(Language language);

    @Query("SELECT f FROM Film f WHERE f.rentalRate BETWEEN :minRate AND :maxRate")
    List<Film> findByRentalRateRange(@Param("minRate") BigDecimal minRate,
                                     @Param("maxRate") BigDecimal maxRate);

    @Query("SELECT f FROM Film f WHERE f.length BETWEEN :minLength AND :maxLength")
    List<Film> findByLengthRange(@Param("minLength") Short minLength,
                                 @Param("maxLength") Short maxLength);

    Page<Film> findAll(Pageable pageable);

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