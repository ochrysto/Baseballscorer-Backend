package com.example.baseballscoresheet.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * Die Klasse {@link UmpireEntity} bildet ein Game-Objekt mit seinen dazugehörigen Attributen ab.
 * Ein Umpire ist der Schiedsrichter des Spiels. Es kann mehr als einen Umpire pro Spiel geben.
 */
@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "umpire")
public class UmpireEntity {

    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     *
     */
    private Long passnumber;

    /**
     * Vorname des Schiedsrichters.
     */
    private String firstName;

    /**
     * Nachname des Schiedsrichters.
     */
    private String lastName;

    /**
     *
     */
    @OneToMany(mappedBy = "umpire")
    private Set<GameUmpireEntity> gameUmpire;
}
