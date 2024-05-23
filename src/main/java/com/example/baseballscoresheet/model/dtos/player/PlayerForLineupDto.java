package com.example.baseballscoresheet.model.dtos.player;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerForLineupDto {

    @NotNull (message = "Player id is mandatory")
    private Long playerId;

    @Size(max = 99, message = "Jersey number must be between 0 and 99")
    private Integer jerseyNr;

    @NotNull (message = "Position id is mandatory")
    private Long positionId;
}
