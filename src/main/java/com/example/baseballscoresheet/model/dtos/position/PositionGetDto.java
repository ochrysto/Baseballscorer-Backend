package com.example.baseballscoresheet.model.dtos.position;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PositionGetDto {

    private Long id;

    private Integer position;
    private String description;
}
