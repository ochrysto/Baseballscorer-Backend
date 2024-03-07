package com.example.baseballscoresheet.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Die Klasse {@link ScorerEntity} bildet ein Scorer-Objekt mit seinen dazugehörigen Attributen ab.
 * Der Scorer erfasst aller relevanten Aktionen eines Spiels.
 */
@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "scorer")
public class ScorerEntity {
    /**
     * Attribut, das zur eindeutigen Identifikation eines Scorer-Objekts dient.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Vorname des Scorers.
     */
    private String firstName;

    /**
     * Nachname des Scorers.
     */
    private String lastName;

    /**
     * E-Mail des Scorers.
     */
    private String email;
}
