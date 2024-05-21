package com.example.baseballscoresheet.model.dtos.game;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class AddGameDto {

    private Integer gameNr;

    private LocalDate date;

    private String location;

    private Integer innings;

    private Long associationId;

    private Long leagueId;

    private Long hostTeamId;

    private Long guestTeamId;

    private List<Long> umpireIdsList;

    private Long scorerId;
}
