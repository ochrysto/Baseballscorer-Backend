package com.example.baseballscoresheet.model.dto.team;

import com.example.baseballscoresheet.model.dto.association.GetAssociationDto;
import com.example.baseballscoresheet.model.dto.club.GetClubDto;
import com.example.baseballscoresheet.model.dto.manager.GetManagerDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetTeamInfoDto {

    private GetAssociationDto association;

    private GetClubDto club;

    private String name;

    private String clubLogo;

    private GetManagerDto manager;

}
