package com.example.baseballscoresheet.mapping;

import com.example.baseballscoresheet.model.*;
import com.example.baseballscoresheet.model.dto.association.GetAssociationDto;
import com.example.baseballscoresheet.model.dto.club.GetClubDto;
import com.example.baseballscoresheet.model.dto.league.GetLeagueDto;
import com.example.baseballscoresheet.model.dto.manager.GetManagerDto;
import com.example.baseballscoresheet.model.dto.team.AddTeamInfoDto;
import com.example.baseballscoresheet.model.dto.team.GetTeamInfoDto;
import org.springframework.stereotype.Service;

@Service
public class MappingService {
    public TeamEntity mapAddTeamInfoDtoToTeamEntity(AddTeamInfoDto addTeamInfoDto, ManagerEntity managerEntity,
                                                    ClubEntity clubEntity, LeagueEntity leagueEntity) {
        var teamEntity = new TeamEntity();

        teamEntity.setName(addTeamInfoDto.getName());
        teamEntity.setManager(managerEntity);
        teamEntity.setClub(clubEntity);
        teamEntity.setLeague(leagueEntity);
        teamEntity.setTeamLogo(addTeamInfoDto.getTeamLogo());

        return teamEntity;
    }

    public GetTeamInfoDto mapTeamToGetTeamInfoDto(TeamEntity teamEntity) {
        GetTeamInfoDto getTeamInfoDto = new GetTeamInfoDto();

        getTeamInfoDto.setName(teamEntity.getName());
        getTeamInfoDto.setGetClubDto(mapClubEntityToGetClubDto(teamEntity.getClub()));
        getTeamInfoDto.setGetManagerDto(mapManagerEntityToGetManagerDto(teamEntity.getManager()));
        getTeamInfoDto.setGetLeagueDto(mapLeagueEntityToGetLeagueDto(teamEntity.getLeague()));
        getTeamInfoDto.setTeamLogo(teamEntity.getTeamLogo());

        return getTeamInfoDto;
    }

    public GetClubDto mapClubEntityToGetClubDto(ClubEntity clubEntity) {
        GetClubDto getClubDto = new GetClubDto();

        getClubDto.setName(clubEntity.getName());
        getClubDto.setCity(clubEntity.getCity());
        getClubDto.setEmail(clubEntity.getEmail());
        getClubDto.setLogo(clubEntity.getClubLogo());
        getClubDto.setAssociationDto(mapAssociationEntityToGetAssociationDto(clubEntity.getAssociation()));

        return getClubDto;
    }

    public GetManagerDto mapManagerEntityToGetManagerDto(ManagerEntity managerEntity) {
        GetManagerDto getManagerDto = new GetManagerDto();

        getManagerDto.setFirstName(managerEntity.getFirstName());
        getManagerDto.setLastName(managerEntity.getLastName());
        getManagerDto.setEmail(managerEntity.getEmail());

        return getManagerDto;
    }

    public GetLeagueDto mapLeagueEntityToGetLeagueDto(LeagueEntity leagueEntity) {
        GetLeagueDto getLeagueDto = new GetLeagueDto();

        getLeagueDto.setName(leagueEntity.getName());
        getLeagueDto.setAssociation(mapAssociationEntityToGetAssociationDto(leagueEntity.getAssociation()));

        return getLeagueDto;
    }

    public GetAssociationDto mapAssociationEntityToGetAssociationDto(AssociationEntity associationEntity) {
        GetAssociationDto associationDto = new GetAssociationDto();

        associationDto.setName(associationEntity.getName());

        return associationDto;
    }
}
