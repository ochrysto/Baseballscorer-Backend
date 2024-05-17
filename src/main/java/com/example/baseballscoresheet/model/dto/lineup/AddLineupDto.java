package com.example.baseballscoresheet.model.dto.lineup;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddLineupDto {

    private Long teamId;

    private Integer jerseyNr;

    private Long playerId;

    private Long position;

}
