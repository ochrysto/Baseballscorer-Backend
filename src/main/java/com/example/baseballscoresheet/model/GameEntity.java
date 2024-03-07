package com.example.baseballscoresheet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Die Klasse {@link GameEntity} bildet ein Game-Objekt mit seinen dazugehörigen Attributen ab.
 * Ein Game-Objekt speichert sämtliche Daten und Informationen zu einem Spiel.
 */
@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "game")
public class GameEntity {
    /**
     * Attribut, das zur eindeutigen Identifikation eines Game-Objekts dient.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Datum, an dem das Spiel stattfindet.
     */
    @NotBlank(message = "Date of the game is mandatory")
    private LocalDate date;

    /**
     * Ort/Stadt, an dem oder in der das Spiel stattfindet.
     */
    private String location;

    /**
     * Zeit, zu der das Spiel gestartet wurde.
     */
    private LocalTime startTime;

    /**
     * Zeit, zu der das Spiel beendet wurde.
     */
    private LocalTime endTime;

    //TODO was ist in diesem Kontext eine League?
    private LeagueEntity league;

    //TODO was ist in diesem Kontext ein Verband?
    private AssociationEntity association;

    //TODO reicht int als Datentyp oder lieber Long? / Gibt sie die Spielnummer der Liga/des Verbandes etc. an?
    private Integer gameNumber;

    //TODO was ist timeOfGame? die Länge? - dann aus Start- und Endzeit berechnen lassen
    private Double timeOfGame;

    //TODO ???
    private String attendance;


    /**
     * Der Scorer {@link ScorerEntity} ist die Person, die das Spiel dokumentiert.
     * Die Dokumentation erfolgt mithilfe eines Scoresheets.
     */
    private ScorerEntity scorer;

    /**
     * Anzahl der Innings, die während eines Spiels gespielt wurden.
     * Ein Inning ist im Baseball ein Spielabschnitt. Ein Inning ist beendet, wenn beide Mannschaften einmal Schlagrecht hatten und einmal in der Verteidigung waren.
     * Dies wechselt jeweils nach drei Out
     */
    private Integer innings;
}
