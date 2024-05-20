package com.example.baseballscoresheet.model.dto.game;

import java.util.Date;
import java.util.List;

public class AddGameDto {

    private Long gameNumber;

    private Date date;

    private String location;

    private Integer innings;

    private Long associationId;

    private Long leagueId;

    private Long hostTeamId;

    private Long guestTeamId;

    private List<Long> umpireIdsList;

    private Long scorerId;
}
