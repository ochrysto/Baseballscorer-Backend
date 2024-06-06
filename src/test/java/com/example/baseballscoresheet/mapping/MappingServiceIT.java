package com.example.baseballscoresheet.mapping;

import com.example.baseballscoresheet.model.dtos.league.LeagueGetDto;
import com.example.baseballscoresheet.model.dtos.manager.ManagerGetDto;
import com.example.baseballscoresheet.model.dtos.team.TeamGetDto;
import com.example.baseballscoresheet.model.entities.ClubEntity;
import com.example.baseballscoresheet.model.entities.LeagueEntity;
import com.example.baseballscoresheet.model.entities.ManagerEntity;
import com.example.baseballscoresheet.model.entities.TeamEntity;
import com.example.baseballscoresheet.model.dtos.club.ClubGetDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MappingServiceIT {

    private ModelMapper mapper;
    private TeamEntity teamEntity;
    private ClubEntity clubEntity;
    private ManagerEntity managerEntity;
    private LeagueEntity leagueEntity;

    @BeforeEach
    public void setup(){
        this.mapper = new ModelMapper();

        teamEntity = new TeamEntity();
        clubEntity = new ClubEntity();
        managerEntity = new ManagerEntity();
        leagueEntity = new LeagueEntity();

        clubEntity.setId(1L);
        clubEntity.setName("Club-1");
        clubEntity.setCity("Hannover");

        managerEntity.setId(1L);
        managerEntity.setFirstName("Max");
        managerEntity.setLastName("Muster");

        leagueEntity.setId(1L);
        leagueEntity.setName("League-1");

        teamEntity.setId(1L);
        teamEntity.setName("Team 1");
        teamEntity.setTeamLogo("TEAM-LOGO");
        teamEntity.setClub(clubEntity);
        teamEntity.setManager(managerEntity);
        teamEntity.setLeague(leagueEntity);
    }

    @Test
    public void whenMapClubEntityToGetClubDto_thenReturnClubDto(){
        ClubGetDto clubDto = this.mapper.map(clubEntity, ClubGetDto.class);

        assertEquals(clubEntity.getName(), clubDto.getName());
        assertEquals(clubDto.getCity(), clubEntity.getCity());

        System.out.println(clubEntity);
    }

    @Test
    public void whenMapManagerEntityToGetManagerDto_thenReturnManagerDto(){
        ManagerGetDto managerDto = this.mapper.map(managerEntity, ManagerGetDto.class);

        assertEquals(managerEntity.getFirstName(), managerDto.getFirstName());
        assertEquals(managerEntity.getLastName(), managerDto.getLastName());

        System.out.println(managerEntity);
    }

    @Test
    public void whenMapLeagueEntityToGetLeagueDto_thenReturnLeagueDto(){
        LeagueGetDto leagueDto = this.mapper.map(leagueEntity, LeagueGetDto.class);

        assertEquals(leagueEntity.getName(), leagueDto.getName());

        System.out.println(leagueEntity);
    }

    @Test
    public void whenMapTeamEntityToGetTeamInfoDto_thenReturnTeamDto(){
        TeamGetDto teamDto = this.mapper.map(teamEntity, TeamGetDto.class);

        assertEquals(teamEntity.getName(), teamDto.getName());
        assertEquals(teamEntity.getTeamLogo(), teamDto.getTeamLogo());

        System.out.println(teamEntity);
    }
}
