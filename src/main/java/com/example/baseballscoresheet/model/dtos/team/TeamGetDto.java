package com.example.baseballscoresheet.model.dtos.team;

import com.example.baseballscoresheet.model.dtos.club.ClubGetDto;
import com.example.baseballscoresheet.model.dtos.league.LeagueGetDto;
import com.example.baseballscoresheet.model.dtos.manager.ManagerGetDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamGetDto {

    private Long teamId;

    private String name;

    private ClubGetDto club;

    private ManagerGetDto manager;

    private LeagueGetDto league;

    private String teamLogo;
}
