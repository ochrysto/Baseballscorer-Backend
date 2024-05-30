package com.example.baseballscoresheet.controllerTests;

import com.example.baseballscoresheet.model.entities.*;
import com.example.baseballscoresheet.model.entities.AssociationEntity;
import com.example.baseballscoresheet.model.entities.ClubEntity;
import com.example.baseballscoresheet.model.entities.LeagueEntity;
import com.example.baseballscoresheet.model.entities.TeamEntity;
import com.example.baseballscoresheet.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TeamController_IT extends AbstractIntegrationTest {

    // TODO Sefa

    @Test
    void authorization() throws Exception {
        this.mockMvc.perform(get("/team"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void createTeam() throws Exception {

    }

    @Test
    @WithMockUser
    void findAllTeams() throws Exception {
        var association = new AssociationEntity();
        association.setId(1L);

        var league = new LeagueEntity();
        league.setName("Test League");
        league.setAssociation(association);

        var club = new ClubEntity();
        club.setName("Test Club");
        club.setAssociation(association);

        var team = new TeamEntity();
        team.setId(1L);
        team.setClub(club);
        team.setLeague(league);

        this.associationRepository.save(association);
        this.clubRepository.save(club);
        this.leagueRepository.save(league);
        this.teamRepository.save(team);

        this.mockMvc.perform(get("/team"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].teamId", is(1)))
                .andExpect(jsonPath("$[0].club.name", is("Test Club")))
                .andExpect(jsonPath("$[0].league.id", is(1)))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void findTeamById() throws Exception {

    }

    @Test
    void updateTeamInfo() throws Exception {

    }

    @Test
    void deleteTeamById() throws Exception {

    }

    @Test
    void addPlayersToTeam() throws Exception {

    }

    @Test
    void removePlayerFromTeam() throws Exception {

    }

    @Test
    void getAllPlayersFromTeam() throws Exception {

    }
}
