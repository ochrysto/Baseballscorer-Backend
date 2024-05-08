package com.example.baseballscoresheet.model.dto.team;

import com.example.baseballscoresheet.model.PlayerEntity;
import com.example.baseballscoresheet.model.TeamPlayerEntity;
import com.example.baseballscoresheet.model.dto.club.GetClubDto;
import com.example.baseballscoresheet.model.dto.league.GetLeagueDto;
import com.example.baseballscoresheet.model.dto.manager.GetManagerDto;
import com.example.baseballscoresheet.model.dto.player.GetPlayerInfoDto;
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
