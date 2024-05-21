package com.example.baseballscoresheet.model.dtos.game;

import com.example.baseballscoresheet.model.dtos.association.GetAssociationDto;
import com.example.baseballscoresheet.model.dtos.league.GetLeagueDto;
import com.example.baseballscoresheet.model.dtos.scorer.GetScorerDto;
import com.example.baseballscoresheet.model.dtos.team.GetTeamDto;
import com.example.baseballscoresheet.model.dtos.umpire.GetUmpireDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class GetGameDto {

    private Integer gameNr;

    private LocalDate date;

    private String location;

    private Integer innings;

    private GetAssociationDto association;

    private GetLeagueDto league;

    private GetTeamDto hostTeam;

    private GetTeamDto guestTeam;

    private List<GetUmpireDto> umpireList;

    private GetScorerDto scorer;
}
