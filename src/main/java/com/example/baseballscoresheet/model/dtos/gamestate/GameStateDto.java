package com.example.baseballscoresheet.model.dtos.gamestate;

import com.example.baseballscoresheet.model.dtos.player.GetPlayerDto;
import com.example.baseballscoresheet.model.entities.InningEntity;

import java.util.List;

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
    private int inning;
    private InningEntity.Team team;
    private int outs;
    private int balls;
    private int strikes;
    private GetPlayerDto onDeck;
    private GetPlayerDto batter;
    private GetPlayerDto firstBase;
    private GetPlayerDto secondBase;
    private GetPlayerDto thirdBase;
}
