package com.example.baseballscoresheet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

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
    private Long gameNr;

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

    /**
     *
     */
    private Integer gameNumber;

    /**
     * Dauer bzw. Länge des Spiels.
     * Aus Start- und Endzeit berechnen lassen
     */
    private Double timeOfGame;

    /**
     * Zuschauerzahl
     */
    private Integer attendance;

    /**
     * Anzahl der Innings, die während eines Spiels gespielt wurden.
     * Ein Inning ist im Baseball ein Spielabschnitt. Ein Inning ist beendet, wenn beide Mannschaften einmal Schlagrecht hatten und einmal in der Verteidigung waren.
     * Dies wechselt jeweils nach drei Out
     */
    private Integer innings;

    /**
     *
     */
    @OneToMany(mappedBy = "game")
    private Set<GameUmpireEntity> gameUmpire;

    /**
     *
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "scorer_passnumber", referencedColumnName = "passnumber")
    private ScorerEntity scorer;

    /**
     *
     */
    @OneToMany(mappedBy = "game")
    private Set<TeamEntity> teams;

    /**
     *
     */
    @ManyToOne
    @JoinColumn(name = "association_id", nullable = false)
    private AssociationEntity association;
}
