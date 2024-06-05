package com.example.baseballscoresheet.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "responsible")
@Setter
@Getter
public class ResponsibleEntity {
    public enum Place {
        PITCHER, CATCHER, FIRST_BASE, SECOND_BASE, THIRD_BASE, SHORTSTOP,
        LEFT_FIELD, CENTER_FIELD, RIGHT_FIELD
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "action_id", nullable = false)
    private ActionEntity action;

    private int counter;
    @Enumerated(EnumType.STRING)
    private Place defencePosition;  // Use Place enum if desired

    public int getPosition() {
        return switch (defencePosition) {
            case PITCHER -> 1;
            case CATCHER -> 2;
            case FIRST_BASE -> 3;
            case SECOND_BASE -> 4;
            case THIRD_BASE -> 5;
            case SHORTSTOP -> 6;
            case LEFT_FIELD -> 7;
            case CENTER_FIELD -> 8;
            case RIGHT_FIELD -> 9;
        };
    }

    // Getters and Setters
}
