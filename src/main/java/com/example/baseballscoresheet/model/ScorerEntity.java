package com.example.baseballscoresheet.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Die Klasse {@link ScorerEntity} bildet ein Scorer-Objekt mit seinen dazugehörigen Attributen ab.
 * Der Scorer erfasst aller relevanten Aktionen eines Spiels.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "scorer")
public class ScorerEntity {

    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     *
     */
    private Long passnumber;

    /**
     * Vorname des Scorers.
     */
    private String firstName;

    /**
     * Nachname des Scorers.
     */
    private String lastName;

    /**
     *
     */
    @OneToOne(mappedBy = "scorer")
    private GameEntity game;
}
