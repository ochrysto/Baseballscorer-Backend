package com.example.baseballscoresheet.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * Die Klasse {@link AssociationEntity} represents an association object with its associated attributes
 * Association = "Verband"
 * An association is an association of several sports clubs of different or the same sports.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "association")
public class AssociationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    @OneToMany(mappedBy = "association")
    private Set<LeagueEntity> leagueSet;

    @Column(name = "club")
    @OneToMany(mappedBy = "association")
    private Set<ClubEntity> clubSet;

    @Column(name = "game")
    @OneToMany(mappedBy = "association")
    private Set<GameEntity> gameSet;
}
