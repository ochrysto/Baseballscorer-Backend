package com.example.baseballscoresheet.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * Die Klasse {@link TeamEntity} bildet ein Team-Objekt mit seinen dazugehörigen Attributen ab.
 * Ein Team besteht aus mehreren Spielern.
 */
@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "team")
public class TeamEntity {
    /**
     * Attribut, das zur eindeutigen Identifikation eines Team-Objekts dient.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name des Teams.
     */
    private String name;

    /**
     *
     */
    @OneToMany(mappedBy = "team")
    private Set<TeamPlayerEntity> teamplayer;

    /**
     *
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    private ManagerEntity manager;

    /**
     *
     */
    @ManyToOne
    @JoinColumn(name="game_gameNr", nullable = false)
    private GameEntity game;

    /**
     *
     */
    @ManyToOne
    @JoinColumn(name="club_id", nullable = false)
    private ClubEntity club;

    /**
     *
     */
    @ManyToOne
    @JoinColumn(name="league_id", nullable = false)
    private LeagueEntity league;
}
