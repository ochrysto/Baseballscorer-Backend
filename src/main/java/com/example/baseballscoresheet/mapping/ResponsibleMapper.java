package com.example.baseballscoresheet.mapping;

import com.example.baseballscoresheet.model.dtos.responsible.ResponsibleDto;
import com.example.baseballscoresheet.model.entities.ResponsibleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ResponsibleMapper {
    ResponsibleMapper INSTANCE = Mappers.getMapper(ResponsibleMapper.class);

//    List<ResponsibleEntity> responsibleDtoToResponsibleEntities(List<ResponsibleDto> responsibleDtoList);
}
