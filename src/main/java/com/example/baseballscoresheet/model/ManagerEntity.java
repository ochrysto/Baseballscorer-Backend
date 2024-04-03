package com.example.baseballscoresheet.model;

import jakarta.persistence.*;
import lombok.*;

//TODO wozu gehört der Manager?

/**
 * Die Klasse {@link ManagerEntity} bildet ein Manager-Objekt mit seinen dazugehörigen Attributen ab.
 * Nachdem ein Spiel beendet wurde, erhält der Manager des Teams die Auswertung und Dokumentation des Spiels als PDF.
 */
@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "manager")
public class ManagerEntity {
    /**
     * Attribut, das zur eindeutigen Identifikation eines Manager-Objekts dient.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Vorname des Managers.
     */
    private String firstName;

    /**
     * Nachname des Managers.
     */
    private String lastName;

    /**
     * E-Mailadresse des Managers.
     */
    private String email;

    /**
     *
     */
    @OneToOne(mappedBy = "manager")
    private TeamEntity team;
}
