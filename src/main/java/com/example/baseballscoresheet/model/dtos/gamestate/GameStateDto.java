package com.example.baseballscoresheet.model.dtos.gamestate;

import com.example.baseballscoresheet.model.dtos.player.GetPlayerDto;
import com.example.baseballscoresheet.model.dtos.player.GetPlayerFromLineupDto;
import com.example.baseballscoresheet.model.entities.InningEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GameStateDto {
    private Long game;
    private int awayRunsTotal;
    private int homeRunsTotal;
    private int awayErrors;
    private int homeErrors;
    private int awayHits;
    private int homeHits;
    private int awayLob;
    private int homeLob;
    private List<Integer> awayRuns;
    private List<Integer> homeRuns;
//    private List<ScoreBoardInningGetDto> scoreBoardInnings;
    private int inning;
    private InningEntity.Team team;
    private int outs;
    private int balls;
    private int strikes;
    private GetPlayerFromLineupDto onDeck;
    private GetPlayerFromLineupDto batter;
    private GetPlayerFromLineupDto firstBase;
    private GetPlayerFromLineupDto secondBase;
    private GetPlayerFromLineupDto thirdBase;
}
