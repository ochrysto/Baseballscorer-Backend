package com.example.baseballscoresheet.mapping;

import com.example.baseballscoresheet.model.*;
import com.example.baseballscoresheet.model.dto.club.GetClubDto;
import com.example.baseballscoresheet.model.dto.league.GetLeagueDto;
import com.example.baseballscoresheet.model.dto.manager.GetManagerDto;
import com.example.baseballscoresheet.model.dto.team.AddTeamInfoDto;
import com.example.baseballscoresheet.model.dto.team.GetTeamInfoDto;

public class MappingService {
    public TeamEntity mapAddTeamDtoToTeamEntity(AddTeamInfoDto addTeamInfoDto) {
        var teamEntity = new TeamEntity();

        teamEntity.setName(addTeamInfoDto.getName());
        teamEntity.setManager(mapManagerDtoToManagerEntity(addTeamInfoDto.getManager()));
        teamEntity.setClub(mapClubDtoToClubEntity(addTeamInfoDto.getClub()));
        teamEntity.setLeague(mapLeagueDtoToLeagueEntity(addTeamInfoDto.getAssociation().));
        teamEntity.setTeamLogo(addTeamInfoDto.getTeamLogo());

        //TODO mapAddTeamDtoToTeamEntity(AddTeamInfoDto addTeamInfoDto)

        return teamEntity;
    }

    private LeagueEntity mapLeagueDtoToLeagueEntity(GetLeagueDto leagueDto) {
        var leagueEntity = new LeagueEntity();



        //TODO mapLeagueDtoToLeagueEntity(GetLeagueDto leagueDto)

        return leagueEntity;
    }

    private ClubEntity mapClubDtoToClubEntity(GetClubDto clubDto) {
        var clubEntity = new ClubEntity();

        //TODO mapClubDtoToClubEntity(GetClubDto clubDto)

        return clubEntity;
    }

    public GetTeamInfoDto mapTeamToGetTeamInfoDto(TeamEntity teamEntity) {
        GetTeamInfoDto getTeamInfoDto = new GetTeamInfoDto();

        getTeamInfoDto.setName(teamEntity.getName());

        //TODO mapTeamToGetTeamInfoDto(TeamEntity teamEntity)

        return getTeamInfoDto;
    }

    public ManagerEntity mapManagerDtoToManagerEntity(GetManagerDto managerDto) {
        var managerEntity = new ManagerEntity();

        managerEntity.setFirstName(managerDto.getFirstName());
        managerEntity.setLastName(managerDto.getLastName());
        managerEntity.setEmail(managerDto.getEmail());

        //TODO mapManagerDtoToManagerEntity(GetManagerDto managerDto)

        return managerEntity;
    }
}
