package com.example.baseballscoresheet.model.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Die Klasse {@link ManagerEntity} bildet ein Manager-Objekt mit seinen dazugehörigen Attributen ab.
 * Nachdem ein Spiel beendet wurde, erhält der Manager des Teams die Auswertung und Dokumentation des Spiels als PDF.
 * Er ist der Trainer des Teams.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "manager")
public class ManagerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    @OneToOne(mappedBy = "manager")
    private TeamEntity team;
}
