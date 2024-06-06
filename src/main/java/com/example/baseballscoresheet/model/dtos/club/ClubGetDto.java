package com.example.baseballscoresheet.model.dtos.club;

import com.example.baseballscoresheet.model.dtos.association.AssociationGetDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClubGetDto {
    private Long id;

    private String name;

    private String city;

    private String logo;

    private String email;

    private AssociationGetDto association;
}
