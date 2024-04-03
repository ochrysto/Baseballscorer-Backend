package com.example.baseballscoresheet.enums;

import lombok.Getter;

@Getter
public enum POSITION {
    PITCHER(1), CATCHER(2),
    FIRST_BASEMAN(3), SECOND_BASEMAN(4), THIRD_BASEMAN(5),
    SHORTSTOP(6),
    LEFTFIELDER(7), CENTERFIELDER(8), RIGHTFIELDER(9);

    private int id;
    POSITION(int id) {
    }
}
