package com.example.baseballscoresheet.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

/**
 * The Class {@link GameEntity} represents a game object with its associated attributes.
 * A game object stores all data and information about a game.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "game")
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private Integer gameNr;  // TODO: why not auto-increment?

    private LocalDate date;

    private String location;

    private LocalTime startTime;

    private LocalTime endTime;

    @Column(name = "duration_in_minutes")
    private Long durationInMinutes;

    private Integer attendance;

    /**
     * Number of innings played during a game.
     * In baseball, an inning is a period of play.
     * An inning is over when both teams have batted once and been on defense once.
     * This changes after every three 'Outs'.
     */
    @Column(nullable = false)
    private Integer innings;  // TODO: is it just a counter of the innings played?

    @OneToMany(mappedBy = "game")
    private Set<GameUmpireEntity> gameUmpire;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "scorer_id", referencedColumnName = "id")
    private ScorerEntity scorer;

    @ManyToOne
    @JoinColumn(name = "association_id", nullable = false)
    private AssociationEntity association;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "guest_team_id", referencedColumnName = "id")
    private TeamEntity guest;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "host_team_id", referencedColumnName = "id")
    private TeamEntity host;

    @ManyToOne
    @JoinColumn(name = "league_id", nullable = false)
    private LeagueEntity league;

    @OneToOne(mappedBy = "game", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private GameStateEntity gameState;
}
