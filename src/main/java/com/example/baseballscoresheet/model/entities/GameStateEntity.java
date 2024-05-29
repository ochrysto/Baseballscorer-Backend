package com.example.baseballscoresheet.model.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "game_state")
@Getter
@Setter
public class GameStateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "game_id")
    private GameEntity game;

    @Column(name = "away_runs", nullable = false)
    private int awayRuns = 0;

    @Column(name = "home_runs", nullable = false)
    private int homeRuns = 0;

    @Column(name = "away_errors", nullable = false)
    private int awayErrors = 0;

    @Column(name = "home_errors", nullable = false)
    private int homeErrors = 0;

    @Column(name = "away_hits", nullable = false)
    private int awayHits = 0;

    @Column(name = "home_hits", nullable = false)
    private int homeHits = 0;

    @Column(name = "away_lob", nullable = false)
    private int awayLOB = 0;

    @Column(name = "home_lob", nullable = false)
    private int homeLOB = 0;
}
