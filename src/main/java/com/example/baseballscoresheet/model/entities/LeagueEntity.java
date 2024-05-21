package com.example.baseballscoresheet.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * Die Klasse {@link LeagueEntity} bildet ein League-Objekt mit seinen dazugehörigen Attributen ab.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "league")
public class LeagueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "league")
    private Set<TeamEntity> teamSet;

    @ManyToOne
    @JoinColumn(name="association_id", nullable=false)
    private AssociationEntity association;

    @OneToMany(mappedBy = "league")
    private Set<GameEntity> gameSet;

}
