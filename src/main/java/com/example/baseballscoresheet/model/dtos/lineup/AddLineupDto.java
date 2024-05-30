package com.example.baseballscoresheet.model.dtos.lineup;

import com.example.baseballscoresheet.model.dtos.player.PlayerForLineupDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddLineupDto {

    @NotNull(message = "Team id is mandatory")
    private Long teamId;

    @NotNull(message = "Team id is mandatory")
    @JsonProperty
    private boolean isHostTeam;

    @JsonProperty
    private boolean isGuestTeam;

    private List<PlayerForLineupDto> playerList;

}
