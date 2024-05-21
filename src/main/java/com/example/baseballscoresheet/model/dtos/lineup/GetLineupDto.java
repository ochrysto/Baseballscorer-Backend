package com.example.baseballscoresheet.model.dtos.lineup;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class GetLineupDto {

    private Long teamId;

    List<GetPlayerFromLineupDto> playerList;

}
