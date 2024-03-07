package com.example.baseballscoresheet.model;


import jakarta.persistence.*;
import lombok.*;

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

    //TODO was ist der Club eines Teams?
    private ClubEntity club;

    /**
     * Stadt, aus dem das Team kommt.
     */
    private String city;

    /**
     * E-Mailadresse des Teams.
     */
    private String email;

    /**
     * Logo des Teams.
     */
    private String logo;
}
