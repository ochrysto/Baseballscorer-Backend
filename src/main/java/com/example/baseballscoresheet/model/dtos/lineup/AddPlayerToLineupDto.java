package com.example.baseballscoresheet.model.dtos.lineup;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddPlayerToLineupDto {

    @Size(max = 99, message = "Jersey number must be between 0 and 99")
    private Integer jerseyNr;

    private Long playerId;

    private Long position;
}
