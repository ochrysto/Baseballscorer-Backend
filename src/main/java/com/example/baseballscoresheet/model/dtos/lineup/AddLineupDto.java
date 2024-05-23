package com.example.baseballscoresheet.model.dtos.lineup;

import com.example.baseballscoresheet.model.dtos.player.PlayerForLineupDto;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddLineupDto {

    @NotNull
    private Long teamId;

    private List<PlayerForLineupDto> playerList;

}
