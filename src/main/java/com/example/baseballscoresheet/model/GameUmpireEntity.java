package com.example.baseballscoresheet.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Die Klasse {@link GameUmpireEntity} bildet ein GameHistory-Objekt mit seinen dazugehörigen Attributen ab.
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
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "gameNr")
    private GameEntity game;

    @ManyToOne
    @JoinColumn(name = "umpire_passnumber")
    private UmpireEntity umpire;
}
