package com.example.baseballscoresheet.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

//TODO bisschen mehr Infos zur Lineup Entität

/**
 * Die Klasse {@link LineupEntity} bildet ein Lineup-Objekt mit seinen dazugehörigen Attributen ab.
 */
@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lineup")
public class LineupEntity {
    /**
     * Attribut, das zur eindeutigen Identifikation eines Lineup-Objekts dient.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * {@link TeamEntity} bildet die Game-Team-Beziehung der Beziehung ab.
     */
    private GameTeamEntity gameTeam;

    /**
     * {@link ManagerEntity} bildet den Manager in der Beziehung ab.
     */
    private ManagerEntity manager;

    @OneToMany(mappedBy = "lineup")
    private Set<LineupPlayerEntity> lineupPlayers;
}
