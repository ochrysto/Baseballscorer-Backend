package com.example.baseballscoresheet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

/**
 * Die Klasse {@link ClubEntity} bildet ein Club-Objekt mit seinen dazugehörigen Attributen ab.
 * Ein Club ist ein Verein. Ein Verein kann mehrere Teams haben.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "club")
public class ClubEntity {

    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name des Clubs.
     */
    @NotBlank(message = "Name of the club is mandatory")
    private String name;

    /**
     *
     */
    private String clubLogo;

    /**
     *
     */
    private String city;

    /**
     *
     */
    private String email;

    /**
     *
     */
    @OneToMany(mappedBy = "club")
    private Set<TeamEntity> teamSet;

    /**
     *
     */
    @ManyToOne
    @JoinColumn(name = "association_id", nullable = false)
    private AssociationEntity association;
}
