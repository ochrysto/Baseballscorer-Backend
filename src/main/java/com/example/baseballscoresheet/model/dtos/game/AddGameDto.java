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

    @NotNull(message = "Number for game name is mandatory")
    private Integer gameNr;

    private LocalDate date;

    @NotEmpty
    private String location;

    @NotNull(message = "Number of innings is mandatory")
    @Min(value = 5)
    @Max(value = 35)
    private Integer innings;

    @NotNull(message = "Association id is mandatory")
    private Long associationId;

    @NotNull(message = "League id is mandatory")
    private Long leagueId;

    @NotNull(message = "Host team id is mandatory")
    private Long hostTeamId;

    @NotNull(message = "Guest team id is mandatory")
    private Long guestTeamId;

    private List<Long> umpireIdsList;

    @NotNull(message = "Scorer id is mandatory")
    private Long scorerId;
}
