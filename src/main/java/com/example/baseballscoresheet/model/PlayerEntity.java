package com.example.baseballscoresheet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Aktuelle Passnummer eines Spielers.
     * Jeder Spieler hat eine eigene eindeutige Passnummer.
     */
    @Column(name = "passnumber")
    //Unique
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
     *
     */
    @OneToMany(mappedBy = "player")
    private Set<TeamPlayerEntity> teamPlayers;

    private Integer pa;

    private Integer ab;

    private Integer r;

    private Integer rbi;

    private Integer h;

    private Integer twoB;

    private Integer threeB;

    private Integer hr;

    private Integer k;

    private Integer bb;

    private Integer hp;

    private Integer sb;

    private Integer cs;

    private Integer sh;

    private Integer sf;

    private Integer a;

    private Integer po;

    private Integer e;

    private Integer dp;

    private Integer ip;
}
