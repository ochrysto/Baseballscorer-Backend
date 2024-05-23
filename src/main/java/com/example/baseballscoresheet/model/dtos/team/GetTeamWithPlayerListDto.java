package com.example.baseballscoresheet.model.dtos.team;

import com.example.baseballscoresheet.model.dtos.club.GetClubDto;
import com.example.baseballscoresheet.model.dtos.league.GetLeagueDto;
import com.example.baseballscoresheet.model.dtos.manager.GetManagerDto;
import com.example.baseballscoresheet.model.dtos.player.GetPlayerDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetTeamWithPlayerListDto {

    private Long teamId;

    private String name;

    private GetClubDto club;

    private GetManagerDto manager;

    private GetLeagueDto league;

    private String teamLogo;

    private List<GetPlayerDto> playerList;
}
