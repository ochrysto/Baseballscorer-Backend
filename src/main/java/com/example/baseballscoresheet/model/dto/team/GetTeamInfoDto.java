package com.example.baseballscoresheet.model.dto.team;

import com.example.baseballscoresheet.model.dto.club.GetClubDto;
import com.example.baseballscoresheet.model.dto.league.GetLeagueDto;
import com.example.baseballscoresheet.model.dto.manager.GetManagerDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetTeamInfoDto {

    private String name;

    private GetClubDto getClubDto;

    private GetManagerDto getManagerDto;

    private GetLeagueDto getLeagueDto;

    private String teamLogo;
}
