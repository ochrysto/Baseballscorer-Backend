package com.example.baseballscoresheet.model.dtos.lineup;

import com.example.baseballscoresheet.model.dtos.player.LineupPlayerGetDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class LineupGetDto {

    private Long teamId;

    List<LineupPlayerGetDto> playerList;

}
