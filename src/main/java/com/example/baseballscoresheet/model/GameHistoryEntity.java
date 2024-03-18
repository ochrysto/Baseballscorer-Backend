package com.example.baseballscoresheet.model;

import com.example.baseballscoresheet.enums.POSITION;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * Die Klasse {@link GameHistoryEntity} bildet ein GameHistory-Objekt mit seinen dazugehörigen Attributen ab.
 */

@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "game_history")
public class GameHistoryEntity {

    /**
     * Attribut, das zur eindeutigen Identifikation eines Association-Objekts dient.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     *
     */
    @ManyToOne
    @JoinColumn(name="player_passnumber", nullable = false)
    private PlayerEntity player;

    /**
     *
     */
    @ManyToOne
    @JoinColumn(name="game_nr", nullable = false)
    private GameEntity game;

    @Enumerated(EnumType.STRING)
    private POSITION position;

    private Integer positionCounter;

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
