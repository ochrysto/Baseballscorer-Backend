package com.example.baseballscoresheet.controllerTests;

import com.example.baseballscoresheet.model.entities.*;
import com.example.baseballscoresheet.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TeamController_IT extends AbstractIntegrationTest {

    @Test
    void authorization() throws Exception {
        this.mockMvc.perform(get("/team"))
                .andExpect(status().isUnauthorized());
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
}
