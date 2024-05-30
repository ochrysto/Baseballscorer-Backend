package com.example.baseballscoresheet.model.dtos.gamestate;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ScoreBoardInningGetDto {
    int inningNumber;
    int guestScore;
    int homeScore;
}
