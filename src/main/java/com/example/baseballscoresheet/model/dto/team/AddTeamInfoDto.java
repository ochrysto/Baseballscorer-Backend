package com.example.baseballscoresheet.model.dto.team;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddTeamInfoDto {

    @NotBlank(message = "Name is obligatory")
    @Size(max = 70, message = "Name must not exceed 70 characters")
    private String name;

    @NotBlank(message = "Manager is obligatory")
    private Long managerId;

    @NotBlank(message = "Club is obligatory")
    private Long clubId;

    @NotBlank(message = "League is obligatory")
    private Long leagueId;

    private String teamLogo;

}
