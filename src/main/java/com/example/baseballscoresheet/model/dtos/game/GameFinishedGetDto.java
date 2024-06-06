package com.example.baseballscoresheet.model.dtos.game;

import com.example.baseballscoresheet.model.dtos.association.AssociationGetDto;
import com.example.baseballscoresheet.model.dtos.league.LeagueGetDto;
import com.example.baseballscoresheet.model.dtos.scorer.ScorerGetDto;
import com.example.baseballscoresheet.model.dtos.team.TeamWithPlayersGetDto;
import com.example.baseballscoresheet.model.dtos.umpire.UmpireGetDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Setter
@Getter
public class GameFinishedGetDto {

    private Integer gameNr;

    private LocalDate date;

    private String location;

    private Integer innings;

    private AssociationGetDto association;

    private LeagueGetDto league;

    private TeamWithPlayersGetDto hostTeam;

    private TeamWithPlayersGetDto guestTeam;

    private List<UmpireGetDto> umpireList;

    private ScorerGetDto scorer;

    private Integer attendance;

    private LocalTime startTime;

    private LocalTime endTime;

    private Long durationInMinutes;
}
