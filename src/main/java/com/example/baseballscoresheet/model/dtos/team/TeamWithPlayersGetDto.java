package com.example.baseballscoresheet.model.dtos.team;

import com.example.baseballscoresheet.model.dtos.club.ClubGetDto;
import com.example.baseballscoresheet.model.dtos.league.LeagueGetDto;
import com.example.baseballscoresheet.model.dtos.manager.ManagerGetDto;
import com.example.baseballscoresheet.model.dtos.player.PlayerGetDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TeamWithPlayersGetDto {

    private Long teamId;

    private String name;

    private ClubGetDto club;

    private ManagerGetDto manager;

    private LeagueGetDto league;

    private String teamLogo;

    private List<PlayerGetDto> playerList;
}
