package com.example.baseballscoresheet.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * Die Klasse {@link TeamEntity} bildet ein Team-Objekt mit seinen dazugehörigen Attributen ab.
 * Ein Team besteht aus mehreren Spielern.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "team")
public class TeamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team")
    private Set<TeamPlayerEntity> teamplayer;

    @OneToOne
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    private ManagerEntity manager;

    @ManyToOne
    @JoinColumn(name = "club_id", nullable = false)
    private ClubEntity club;

    @ManyToOne
    @JoinColumn(name = "league_id", nullable = false)
    private LeagueEntity league;

    @OneToMany(mappedBy = "team")
    private Set<LineupEntity> lineups;

    private String teamLogo;

}
