package com.example.baseballscoresheet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

/**
 * Die Klasse {@link PlayerEntity} bildet ein Spieler-Objekt mit seinen dazugehörigen Attributen ab.
 * Er ist Teilnehmer eines Spiels und Mitglied in einem Team.
 */
@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "player")
public class PlayerEntity {
    /**
     * Attribut, das zur eindeutigen Identifikation eines Player-Objekts dient.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Vorname eines Spielers.
     */
    @Column(name = "first_name")
    @NotBlank(message = "Players first name is mandatory")
    private String firstName;

    /**
     * Nachname eines Spielers.
     */
    @Column(name = "last_name")
    @NotBlank(message = "Players last name is mandatory")
    private String lastName;

    /**
     * Aktuelle Passnummer eines Spielers.
     * Dient der Identifikation des Spielers im Spielbetrieb.
     * Jeder Spieler hat eine eigene eindeutige Passnummer.
     */
    @Column(name = "pass_number")
    @NotBlank(message = "Players Pass Number is mandatory")
    private String passNumber;

    //TODO welche ist die maximal höchste Trikot Nummer?
    /**
     * Aktuelle Trikotnummer eines Spielers.
     * Die Trikotnummer kann sich ändern und ist nicht fest einem Spieler zugeordnet.
     */
    @Column(name = "tricot_number")
    @Size(max = 99, message = "Tricot Number must not be greater than 99")
    private Integer tricotNumber;

    @OneToMany(mappedBy = "player")
    private Set<TeamPlayerEntity> teamPlayers;
}
