package com.example.baseballscoresheet.model;

import jakarta.persistence.*;
import lombok.*;

//TODO bisschen mehr Infos, was eine League ist?

/**
 * Die Klasse {@link LeagueEntity} bildet ein League-Objekt mit seinen dazugehörigen Attributen ab.
 *
 */
@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "league")
public class LeagueEntity {
    /**
     * Attribut, das zur eindeutigen Identifikation eines League-Objekts dient.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     *
     */
    private String name;
}
