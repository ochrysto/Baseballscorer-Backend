package com.example.baseballscoresheet.model;

import com.example.baseballscoresheet.enums.Type;
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

    /**
     *
     */
    private Type type;

}
