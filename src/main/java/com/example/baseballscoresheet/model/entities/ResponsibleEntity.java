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

    // Getters and Setters
}
