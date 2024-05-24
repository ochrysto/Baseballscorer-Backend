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
public class TurnEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    private PlayerEntity player;
    @ManyToOne
    @JoinColumn(name = "inning_id", nullable = false)
    private InningEntity inning;
    private int base = 0;  // Use Base enum if desired
    private String currentStatus = Status.AT_BAT.name();
    private int strikes = 0;
    private int balls = 0;
    @OneToMany(mappedBy = "turn", cascade = CascadeType.ALL)
    private List<ActionEntity> actions;

    public TurnEntity(PlayerEntity player, InningEntity inning, int base, String currentStatus) {
        this.player = player;
        this.inning = inning;
        this.base = base;
        this.currentStatus = currentStatus;
    }

    public enum Base {
        BATTER(0), FIRST_BASE(1), SECOND_BASE(2), THIRD_BASE(3), HOME_BASE(4);

        private final int value;

        Base(int value) {
            this.value = value;
        }

        public static int getPrevious(int base) {
            if (base == 0) {
                throw new IllegalArgumentException("There is no previous base for batter (at bat)");
            }
            return base - 1;
        }

        public static int getNext(int base) {
            if (base == 4) {
                throw new IllegalArgumentException("There is no next base for home base");
            }
            return base + 1;
        }

        public int getValue() {
            return value;
        }
    }

    public enum Status {
        AT_BAT, ON_BASE, IS_OUT, RUN, BATTING_OUT_OF_ORDER
    }

    // Getters and Setters
}
