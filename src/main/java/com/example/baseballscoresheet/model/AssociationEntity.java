package com.example.baseballscoresheet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

/**
 * Die Klasse {@link AssociationEntity} bildet ein Association-Objekt mit seinen dazugehörigen Attributen ab.
 * Eine Association ist ein Verband. Ein Verband ist ein Zusammenschluss mehrerer Sportvereine verschiedener oder gleicher Sportarten.
 */
@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "association")
public class AssociationEntity {
    /**
     * Attribut, das zur eindeutigen Identifikation eines Association-Objekts dient.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    /**
     * Name des Verbandes.
     */
    @Column
    @NotBlank(message = "the name of the association is mandatory")
    private String name;

    /**
     *
     */
    @Column
    @OneToMany(mappedBy = "association")
    private Set<LeagueEntity> leagueSet;

    /**
     *
     */
    @Column(name = "club")
    @OneToMany(mappedBy = "association")
    private Set<ClubEntity> clubSet;

    /**
     *
     */
    @Column(name = "game")
    @OneToMany(mappedBy = "association")
    private Set<GameEntity> gameSet;
}
