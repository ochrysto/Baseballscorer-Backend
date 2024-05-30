package com.example.baseballscoresheet.controllerTests;

import com.example.baseballscoresheet.model.entities.*;
import com.example.baseballscoresheet.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// TODO ich

public class GameController_IT extends AbstractIntegrationTest {

    @Test
    void authorization() throws Exception {
        this.mockMvc.perform(get("/game")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "user")
    void createGame() throws Exception {

        var association = new AssociationEntity();
        association.setId(1L);
        association.setName("MockAssociation");
        this.associationRepository.save(association);

        var league = new LeagueEntity();
        league.setId(1L);
        league.setName("MockLeague");
        league.setAssociation(association);
        this.leagueRepository.save(league);

        var hostTeam = new TeamEntity();
        hostTeam.setId(1L);
        hostTeam.setName("MockGuestTeam");
        this.teamRepository.save(hostTeam);

        var guestTeam = new TeamEntity();
        guestTeam.setId(2L);
        guestTeam.setName("MockGuestTeam");
        this.teamRepository.save(guestTeam);

        var umpire1 = new UmpireEntity();
        umpire1.setId(1L);
        umpire1.setPassnumber(123L);
        umpire1.setFirstName("Piet");
        umpire1.setLastName("Müller");
        this.umpireRepository.save(umpire1);

        var umpire2 = new UmpireEntity();
        umpire2.setId(2L);
        umpire2.setPassnumber(123L);
        umpire2.setFirstName("Peter");
        umpire2.setLastName("Meyer");
        this.umpireRepository.save(umpire2);

        var scorer = new ScorerEntity();
        scorer.setId(1L);
        scorer.setPassnumber(123L);
        scorer.setFirstName("Paul");
        scorer.setLastName("Muster");
        this.scorerRepository.save(scorer);

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

        final String response = this.mockMvc.perform(post("/game")
                        .content(content).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("gameNr", is(1)))
                .andExpect(jsonPath("date", is("2024-05-21")))
                .andExpect(jsonPath("location", is("Bremen")))
                .andExpect(jsonPath("innings", is(9)))
                .andReturn()
                .getResponse()
                .getContentAsString();
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

        // TODO return GetFinishedGameDto - gameNr = 1
        final String response = this.mockMvc.perform(post("/game/1")
                        .content(content).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    @WithMockUser(roles = "user")
    void addLineupsToGame() throws Exception {
        String content = """
                        [
                            {
                                "teamId": 3,
                                "isHostTeam": true,
                                "isGuestTeam": false,
                                "playerList": [
                                    {
                                        "playerId": 5,
                                        "jerseyNr": 5,
                                        "positionId": 5
                                    },  
                                    {
                                        "playerId": 6,
                                        "jerseyNr": 6,
                                        "positionId": 6
                                    }
                                ]
                            },
                            {
                                "teamId": 4,
                                "isHostTeam": false,
                                "isGuestTeam": true,
                                "playerList": [
                                    {
                                        "playerId": 7,
                                        "jerseyNr": 7,
                                        "positionId": 7
                                    },  
                                    {
                                        "playerId": 8,
                                        "jerseyNr": 8,
                                        "positionId": 8
                                    }
                                ]
                            }
                        ]
                """;

        // TODO return GetGameWithLineupsDto - gameNr = 1
        final String response = this.mockMvc.perform(post("/game/1/lineup")
                        .content(content).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }


}
