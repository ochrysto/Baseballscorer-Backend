package com.example.baseballscoresheet.model.dto.team;

import com.example.baseballscoresheet.model.AssociationEntity;
import com.example.baseballscoresheet.model.dto.association.GetAssociationDto;
import com.example.baseballscoresheet.model.dto.club.GetClubDto;
import com.example.baseballscoresheet.model.dto.manager.GetManagerDto;
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
    private GetManagerDto manager;

    @NotBlank(message = "Club is obligatory")
    private GetClubDto club;

    @NotBlank(message = "Association is obligatory")
    private GetAssociationDto association;

    private String teamLogo;

}
