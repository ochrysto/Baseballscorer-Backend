package com.example.baseballscoresheet.model.dto.umpire;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetUmpireDto {

    private Long umpireId;

    private Long passnumber;

    @Setter(AccessLevel.NONE)
    private String name;

    public void setName(String firstName, String lastName) {
        this.name = firstName + " " + lastName;
    }
}
