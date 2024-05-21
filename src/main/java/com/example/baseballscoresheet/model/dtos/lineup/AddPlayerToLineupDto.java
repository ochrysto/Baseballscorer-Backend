package com.example.baseballscoresheet.model.dtos.lineup;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddPlayerToLineupDto {

    private Integer jerseyNr;

    private Long playerId;

    private Long position;
}
