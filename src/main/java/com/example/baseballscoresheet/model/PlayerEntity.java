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
     * Aktuelle Passnummer eines Spielers.
     * Dient der Identifikation des Spielers im Spielbetrieb.
     * Jeder Spieler hat eine eigene eindeutige Passnummer.
     */
    @Id
    @Column(name = "passnumber")
    private Long passnumber;

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
     * Aktuelle Trikotnummer eines Spielers.
     * Die Trikotnummer kann sich ändern und ist einem Spieler NICHT fest zugeordnet.
     */
    @Column(name = "tricot_number")
    @Size(max = 99, min = 0, message = "Tricot Number must be between 0 and 99")
    private Integer jerseyNr;

    @OneToMany(mappedBy = "player")
    private Set<TeamPlayerEntity> teamPlayers;

    /**
     *
     */
    @ManyToOne
    @JoinColumn(name="game_history_id", nullable = false)
    private GameHistoryEntity gameHistory;
}
