package com.example.baseballscoresheet.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * Die Klasse {@link LeagueEntity} bildet ein League-Objekt mit seinen dazugehörigen Attributen ab.
 */
@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "league")
public class LeagueEntity {

    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     *
     */
    private String name;

    /**
     *
     */
    @OneToMany(mappedBy = "league")
    private Set<TeamEntity> teamSet;

    @ManyToOne
    @JoinColumn(name="association_id", nullable=false)
    private AssociationEntity association;
}
