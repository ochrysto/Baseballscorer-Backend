package com.example.baseballscoresheet.mapping;

import com.example.baseballscoresheet.model.*;
import com.example.baseballscoresheet.model.dto.association.GetAssociationDto;
import com.example.baseballscoresheet.model.dto.club.GetClubDto;
import com.example.baseballscoresheet.model.dto.league.GetLeagueDto;
import com.example.baseballscoresheet.model.dto.manager.GetManagerDto;
import com.example.baseballscoresheet.model.dto.player.AddPlayerInfoDto;
import com.example.baseballscoresheet.model.dto.player.GetPlayerInfoDto;
import com.example.baseballscoresheet.model.dto.player.GetPlayerInfoForLineUpDto;
import com.example.baseballscoresheet.model.dto.position.GetPositionDto;
import com.example.baseballscoresheet.model.dto.team.GetTeamDto;
import com.example.baseballscoresheet.model.dto.team.GetTeamInfoDto;
import com.example.baseballscoresheet.model.dto.team.AddTeamInfoDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
public class MappingService {
    private final ModelMapper mapper;

    public MappingService() {
        this.mapper = new ModelMapper();
    }

    // AddTeamInfoDto + ManagerEntity + ClubEntity + LeagueEntity -> TeamEntity
    public TeamEntity mapAddTeamInfoDtoToTeamEntity(AddTeamInfoDto addTeamInfoDto, ManagerEntity managerEntity,
                                                    ClubEntity clubEntity, LeagueEntity leagueEntity) {
        var teamEntity = new TeamEntity();
        teamEntity.setName(addTeamInfoDto.getName());
        teamEntity.setTeamLogo(addTeamInfoDto.getTeamLogo());
        teamEntity.setClub(clubEntity);
        teamEntity.setLeague(leagueEntity);
        teamEntity.setManager(managerEntity);
        return teamEntity;
    }

    // TeamEntity -> GetTeamInfoDto
    public GetTeamInfoDto mapTeamToGetTeamInfoDto(TeamEntity teamEntity) {
        GetTeamInfoDto teamInfoDto = new GetTeamInfoDto();
        teamInfoDto.setName(teamEntity.getName());
        teamInfoDto.setTeamLogo(teamEntity.getTeamLogo());
        teamInfoDto.setGetManagerDto(mapManagerEntityToGetManagerDto(teamEntity.getManager()));
        teamInfoDto.setGetLeagueDto(mapLeagueEntityToGetLeagueDto(teamEntity.getLeague()));
        teamInfoDto.setGetClubDto(mapClubEntityToGetClubDto(teamEntity.getClub()));
        return teamInfoDto;
    }

    // ClubEntity -> GetClubDto
    public GetClubDto mapClubEntityToGetClubDto(ClubEntity clubEntity) {
        GetClubDto getClubDto;
        getClubDto = this.mapper.map(clubEntity, GetClubDto.class);
        getClubDto.setAssociationDto(mapAssociationEntityToGetAssociationDto(clubEntity.getAssociation()));
        return getClubDto;
    }

    // ManagerEntity -> GetManagerDto
    public GetManagerDto mapManagerEntityToGetManagerDto(ManagerEntity managerEntity) {
        return this.mapper.map(managerEntity, GetManagerDto.class);
    }

    // ManagerEntity -> GetLeagueDto
    public GetLeagueDto mapLeagueEntityToGetLeagueDto(LeagueEntity leagueEntity) {
        GetLeagueDto getLeagueDto;
        getLeagueDto = this.mapper.map(leagueEntity, GetLeagueDto.class);
        getLeagueDto.setAssociation(mapAssociationEntityToGetAssociationDto(leagueEntity.getAssociation()));
        return getLeagueDto;
    }

    // AssociationEntity -> GetAssociationDto
    public GetAssociationDto mapAssociationEntityToGetAssociationDto(AssociationEntity associationEntity) {
        return this.mapper.map(associationEntity, GetAssociationDto.class);
    }

    // AddPlayerInfoDto -> PlayerEntity
    public PlayerEntity mapAddPlayerInfoDtoToPlayerEntity(AddPlayerInfoDto newPlayer) {
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setFirstName(newPlayer.getFirstName());
        playerEntity.setLastName(newPlayer.getLastName());
        playerEntity.setPassnumber(newPlayer.getPassnumber());
        return playerEntity;
    }

    // PlayerEntity -> GetPlayerInfoDto
    public GetPlayerInfoDto mapPlayerEntityToGetPlayerInfoDto(PlayerEntity playerEntity) {
        GetPlayerInfoDto getPlayerInfoDto = new GetPlayerInfoDto();
        getPlayerInfoDto.setId(playerEntity.getId());
        getPlayerInfoDto.setFirstName(playerEntity.getFirstName());
        getPlayerInfoDto.setLastName(playerEntity.getLastName());
        getPlayerInfoDto.setPassnumber(playerEntity.getPassnumber());
        return getPlayerInfoDto;
    }

    // TeamEntity -> GetTeamDto
    public GetTeamDto mapTeamEntityToGetTeamDto(TeamEntity teamEntity) {
        Set<GetPlayerInfoDto> players = new HashSet<>();
        GetTeamDto getTeamDto = new GetTeamDto();
        getTeamDto.setName(teamEntity.getName());
        getTeamDto.setGetClubDto(mapClubEntityToGetClubDto(teamEntity.getClub()));
        getTeamDto.setGetManagerDto(mapManagerEntityToGetManagerDto(teamEntity.getManager()));
        getTeamDto.setGetLeagueDto(mapLeagueEntityToGetLeagueDto(teamEntity.getLeague()));
        getTeamDto.setTeamLogo(teamEntity.getTeamLogo());
        getTeamDto.setPlayers(players);
        for (TeamPlayerEntity teamPlayerEntity : teamEntity.getTeamplayer()) {
            getTeamDto.getPlayers().add(mapTeamPlayerEntityToGetPlayerInfoDto(teamPlayerEntity));
        }
        return getTeamDto;
    }

    // TeamPlayerEntity -> GetPlayerInfoDto
    public GetPlayerInfoDto mapTeamPlayerEntityToGetPlayerInfoDto(TeamPlayerEntity teamPlayerEntity) {
        GetPlayerInfoDto playerInfoDto = new GetPlayerInfoDto();
        playerInfoDto.setId(teamPlayerEntity.getPlayer().getId());
        playerInfoDto.setFirstName(teamPlayerEntity.getPlayer().getFirstName());
        playerInfoDto.setLastName(teamPlayerEntity.getPlayer().getLastName());
        playerInfoDto.setPassnumber(teamPlayerEntity.getPlayer().getPassnumber());
        return playerInfoDto;
    }

    // PositionEntity -> GetPositionDto
    public GetPositionDto mapPositionEntityToGetPositionDto(PositionEntity positionEntity) {
        return this.mapper.map(positionEntity, GetPositionDto.class);
    }

    // PlayerEntity -> GetPlayerInfoForLineUpDto
    public GetPlayerInfoForLineUpDto mapPlayerEntityToGetPlayerInfoForLineUpDto(PlayerEntity player) {
        GetPlayerInfoForLineUpDto playerDto = new GetPlayerInfoForLineUpDto();
        playerDto.setId(player.getId());
        playerDto.setName(player.getFirstName() + " " + player.getLastName());
        playerDto.setPassnumber(player.getPassnumber());
        return playerDto;
    }
}