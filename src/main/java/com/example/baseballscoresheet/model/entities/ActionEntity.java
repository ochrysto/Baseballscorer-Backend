package com.example.baseballscoresheet.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ActionEntity {
    public ActionEntity(TurnEntity turn, String type) {
        this.turn = turn;
        this.type = type;
    }

    public ActionEntity(TurnEntity turn, String type, String place, int distance, ActionEntity linkedAction, boolean isStandalone) {
        this.turn = turn;
        this.type = type;
        this.place = place;
        this.distance = distance;
        this.linkedAction = linkedAction;
        this.isStandalone = isStandalone;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "turn_id", nullable = false)
    private TurnEntity turn;

    private String type;
    private String place;
    private int distance = 0;

    @ManyToOne
    @JoinColumn(name = "linked_action_id")
    private ActionEntity linkedAction;

    private boolean isStandalone = true;
    private boolean proceed = false;

    @OneToMany(mappedBy = "action", cascade = CascadeType.ALL)
    private List<ResponsibleEntity> sequence;

    static public boolean isResponsibleRequired(ActionEntity.Type actionType) {
        return true;
    }

    static public boolean areMultipleResponsibleRequired(ActionEntity.Type actionType) {
        return true;
    }

    static public List<ActionEntity.Type> atBat() {
        return List.of();
    }

    static public List<ActionEntity.Type> out(){
        return List.of();
    }

    static public List<ActionEntity.Type> safe(){
        return List.of();
    }

    static public List<ActionEntity.Type> error(){
        return List.of();
    }

    static public List<ActionEntity.Type> safeBatter(){
        return List.of();
    }

    static public List<ActionEntity.Type> errorBatter(){
        return List.of();
    }

    static public List<ActionEntity.Type> outRunnerHold(){
        return List.of();
    }

    static public List<ActionEntity.Type> safeRunnerHold(){
        return List.of();
    }

    static public List<ActionEntity.Type> errorRunnerHold(){
        return List.of();
    }

    static public List<ActionEntity.Type> outRunnerAdvance(){
        return List.of();
    }

    static public List<ActionEntity.Type> safeRunnerAdvance(){
        return List.of();
    }

    static public List<ActionEntity.Type> errorRunnerAdvance(){
        return List.of();
    }

    // Getters and Setters
}
