package com.example.baseballscoresheet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

//TODO bisschen mehr Infos, was ein Club ist / Unterscheidung von einem Team?
/**
 * Die Klasse {@link ClubEntity} bildet ein Club-Objekt mit seinen dazugehörigen Attributen ab.
 */
@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "club")
public class ClubEntity {
    /**
     * Attribut, das zur eindeutigen Identifikation eines Club-Objekts dient.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name des Clubs.
     */
    @NotBlank(message = "Name of the club is mandatory")
    private String name;

    //TODO 1:n- oder n:m-Beziehung?
    private AssociationEntity association;
}
