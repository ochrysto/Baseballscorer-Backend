package com.example.baseballscoresheet.model;

import com.example.baseballscoresheet.enums.Position;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * Die Klasse {@link LineupPlayerEntity} bildet die n:m-Beziehung zwischen Lineup und Player mit ihren dazugehörigen Attributen ab.
 * Sie gibt an welche Spieler in welchem Lineup hinterlegt ist. Die Position gibt an, an welcher Position der Spieler spielt.
 */
@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lineup_player")
public class LineupPlayerEntity {
    /**
     * Attribut, das zur eindeutigen Identifikation einer Lineup-Player-Beziehung dient.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * {@link TeamEntity} bildet die zugehörige Team-Entity-Beziehung dieser Beziehung ab.
     */
    @ManyToOne
    @JoinColumn(name = "team_player_id")
    private TeamPlayerEntity teamPlayer;

    /**
     * {@link LineupEntity} bildet das Lineup dieser Beziehung ab.
     */
    @ManyToOne
    @JoinColumn(name = "lineup_id")
    private LineupEntity lineup;

    /**
     * {@link Position} spezifiziert die Position eines Spielers in dieser Beziehung.
     */
    private Position position;
}
