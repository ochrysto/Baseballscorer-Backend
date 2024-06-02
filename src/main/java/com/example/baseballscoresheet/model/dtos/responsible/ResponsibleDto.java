package com.example.baseballscoresheet.model.dtos.responsible;

import com.example.baseballscoresheet.model.entities.ResponsibleEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponsibleDto {
    private ResponsibleEntity.Place defencePosition;
}
