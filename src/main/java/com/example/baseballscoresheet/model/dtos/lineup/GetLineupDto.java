package com.example.baseballscoresheet.model.dtos.lineup;

import com.example.baseballscoresheet.model.dtos.player.GetPlayerFromLineupDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class GetLineupDto {

    private Long teamId;

    List<GetPlayerFromLineupDto> playerList;

}
