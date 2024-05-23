package com.example.baseballscoresheet.model.dtos.player;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerForLineupDto {

    @NotNull
    private Long playerId;

    @Size(max = 99, message = "Jersey number must be between 0 and 99")
    private Integer jerseyNr;

    @NotNull
    private Long position;
}
