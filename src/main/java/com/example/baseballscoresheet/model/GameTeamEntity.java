package com.example.baseballscoresheet.model;

import com.example.baseballscoresheet.enums.Type;
import jakarta.persistence.*;
import lombok.*;

/**
 * Die Klasse {@link GameTeamEntity} bildet die n:m-Beziehung zwischen Game und Team mit ihren dazugehörigen Attributen ab.
 * Es wird abgebildet, welche Teams in welchem Spiel teilgenommen haben.
 * Weiterhin wird die Beziehung durch einen Type {@link Type} näher definiert.
 */
@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "player")
public class GameTeamEntity {
    /**
     * Attribut, das zur eindeutigen Identifikation einer Game-Team-Zuordnung dient.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * {@link TeamEntity} bildet das Team der Beziehung ab.
     */
    private TeamEntity team;

    /**
     * {@link GameEntity} bildet das Spiel der Beziehung ab.
     */
    private GameEntity game;

    //TODO was ist in diesem Kontext ein Type?
    /**
     * * {@link Type} spezifiziert die Beziehung zwischen Team und Spiel.
     */
    private Type type;
}
