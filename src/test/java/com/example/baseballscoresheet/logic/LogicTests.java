package com.example.baseballscoresheet.logic;

import com.example.baseballscoresheet.model.entities.ActionEntity;
import com.example.baseballscoresheet.model.entities.GameEntity;
import com.example.baseballscoresheet.model.entities.InningEntity;
import com.example.baseballscoresheet.model.entities.ResponsibleEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class LogicTests extends TestConfiguration {

    @Autowired
    private TestUtils testUtils;

    @Test
    public void testCase1() {
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

        testUtils.checkGameState(game.getId(), expectedData);

        Map<String, Object> expectedActions = Map.of("batter", List.of("out", "safe", "error"));
        testUtils.checkAvailableActions(game.getId(), expectedActions);

        // Step 2: Three Balls and a Triple Hit
        for (int i = 1; i <= 3; i++) {
            testUtils.createAction(game.getId(), 0, ActionEntity.Type.BALL, null, null);
            testUtils.checkGameState(game.getId(), Map.of("balls", i));
        }

        testUtils.createAction(game.getId(), 0, ActionEntity.Type.HIT_TRIPLE, null, null);

        expectedData.put("balls", 0);
        expectedData.put("batter", Map.of("number", 22, "name", "Jack Sluggard"));
        expectedData.put("third_base", Map.of("number", 11, "name", "Bas Topiac"));
        testUtils.checkGameState(game.getId(), expectedData);

        // Step 3: Single Hit and Hold Action
        testUtils.createAction(game.getId(), 0, ActionEntity.Type.HIT_SINGLE, null, null);

        expectedActions = Map.of("third_base_runner", List.of("third_base", "home_base"));
        testUtils.checkAvailableActions(game.getId(), expectedActions);

        testUtils.createAction(game.getId(), 3, ActionEntity.Type.HOLD, null, null);

        expectedActions = Map.of(
                "batter", List.of("out", "safe", "error"),
                "first_base_runner", List.of("second_base"),
                "third_base_runner", List.of("home_base")
        );
        testUtils.checkAvailableActions(game.getId(), expectedActions);

        // Step 4: Second Single Hit and Assisted Out
        testUtils.createAction(game.getId(), 0, ActionEntity.Type.HIT_SINGLE, null, null);

        expectedActions = Map.of("third_base_runner", List.of("third_base", "home_base"));
        testUtils.checkAvailableActions(game.getId(), expectedActions);

        testUtils.createAction(game.getId(), 3, ActionEntity.Type.HOLD, null, null);

        expectedActions = Map.of("first_base_runner", List.of("second_base"));
        testUtils.checkAvailableActions(game.getId(), expectedActions);

        testUtils.createAction(game.getId(), 1, ActionEntity.Type.ASSISTED_OUT, null,
                List.of(
                        Map.of("defence_position", ResponsibleEntity.Place.SHORTSTOP.toString()),
                        Map.of("defence_position", ResponsibleEntity.Place.SECOND_BASE.toString())
                ));

        expectedData.put("balls", 0);
        expectedData.put("batter", Map.of("number", 44, "name", "Sally Curveball"));
        expectedData.put("first_base", Map.of("number", 33, "name", "Tommy Fastball"));
        expectedData.put("third_base", Map.of("number", 11, "name", "Bas Topiac"));
        testUtils.checkGameState(game.getId(), expectedData);
    }

    @Test
    public void testNotExistingRunner() {
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

        testUtils.checkGameState(game.getId(), expectedData);

        Map<String, Object> expectedActions = Map.of("batter", List.of("out", "safe", "error"));
        testUtils.checkAvailableActions(game.getId(), expectedActions);

        // Step 2: Three Balls and a Triple Hit
        for (int i = 1; i <= 3; i++) {
            testUtils.createAction(game.getId(), 0, ActionEntity.Type.BALL, null, null);
            testUtils.checkGameState(game.getId(), Map.of("balls", i));
        }

        testUtils.createAction(game.getId(), 0, ActionEntity.Type.HIT_TRIPLE, null, null);

        expectedData.put("balls", 0);
        expectedData.put("batter", Map.of("number", 22, "name", "Jack Sluggard"));
        expectedData.put("third_base", Map.of("number", 11, "name", "Bas Topiac"));
        testUtils.checkGameState(game.getId(), expectedData);

        // Step 3: BAD CASE - stolen base for a runner at the second base
        assertThrows(Exception.class, () -> testUtils.createAction(game.getId(), 2, ActionEntity.Type.STOLEN_BASE, 1, null));

        expectedActions = Map.of(
                "batter", List.of("out", "safe", "error"),
                "third_base_runner", List.of("home_base")
        );
        testUtils.checkAvailableActions(game.getId(), expectedActions);
    }
}