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
    private Integer innings;

    @OneToMany(mappedBy = "game")
    private Set<GameUmpireEntity> gameUmpire;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "scorer_id", referencedColumnName = "id")
    private ScorerEntity scorer;

    @ManyToOne
    @JoinColumn(name = "association_id", nullable = false)
    private AssociationEntity association;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "guest_lineup_id", referencedColumnName = "id")
    private LineupEntity guest;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "host_lineup_id", referencedColumnName = "id")
    private LineupEntity host;

    @ManyToOne
    @JoinColumn(name = "league_id", nullable = false)
    private LeagueEntity league;

    // CascadeType.MERGE because of an error "detached entity passed to persist"
    // See: https://www.baeldung.com/hibernate-detached-entity-passed-to-persist
    @OneToOne(mappedBy = "game", cascade = CascadeType.MERGE, fetch = FetchType.LAZY, optional = false)
    private GameStateEntity gameState;
}
