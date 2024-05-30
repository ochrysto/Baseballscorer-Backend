package com.example.baseballscoresheet.controllerTests;

import com.example.baseballscoresheet.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    void findAllTeams() throws Exception {

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
