package project.week03.logging.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
@Table(name = "film")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id")
    private Short filmId;

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Min(value = 1900, message = "Release year must be after 1900")
    @Max(value = 2100, message = "Release year must be before 2100")
    @Column(name = "release_year", columnDefinition = "YEAR")
    private Integer releaseYear;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "original_language_id")
    private Language originalLanguage;

    @Min(value = 1, message = "Rental duration must be at least 1")
    @Max(value = 7, message = "Rental duration must not exceed 7")
    @Column(name = "rental_duration", nullable = false)
    private Byte rentalDuration = 3;

    @DecimalMin(value = "0.0", inclusive = false, message = "Rental rate must be positive")
    @Digits(integer = 2, fraction = 2, message = "Rental rate must have at most 2 decimal places")
    @Column(name = "rental_rate", precision = 4, scale = 2, nullable = false)
    private BigDecimal rentalRate = new BigDecimal("4.99");

    @Min(value = 1, message = "Length must be at least 1 minute")
    @Column(name = "length")
    private Short length;

    @DecimalMin(value = "0.0", inclusive = false, message = "Replacement cost must be positive")
    @Digits(integer = 3, fraction = 2, message = "Replacement cost must have at most 2 decimal places")
    @Column(name = "replacement_cost", precision = 5, scale = 2, nullable = false)
    private BigDecimal replacementCost = new BigDecimal("19.99");

    @Column(name = "rating", columnDefinition = "ENUM('G','PG','PG-13','R','NC-17')")
    private String rating = "G";

    @Column(name = "special_features")
    private String specialFeatures;

    @CreationTimestamp
    @Column(name = "last_update", nullable = false, updatable = false)
    private LocalDateTime lastUpdate;

    // Constructors
    public Film() {}

    public Film(String title, String description, Integer releaseYear, Language language) {
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.language = language;
    }
}
