package com.example.baseballscoresheet.logic;

import com.example.baseballscoresheet.model.entities.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LogicTests extends TestConfiguration {

//    @Autowired
//    private TestUtils testUtils;

    @Test
    @Transactional
    @WithMockUser(roles = "user")
    public void testGetGameState() throws Exception {
        GameEntity game = gameService.findGameByGameNr(1);
        this.mockMvc.perform(get("/game/" + game.getId() + "/state").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    @Transactional
    @WithMockUser(roles = "user")
    public void testGetGame() throws Exception {
        long id = 1;
        PlayerEntity player = playerService.findPlayerById(id);
        this.mockMvc.perform(get("/player/" + player.getId()).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    @Transactional
    @WithMockUser(roles = "user")
    public void testCase1() throws Exception {
        GameEntity game = gameService.findGameByGameNr(1);

        // Step 1: Initial Game State
        Map<String, Object> expectedData = new HashMap<>();
        expectedData.put("game", 1);
        expectedData.put("awayRunsTotal", 0);
        expectedData.put("homeRunsTotal", 0);
        expectedData.put("awayErrors", 0);
        expectedData.put("homeErrors", 0);
        expectedData.put("awayHits", 0);
        expectedData.put("homeHits", 0);
        expectedData.put("awayLob", 0);
        expectedData.put("homeLob", 0);
        expectedData.put("awayRuns", List.of());
        expectedData.put("homeRuns", List.of());
        expectedData.put("inning", 1);
        expectedData.put("team", InningEntity.Team.AWAY.toString());
        expectedData.put("outs", 0);
        expectedData.put("balls", 0);
        expectedData.put("strikes", 0);
        expectedData.put("batter", Map.of("passnumber", 11, "firstName", "Bas", "lastName", "Topiac"));
        expectedData.put("firstBase", null);
        expectedData.put("secondBase", null);
        expectedData.put("thirdBase", null);

        this.checkGameState(game.getId(), expectedData);

        Map<String, Object> expectedActions = Map.of("batter", List.of("out", "safe", "error"));
        this.checkAvailableActions(game.getId(), expectedActions);

        // Step 2: Three Balls and a Triple Hit
        for (int i = 1; i <= 3; i++) {
            this.createAction(game.getId(), 0, ActionEntity.Type.BALL, null, null);
            this.checkGameState(game.getId(), Map.of("balls", i));
        }

        this.createAction(game.getId(), 0, ActionEntity.Type.HIT_TRIPLE, null, null);

        expectedData.put("balls", 0);
        expectedData.put("batter", Map.of("number", 22, "name", "Jack Sluggard"));
        expectedData.put("third_base", Map.of("number", 11, "name", "Bas Topiac"));
        this.checkGameState(game.getId(), expectedData);

        // Step 3: Single Hit and Hold Action
        this.createAction(game.getId(), 0, ActionEntity.Type.HIT_SINGLE, null, null);

        expectedActions = Map.of("third_base_runner", List.of("third_base", "home_base"));
        this.checkAvailableActions(game.getId(), expectedActions);

        this.createAction(game.getId(), 3, ActionEntity.Type.HOLD, null, null);

        expectedActions = Map.of(
                "batter", List.of("out", "safe", "error"),
                "first_base_runner", List.of("second_base"),
                "third_base_runner", List.of("home_base")
        );
        this.checkAvailableActions(game.getId(), expectedActions);

        // Step 4: Second Single Hit and Assisted Out
        this.createAction(game.getId(), 0, ActionEntity.Type.HIT_SINGLE, null, null);

        expectedActions = Map.of("third_base_runner", List.of("third_base", "home_base"));
        this.checkAvailableActions(game.getId(), expectedActions);

        this.createAction(game.getId(), 3, ActionEntity.Type.HOLD, null, null);

        expectedActions = Map.of("first_base_runner", List.of("second_base"));
        this.checkAvailableActions(game.getId(), expectedActions);

        this.createAction(game.getId(), 1, ActionEntity.Type.ASSISTED_OUT, null,
                List.of(
                        Map.of("defence_position", ResponsibleEntity.Place.SHORTSTOP.toString()),
                        Map.of("defence_position", ResponsibleEntity.Place.SECOND_BASE.toString())
                ));

        expectedData.put("balls", 0);
        expectedData.put("batter", Map.of("number", 44, "name", "Sally Curveball"));
        expectedData.put("first_base", Map.of("number", 33, "name", "Tommy Fastball"));
        expectedData.put("third_base", Map.of("number", 11, "name", "Bas Topiac"));
        this.checkGameState(game.getId(), expectedData);
    }

    @Test
    @Transactional
    public void testNotExistingRunner() throws Exception {
        GameEntity game = gameService.findGameByGameNr(1);

        // Step 1: Initial Game State
        Map<String, Object> expectedData = new HashMap<>();
        expectedData.put("game", 1);
        expectedData.put("away_runs_total", 0);
        expectedData.put("home_runs_total", 0);
        expectedData.put("away_errors", 0);
        expectedData.put("home_errors", 0);
        expectedData.put("away_hits", 0);
        expectedData.put("home_hits", 0);
        expectedData.put("away_LOB", 0);
        expectedData.put("home_LOB", 0);
        expectedData.put("away_runs", List.of());
        expectedData.put("home_runs", List.of());
        expectedData.put("inning", 1);
        expectedData.put("team", InningEntity.Team.AWAY.toString());
        expectedData.put("outs", 0);
        expectedData.put("balls", 0);
        expectedData.put("strikes", 0);
        expectedData.put("batter", Map.of("number", 11, "name", "Bas Topiac"));
        expectedData.put("first_base", null);
        expectedData.put("second_base", null);
        expectedData.put("third_base", null);

        this.checkGameState(game.getId(), expectedData);

        Map<String, Object> expectedActions = Map.of("batter", List.of("out", "safe", "error"));
        this.checkAvailableActions(game.getId(), expectedActions);

        // Step 2: Three Balls and a Triple Hit
        for (int i = 1; i <= 3; i++) {
            this.createAction(game.getId(), 0, ActionEntity.Type.BALL, null, null);
            this.checkGameState(game.getId(), Map.of("balls", i));
        }

        this.createAction(game.getId(), 0, ActionEntity.Type.HIT_TRIPLE, null, null);

        expectedData.put("balls", 0);
        expectedData.put("batter", Map.of("number", 22, "name", "Jack Sluggard"));
        expectedData.put("third_base", Map.of("number", 11, "name", "Bas Topiac"));
        this.checkGameState(game.getId(), expectedData);

        // Step 3: BAD CASE - stolen base for a runner at the second base
        assertThrows(Exception.class, () -> this.createAction(game.getId(), 2, ActionEntity.Type.STOLEN_BASE, 1, null));

        expectedActions = Map.of(
                "batter", List.of("out", "safe", "error"),
                "third_base_runner", List.of("home_base")
        );
        this.checkAvailableActions(game.getId(), expectedActions);
    }

    @Autowired
    private ObjectMapper objectMapper;

    public void compareDicts(Map<String, Object> expected, Map<String, Object> actual) {
        for (String key : expected.keySet()) {
            assertTrue(actual.containsKey(key), "Key '" + key + "' not found in response data");
            Object expectedValue = expected.get(key);
            Object actualValue = actual.get(key);
            if (expectedValue instanceof Map) {
                compareDicts((Map<String, Object>) expectedValue, (Map<String, Object>) actualValue);
            } else {
                assertEquals(expectedValue, actualValue, "Value mismatch for key '" + key + "': expected '" + expectedValue + "', got '" + actualValue + "'");
            }
        }
    }

    public void checkGameState(Long gameId, Map<String, Object> expectedData) throws Exception {
        MvcResult result = mockMvc.perform(get("/game/" + gameId + "/state")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Map<String, Object> actualData = objectMapper.readValue(responseBody, Map.class);
        compareDicts(expectedData, actualData);
    }

    public void checkAvailableActions(Long gameId, Map<String, Object> expectedActions) throws Exception {
        MvcResult result = mockMvc.perform(get("/game/" + gameId + "/action")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Map<String, Object> actualActions = objectMapper.readValue(responseBody, Map.class);
        compareDicts(expectedActions, actualActions);
    }

    public void createAction(Long gameId, int base, ActionEntity.Type action, Integer distance, List<Map<String, Object>> responsible) throws Exception {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("base", base);
        requestBody.put("type", action);
        if (distance != null) {
            requestBody.put("distance", distance);
        }
        if (responsible != null) {
            requestBody.put("responsible", responsible);
        }

        String requestJson = objectMapper.writeValueAsString(requestBody);

        MvcResult result = mockMvc.perform(post("/game/" + gameId + "/action")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Map<String, Object> responseMap = objectMapper.readValue(responseBody, Map.class);

        assertNotNull(responseMap, "Response JSON must have action data");
        assertEquals(1, responseMap.size(), "Response JSON must have 1 field");
        assertTrue(responseMap.containsKey("msg"), "Response JSON must have `msg` field");
    }
}