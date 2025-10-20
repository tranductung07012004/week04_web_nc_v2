package project.week03.logging.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.week03.logging.entity.Language;

import java.util.Optional;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Byte> {
    Optional<Language> findByName(String name);
}