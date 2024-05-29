package com.example.baseballscoresheet.model.dtos.button;

import com.example.baseballscoresheet.model.entities.ActionEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ButtonDto {
    private String button;
    private ActionEntity.Type actionType;
    private boolean responsibleRequired;
    private boolean multipleResponsibleRequired;
}
