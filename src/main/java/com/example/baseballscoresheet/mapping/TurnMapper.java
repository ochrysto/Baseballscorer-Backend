package com.example.baseballscoresheet.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TurnMapper {
    TurnMapper INSTANCE = Mappers.getMapper(TurnMapper.class);
}
