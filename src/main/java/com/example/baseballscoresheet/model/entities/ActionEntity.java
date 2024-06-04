package com.example.baseballscoresheet.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "action")
@Getter
@Setter
@NoArgsConstructor
public class ActionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "turn_id", nullable = false)
    private TurnEntity turn;
    @Enumerated(EnumType.STRING)
    private Type type;
    @Enumerated(EnumType.STRING)
    private Place place;
    private int distance = 0;
    @ManyToOne
    @JoinColumn(name = "linked_action_id")
    private ActionEntity linkedAction;
    private boolean isStandalone = true;
    private boolean proceed = false;
    @OneToMany(mappedBy = "action", cascade = CascadeType.ALL)
    private List<ResponsibleEntity> sequence;

    public ActionEntity(TurnEntity turn, Type type) {
        this.turn = turn;
        this.type = type;
    }

    public ActionEntity(TurnEntity turn, Type type, Place place, int distance, ActionEntity linkedAction, boolean isStandalone) {
        this.turn = turn;
        this.type = type;
        this.place = place;
        this.distance = distance;
        this.linkedAction = linkedAction;
        this.isStandalone = isStandalone;
    }

    static public boolean isResponsibleRequired(ActionEntity.Type actionType) {
        List<ActionEntity.Type> actions = List.of(
                Type.FLYOUT, Type.GROUND_OUT, Type.OUT_BY_RULE, Type.APPEAL_PLAY, Type.PICKED_OFF, Type.CAUGHT_BASE, Type.ASSISTED_OUT, Type.UNASSISTED_OUT,  // out
                Type.ADVANCED_BY_BATTER,  // safe
                Type.ERROR, Type.ASSISTED_ERROR  // error
                );
        return actions.contains(actionType);
    }

    static public boolean areMultipleResponsibleRequired(ActionEntity.Type actionType) {
        List<ActionEntity.Type> actions = List.of(
                Type.ASSISTED_OUT,  // out
                // safe
                Type.ASSISTED_ERROR  // error
        );
        return actions.contains(actionType);
    }

    static public List<ActionEntity.Type> atBat() {
        return List.of(
                Type.BALL, Type.STRIKE, Type.FOUL
        );
    }

    static public List<ActionEntity.Type> out() {
        return List.of(
                Type.FLYOUT, Type.GROUND_OUT, Type.OUT_BY_RULE, Type.APPEAL_PLAY, Type.PICKED_OFF, Type.CAUGHT_BASE,
                Type.ASSISTED_OUT, Type.UNASSISTED_OUT, Type.STRIKEOUT, Type.SACRIFICE_HIT, Type.SACRIFICE_FLY
        );
    }

    static public List<ActionEntity.Type> safe() {
        return List.of(
                Type.BASE_ON_BALLS, Type.HIT_SINGLE, Type.HIT_DOUBLE, Type.HIT_TRIPLE, Type.HOME_RUN,
                Type.STOLEN_BASE, Type.WILD_PITCH, Type.PASSED_BALL, Type.ADVANCED_BY_RULE, Type.ADVANCED_BY_BATTER,
                Type.HOLD
        );
    }

    static public List<ActionEntity.Type> error() {
        return List.of(
                Type.ERROR, Type.ASSISTED_ERROR
        );
    }

    static public List<ActionEntity.Type> outBatter() {
        return List.of(
                Type.FLYOUT, Type.GROUND_OUT, Type.OUT_BY_RULE, Type.APPEAL_PLAY, Type.PICKED_OFF, Type.CAUGHT_BASE,
                Type.ASSISTED_OUT, Type.UNASSISTED_OUT, Type.STRIKEOUT, Type.SACRIFICE_HIT, Type.SACRIFICE_FLY
        );
    }

    static public List<ActionEntity.Type> safeBatter() {
        return List.of(
                Type.BALL, Type.STRIKE, Type.FOUL, Type.BASE_ON_BALLS, Type.HIT_SINGLE, Type.HIT_DOUBLE, Type.HIT_TRIPLE, Type.HOME_RUN
        );
    }

    static public List<ActionEntity.Type> errorBatter() {
        return List.of(Type.ERROR, Type.ASSISTED_ERROR);
    }

    static public List<ActionEntity.Type> outRunnerHold() {
        return List.of(Type.GROUND_OUT, Type.OUT_BY_RULE, Type.APPEAL_PLAY, Type.PICKED_OFF, Type.CAUGHT_BASE, Type.ASSISTED_OUT, Type.UNASSISTED_OUT);
    }

    static public List<ActionEntity.Type> safeRunnerHold() {
        return List.of(Type.HOLD);
    }

    static public List<ActionEntity.Type> errorRunnerHold() {
        return List.of(Type.ERROR, Type.ASSISTED_ERROR);
    }

    static public List<ActionEntity.Type> outRunnerAdvance() {
        return List.of(Type.GROUND_OUT, Type.OUT_BY_RULE, Type.APPEAL_PLAY, Type.PICKED_OFF, Type.CAUGHT_BASE, Type.ASSISTED_OUT, Type.UNASSISTED_OUT);
    }

    static public List<ActionEntity.Type> safeRunnerAdvance() {
        return List.of(Type.STOLEN_BASE, Type.WILD_PITCH, Type.PASSED_BALL, Type.ADVANCED_BY_RULE, Type.ADVANCED_BY_BATTER);
    }

    static public List<ActionEntity.Type> errorRunnerAdvance() {
        return List.of(Type.ERROR, Type.ASSISTED_ERROR);
    }

    public enum Type {
        BALL, STRIKE, FOUL, BASE_ON_BALLS, HIT_SINGLE, HIT_DOUBLE, HIT_TRIPLE, HOME_RUN,
        STOLEN_BASE, WILD_PITCH, PASSED_BALL, ADVANCED_BY_RULE, ADVANCED_BY_BATTER,
        HOLD, FLYOUT, GROUND_OUT, OUT_BY_RULE, APPEAL_PLAY, PICKED_OFF, CAUGHT_BASE,
        ASSISTED_OUT, UNASSISTED_OUT, STRIKEOUT, SACRIFICE_HIT, SACRIFICE_FLY, ERROR,
        ADVANCED_ERROR, ASSISTED_ERROR, ASSISTED_ADVANCED_ERROR
    }

    public enum Place {
        BATTER_TO_FIRST, FIRST_TO_SECOND, SECOND_TO_THIRD, THIRD_TO_HOME;

        public static Place forBase(int base) {
            switch (base) {
                case 0:
                    return BATTER_TO_FIRST;
                case 1:
                    return FIRST_TO_SECOND;
                case 2:
                    return SECOND_TO_THIRD;
                case 3:
                    return THIRD_TO_HOME;
                default:
                    throw new IllegalArgumentException("Invalid base");
            }
        }
    }

    // Getters and Setters
}
