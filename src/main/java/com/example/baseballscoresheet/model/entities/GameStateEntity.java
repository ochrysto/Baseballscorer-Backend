package com.example.baseballscoresheet.model.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Die Klasse {@link GameStateEntity} bildet ein GameState-Objekt mit seinen dazugehörigen Attributen ab.
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "game_state")
public class GameStateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer inning;

    private Integer positionCounter;

    private Integer battingOrder;

    @ManyToOne
    @JoinColumn(name = "lineup_player_id", nullable = false)
    private LineupTeamPlayerEntity lineupPlayer;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private GameEntity game;

    private Integer pa;

    private Integer ab;

    private Integer r;

    private Integer rbi;

    private Integer h;

    private Integer twoB;

    private Integer threeB;

    private Integer hr;

    private Integer k;

    private Integer bb;

    private Integer hp;

    private Integer sb;

    private Integer cs;

    private Integer sh;

    private Integer sf;

    private Integer a;

    private Integer po;

    private Integer e;

    private Integer dp;

    private Integer ip;
}
