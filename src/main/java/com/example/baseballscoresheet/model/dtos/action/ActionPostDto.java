package com.example.baseballscoresheet.model.dtos.action;

import com.example.baseballscoresheet.model.dtos.responsible.ResponsibleDto;
import com.example.baseballscoresheet.model.entities.ActionEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ActionPostDto {
    private int base;
    private ActionEntity.Type type;
    private Integer distance;
    private List<ResponsibleDto> responsible;
}
