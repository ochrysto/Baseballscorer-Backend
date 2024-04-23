package com.example.baseballscoresheet;

import com.example.baseballscoresheet.model.ClubEntity;
import com.example.baseballscoresheet.model.LeagueEntity;
import com.example.baseballscoresheet.model.ManagerEntity;
import com.example.baseballscoresheet.model.TeamEntity;
import com.example.baseballscoresheet.model.dto.club.GetClubDto;
import com.example.baseballscoresheet.model.dto.league.GetLeagueDto;
import com.example.baseballscoresheet.model.dto.manager.GetManagerDto;
import com.example.baseballscoresheet.model.dto.team.GetTeamInfoDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;

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
        GetClubDto clubDto = this.mapper.map(clubEntity, GetClubDto.class);

        assertEquals(clubEntity.getName(), clubDto.getName());
        assertEquals(clubDto.getCity(), clubEntity.getCity());

        System.out.println(clubEntity);
    }

    @Test
    public void whenMapManagerEntityToGetManagerDto_thenReturnManagerDto(){
        GetManagerDto managerDto = this.mapper.map(managerEntity, GetManagerDto.class);

        assertEquals(managerEntity.getFirstName(), managerDto.getFirstName());
        assertEquals(managerEntity.getLastName(), managerDto.getLastName());

        System.out.println(managerEntity);
    }

    @Test
    public void whenMapLeagueEntityToGetLeagueDto_thenReturnLeagueDto(){
        GetLeagueDto leagueDto = this.mapper.map(leagueEntity, GetLeagueDto.class);

        assertEquals(leagueEntity.getName(), leagueDto.getName());

        System.out.println(leagueEntity);
    }

    @Test
    public void whenMapTeamEntityToGetTeamInfoDto_thenReturnTeamDto(){
        GetTeamInfoDto teamDto = this.mapper.map(teamEntity, GetTeamInfoDto.class);

        assertEquals(teamEntity.getName(), teamDto.getName());
        assertEquals(teamEntity.getTeamLogo(), teamDto.getTeamLogo());

        System.out.println(teamEntity);
    }
}
