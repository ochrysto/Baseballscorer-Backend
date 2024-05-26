package com.example.baseballscoresheet.mapping;

import com.example.baseballscoresheet.model.dtos.player.GetPlayerDto;
import com.example.baseballscoresheet.model.entities.PlayerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PlayerMapper {
    PlayerMapper INSTANCE = Mappers.getMapper(PlayerMapper.class);

    GetPlayerDto playerEntityToGetPlayerDto(PlayerEntity playerEntity);
}
