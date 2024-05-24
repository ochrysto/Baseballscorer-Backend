package com.example.baseballscoresheet.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class InningEntity {
    public enum Team {
        AWAY, HOME
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private GameEntity game;

    private int inning;
    private int outs;
    private String battingTeam;  // Use Team enum if desired

    @OneToMany(mappedBy = "frame", cascade = CascadeType.ALL)
    private List<TurnEntity> turns;
}
