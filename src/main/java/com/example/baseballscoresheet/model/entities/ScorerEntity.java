package com.example.baseballscoresheet.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Die Klasse {@link ScorerEntity} bildet ein Scorer-Objekt mit seinen dazugehörigen Attributen ab.
 * Der Scorer erfasst aller relevanten Aktionen eines Spiels.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "scorer")
public class ScorerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long passnumber;

    private String firstName;

    private String lastName;

    @OneToMany(mappedBy = "scorer")
    private List<GameEntity> games;
}
