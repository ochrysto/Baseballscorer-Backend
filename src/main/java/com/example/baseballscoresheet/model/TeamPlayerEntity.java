package com.example.baseballscoresheet.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Die Klasse {@link TeamPlayerEntity} bildet die n:m-Beziehung zwischen Team und Player mit ihren dazugehörigen Attributen ab.
 * Sie gibt an, welche Spieler für welches Team spielen.
 */
@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "team_player")
public class TeamPlayerEntity {
    /**
     * Attribut, das zur eindeutigen Identifikation einer Team-Player-Beziehung dient.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * {@link TeamEntity} bildet das Team dieser Beziehung ab.
     */
    @ManyToOne
    @JoinColumn(name = "team_id")
    private TeamEntity team;

    /**
     * {@link PlayerEntity} bildet den Spieler dieser Beziehung ab.
     */
    @ManyToOne
    @JoinColumn(name = "player_id")
    private PlayerEntity player;


}
