package com.example.baseballscoresheet.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * Die Klasse {@link PlayerEntity} bildet ein Spieler-Objekt mit seinen dazugehörigen Attributen ab.
 * Er ist Teilnehmer eines Spiels und Mitglied in einem Team.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "player")
public class PlayerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "passnumber", unique = true)
    private Integer passnumber;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "player")
    private Set<TeamPlayerEntity> teamPlayers;

    /**
     * PA = Plate Appearance
     * gibt an, wie oft ein Schlagmann zum Schlagen dran war
     */
    private Integer pa;

    /**
     * AB = At Bat
     * Schlagdurchgang
     */
    private Integer ab;

    /**
     * R = Runs
     * gibt an, wie viele Punkte gegen DIESEN Pitcher erzielt worden
     */
    private Integer r;

    /**
     * RBI = Run Batted In
     * Run, der durch die Aktion eines Schlagmanns erzielt wird
     */
    private Integer rbi;

    /**
     * H = (Base) Hit
     * Schlag, der so gut ist, dass die Defensivmannschaft den Schlagmann nicht
     * am ersten Base aus machen kann
     */
    private Integer h;

    private Integer twoB;

    private Integer threeB;

    /**
     * HR = Homerun
     * Schlag, durch den der Schlagmann Home Plate erreicht
     */
    private Integer hr;

    /**
     * K = Strike Out
     * Schlagmann ist aus, weil er drei Strikes bekommen hat
     */
    private Integer k;

    /**
     * BB = Base on Balls, Walk
     * Berechtigung für einen Schlagmann zum ersten Base vorzurücken, nachdem er vier Pitches außerhalb der Strike Zone bekommen hat
     */
    private Integer bb;

    /**
     * HP = Hit by Pitch, wenn der Schlagmann von einem gepitchten Ball getroffen wird
     * und deshalb zum ersten Base vorrücken darf
     */
    private Integer hp;

    /**
     * SB = Stolen Base
     * Vorrücken eines Läufers ohne geschlagenen Ball, WP, PB, BB, HP, CI, E, e, FC, ABR
     */
    private Integer sb;

    /**
     * CS = Caught Steal
     * Läufer, wird beim Versuch, eine Base zu stehlen aus gemacht
     */
    private Integer cs;

    /**
     * SH = Sacrifice Hit (= Bunt)
     * Schlag, bei dem der Schlagmann sich opfert, um einem Läufer das Vorrücken zu ermöglichen
     */
    private Integer sh;

    /**
     * SF = Sacrifice Fly
     * Fly Ball, der gefangen wird und dabei einem Läufer erlaubt, zum Home Plate vorzurücken
     */
    private Integer sf;

    /**
     * A = Assist
     * Beihilfe zu einem Aus
     */
    private Integer a;

    /**
     * PO = Putout
     * Aus eines Läufers oder Schlagmanns durch einen Feldspieler
     */
    private Integer po;

    /**
     * E = Error (Extra-Base o der Decisive)
     * Fehler eines Defensivspielers, der einem Läufer ein zusätzliches Base beschert ODER
     * Fehler eines Defensivspielers, der ein Aus verhindert
     */
    private Integer e;

    /**
     * DP = Double Play oder Designated Player
     * Double Play = Spielzug, in dessen Verlauf zwei Spieler der Offensivmannschaft aus gemacht werden
     * Designated Player?
     */
    private Integer dp;

    /**
     * IP = Innings Played/Pitched
     * Played = Anzahl der Innings, in denen ein Spieler in der Defensive gespielt hat
     * Pitched = Anzahl der Innings, in denen ein Pitcher geworfen hat
     */
    private Integer ip;
}
