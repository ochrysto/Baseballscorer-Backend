package com.example.baseballscoresheet.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Die Klasse {@link GameUmpireEntity} bildet die n:m-Beziehung zwischen Game und Umpire mit ihren dazugehörigen Attributen ab.
 * Es wird abgebildet, welche Schiedsrichter in welchem Spiel partizipiert haben.
 */
@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "game_umpire")
public class GameUmpireEntity {
    /**
     * Attribut, das zur eindeutigen Identifikation einer Game-Umpire-Beziehung dient.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * {@link GameEntity} bildet das Spiel der Beziehung ab.
     */
    @ManyToOne
    @JoinColumn(name = "game_id")
    private GameEntity game;

    /**
     * {@link UmpireEntity} bildet den Schiedsrichter der Beziehung ab.
     */
    @ManyToOne
    @JoinColumn(name = "umpire_id")
    private UmpireEntity umpire;
}
