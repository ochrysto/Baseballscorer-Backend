package com.example.baseballscoresheet.model.dtos.game;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class AddGameDto {

    @NotNull
    private Integer gameNr;

    private LocalDate date;

    @NotEmpty
    private String location;

    @NotNull
    @Min(value = 5)
    @Max(value = 35)
    private Integer innings;

    @NotNull
    private Long associationId;

    @NotNull
    private Long leagueId;

    @NotNull
    private Long hostTeamId;

    @NotNull
    private Long guestTeamId;

    private List<Long> umpireIdsList;

    @NotNull
    private Long scorerId;
}
