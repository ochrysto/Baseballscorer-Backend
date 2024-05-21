package com.example.baseballscoresheet.model.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Die Klasse {@link GameUmpireEntity} bildet ein GameState-Objekt mit seinen dazugehörigen Attributen ab.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "game_umpire")
public class GameUmpireEntity {

    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private GameEntity game;

    @ManyToOne
    @JoinColumn(name = "umpire_id")
    private UmpireEntity umpire;
}
