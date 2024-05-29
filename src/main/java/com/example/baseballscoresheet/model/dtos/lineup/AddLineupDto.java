package com.example.baseballscoresheet.model.dtos.lineup;

import com.example.baseballscoresheet.model.dtos.player.PlayerForLineupDto;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddLineupDto {

    @NotNull (message = "Team id is mandatory")
    private Long teamId;

    @NotNull (message = "Team id is mandatory")
    private Long gameId;

    private List<PlayerForLineupDto> playerList;

}
