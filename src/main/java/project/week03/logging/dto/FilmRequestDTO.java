package project.week03.logging.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class FilmRequestDTO {

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;

    private String description;

    @Min(value = 1900, message = "Release year must be after 1900")
    @Max(value = 2100, message = "Release year must be before 2100")
    private Integer releaseYear;

    @NotNull(message = "Language ID is required")
    private Byte languageId;

    private Byte originalLanguageId;

    @Min(value = 1, message = "Rental duration must be at least 1")
    @Max(value = 7, message = "Rental duration must not exceed 7")
    private Byte rentalDuration = 3;

    @DecimalMin(value = "0.0", inclusive = false, message = "Rental rate must be positive")
    @Digits(integer = 2, fraction = 2, message = "Rental rate must have at most 2 decimal places")
    private BigDecimal rentalRate = new BigDecimal("4.99");

    @Min(value = 1, message = "Length must be at least 1 minute")
    private Short length;

    @DecimalMin(value = "0.0", inclusive = false, message = "Replacement cost must be positive")
    @Digits(integer = 3, fraction = 2, message = "Replacement cost must have at most 2 decimal places")
    private BigDecimal replacementCost = new BigDecimal("19.99");

    private String rating = "G";

    private String specialFeatures;

    // Constructors
    public FilmRequestDTO() {}

}