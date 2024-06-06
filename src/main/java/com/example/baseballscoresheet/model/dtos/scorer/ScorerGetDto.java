package com.example.baseballscoresheet.model.dtos.scorer;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ScorerGetDto {

    private Long scorerId;

    private Long passnumber;

    @Setter(AccessLevel.NONE)
    private String name;

    public void setName(String firstName, String lastName) {
        this.name = firstName + " " + lastName;
    }
}