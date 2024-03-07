package com.example.baseballscoresheet.model;

import jakarta.persistence.*;
import lombok.*;

//TODO wozu gehört der Manager?
/**
 * Die Klasse {@link ManagerEntity} bildet ein Manager-Objekt mit seinen dazugehörigen Attributen ab.
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
}
