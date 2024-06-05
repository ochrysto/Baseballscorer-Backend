package com.example.baseballscoresheet.model.dtos.club;

import com.example.baseballscoresheet.model.dtos.association.GetAssociationDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetClubDto {
    private Long id;

    private String name;

    private String city;

    private String logo;

    private String email;

    private GetAssociationDto association;
}
