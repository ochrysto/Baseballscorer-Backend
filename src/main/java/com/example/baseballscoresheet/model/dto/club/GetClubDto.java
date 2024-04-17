package com.example.baseballscoresheet.model.dto.club;

import com.example.baseballscoresheet.model.dto.association.GetAssociationDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetClubDto {

    private String name;

    private String city;

    private String logo;

    private String email;

    private GetAssociationDto associationDto;
}
