package com.example.baseballscoresheet.model.dtos.gamestate;

import com.example.baseballscoresheet.model.dtos.player.LineupPlayerGetDto;
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
    private LineupPlayerGetDto onDeck;
    private LineupPlayerGetDto batter;
    private LineupPlayerGetDto firstBase;
    private LineupPlayerGetDto secondBase;
    private LineupPlayerGetDto thirdBase;
}
