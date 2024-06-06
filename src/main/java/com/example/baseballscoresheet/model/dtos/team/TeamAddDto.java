package com.example.baseballscoresheet.model.dtos.team;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamAddDto {

    @NotBlank(message = "Name is mandatory")
    @Size(max = 70, message = "Name must not exceed 70 characters")
    private String name;

    @NotNull (message = "Manager id is mandatory")
    private Long managerId;

    @NotNull (message = "Club id is mandatory")
    private Long clubId;

    @NotNull (message = "League id is mandatory")
    private Long leagueId;

    private String teamLogo;

}
