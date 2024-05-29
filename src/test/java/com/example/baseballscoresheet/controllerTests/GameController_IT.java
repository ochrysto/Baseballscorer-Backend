package com.example.baseballscoresheet.controllerTests;

import com.example.baseballscoresheet.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GameController_IT extends AbstractIntegrationTest {

    @Test
    void authorization() throws Exception {
        this.mockMvc.perform(get("/game"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "user")
    void createGame() throws Exception {
        String content = """
                        {
                            "gameNr": 1,
                            "date": "2024-05-21",
                            "location": "Bremen",
                            "innings": 9,
                            "associationId": 1,
                            "leagueId": 1,
                            "hostTeamId": 1,
                            "guestTeamId": 2,
                            "umpireIdsList": [
                                1,2
                            ]
                            "scorerId": 1
                        }
                """;
    }

    @Test
    @WithMockUser(roles = "user")
    void finishGame() throws Exception {
        String content = """
                        {
                            "innings": 9,
                            "attendance": 15000,
                            "startTime": "14:30:00.000000",
                            "endTime": "17:00:00.000000",
                            "durationInMinutes": 150
                        }
                """;
    }
}
