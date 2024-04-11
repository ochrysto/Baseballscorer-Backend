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
     * Liste aller Teammitglieder.
     */
    @OneToMany(mappedBy = "team")
    private Set<TeamPlayerEntity> teamplayer;

    /**
     * Manager des Teams.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    private ManagerEntity manager;

    /**
     * Verein des Teams.
     */
    @ManyToOne
    @JoinColumn(name = "club_id", nullable = false)
    private ClubEntity club;

    /**
     * Liga, in der das Team spielt.
     */
    @ManyToOne
    @JoinColumn(name = "league_id", nullable = false)
    private LeagueEntity league;

    /**
     *
     */
    @OneToMany(mappedBy = "team")
    private Set<LineupEntity> lineups;

    private String teamLogo;
}
