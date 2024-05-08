package com.example.baseballscoresheet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

/**
 * The class {@link ClubEntity} represents a club object with its associated attributes.
 * A club is an association. A club can have several teams.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "club")
public class ClubEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name des Clubs.
     */
    @NotBlank(message = "Name of the club is mandatory")
    private String name;

    private String clubLogo;

    private String city;

    private String email;

    @OneToMany(mappedBy = "club")
    private Set<TeamEntity> teamSet;

    @ManyToOne
    @JoinColumn(name = "association_id", nullable = false)
    private AssociationEntity association;
}
