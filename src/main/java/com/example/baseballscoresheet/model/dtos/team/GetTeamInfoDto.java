package com.example.baseballscoresheet.model.dtos.team;

import com.example.baseballscoresheet.model.dtos.club.GetClubDto;
import com.example.baseballscoresheet.model.dtos.league.GetLeagueDto;
import com.example.baseballscoresheet.model.dtos.manager.GetManagerDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetTeamInfoDto {

    private Long teamId;

    private String name;

    private GetClubDto getClubDto;

    private GetManagerDto getManagerDto;

    private GetLeagueDto getLeagueDto;

    private String teamLogo;
}
