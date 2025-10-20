package project.week03.logging.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
@Table(name = "language")
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "language_id")
    private Byte languageId;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @CreationTimestamp
    @Column(name = "last_update", nullable = false, updatable = false)
    private LocalDateTime lastUpdate;

    // Constructors
    public Language() {
    }

    public Language(String name) {
        this.name = name;
    }
}
