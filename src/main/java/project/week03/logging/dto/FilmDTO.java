package project.week03.logging.dto;


import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class FilmDTO {
    private Short filmId;
    private String title;
    private String description;
    private Integer releaseYear;
    private String languageName;
    private String originalLanguageName;
    private Byte rentalDuration;
    private BigDecimal rentalRate;
    private Short length;
    private BigDecimal replacementCost;
    private String rating;
    private String specialFeatures;
    private LocalDateTime lastUpdate;

    // Constructors
    public FilmDTO() {}

}