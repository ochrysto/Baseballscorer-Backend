package com.example.baseballscoresheet.model.dtos.team;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddTeamWithoutPlayerListDto {

    @NotBlank(message = "Name is obligatory")
    @Size(max = 70, message = "Name must not exceed 70 characters")
    private String name;

    @NotNull
    private Long managerId;

    @NotNull
    private Long clubId;

    @NotNull
    private Long leagueId;

    private String teamLogo;

}
