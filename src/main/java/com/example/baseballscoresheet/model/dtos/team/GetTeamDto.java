package com.example.baseballscoresheet.model.dtos.team;

import com.example.baseballscoresheet.model.dtos.club.GetClubDto;
import com.example.baseballscoresheet.model.dtos.league.GetLeagueDto;
import com.example.baseballscoresheet.model.dtos.manager.GetManagerDto;
import com.example.baseballscoresheet.model.dtos.player.GetPlayerInfoDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class GetTeamDto {
    private String name;

    private GetClubDto getClubDto;

    private GetManagerDto getManagerDto;

    private GetLeagueDto getLeagueDto;

    private String teamLogo;

    private Set<GetPlayerInfoDto> players;
}
