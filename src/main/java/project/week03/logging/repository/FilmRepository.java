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

    Page<Film> findAll(Pageable pageable);
}