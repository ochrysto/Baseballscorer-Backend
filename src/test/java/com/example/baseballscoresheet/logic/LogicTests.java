package com.example.baseballscoresheet.logic;

import com.example.baseballscoresheet.model.entities.ActionEntity;
import com.example.baseballscoresheet.model.entities.GameEntity;
import com.example.baseballscoresheet.model.entities.PlayerEntity;
import com.example.baseballscoresheet.model.entities.ResponsibleEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LogicTests extends TestConfiguration {
    @Autowired
    private ObjectMapper objectMapper;

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

        String expectedJson = """
                {
                  "game": 1,
                  "awayRunsTotal": 0,
                  "homeRunsTotal": 0,
                  "awayErrors": 0,
                  "homeErrors": 0,
                  "awayHits": 0,
                  "homeHits": 0,
                  "awayLob": 0,
                  "homeLob": 0,
                  "awayRuns": [],
                  "homeRuns": [],
                  "inning": 1,
                  "team": "AWAY",
                  "outs": 0,
                  "balls": 0,
                  "strikes": 0,
                  "onDeck": null,
                  "batter": {
                    "id": 1,
                    "firstName": "Bas",
                    "lastName": "Topiac",
                    "passnumber": 11
                  },
                  "firstBase": null,
                  "secondBase": null,
                  "thirdBase": null
                }""";
        this.checkGameState(game.getId(), expectedJson);

        expectedJson = """
                {
                  "batter": {
                    "out": [
                      {
                        "button": "Flyout",
                        "actionType": "FLYOUT",
                        "responsibleRequired": true,
                        "multipleResponsibleRequired": true
                      },
                      {
                        "button": "Ground out",
                        "actionType": "GROUND_OUT",
                        "responsibleRequired": true,
                        "multipleResponsibleRequired": true
                      },
                      {
                        "button": "Out by rule",
                        "actionType": "OUT_BY_RULE",
                        "responsibleRequired": true,
                        "multipleResponsibleRequired": true
                      },
                      {
                        "button": "Appeal play",
                        "actionType": "APPEAL_PLAY",
                        "responsibleRequired": true,
                        "multipleResponsibleRequired": true
                      },
                      {
                        "button": "Picked off",
                        "actionType": "PICKED_OFF",
                        "responsibleRequired": true,
                        "multipleResponsibleRequired": true
                      },
                      {
                        "button": "Caught base",
                        "actionType": "CAUGHT_BASE",
                        "responsibleRequired": true,
                        "multipleResponsibleRequired": true
                      },
                      {
                        "button": "Assisted out",
                        "actionType": "ASSISTED_OUT",
                        "responsibleRequired": true,
                        "multipleResponsibleRequired": true
                      },
                      {
                        "button": "Unassisted out",
                        "actionType": "UNASSISTED_OUT",
                        "responsibleRequired": true,
                        "multipleResponsibleRequired": true
                      },
                      {
                        "button": "Strikeout",
                        "actionType": "STRIKEOUT",
                        "responsibleRequired": true,
                        "multipleResponsibleRequired": true
                      },
                      {
                        "button": "Sacrifice hit",
                        "actionType": "SACRIFICE_HIT",
                        "responsibleRequired": true,
                        "multipleResponsibleRequired": true
                      },
                      {
                        "button": "Sacrifice fly",
                        "actionType": "SACRIFICE_FLY",
                        "responsibleRequired": true,
                        "multipleResponsibleRequired": true
                      }
                    ],
                    "safe": [
                      {
                        "button": "Ball",
                        "actionType": "BALL",
                        "responsibleRequired": true,
                        "multipleResponsibleRequired": true
                      },
                      {
                        "button": "Strike",
                        "actionType": "STRIKE",
                        "responsibleRequired": true,
                        "multipleResponsibleRequired": true
                      },
                      {
                        "button": "Foul",
                        "actionType": "FOUL",
                        "responsibleRequired": true,
                        "multipleResponsibleRequired": true
                      },
                      {
                        "button": "Base on balls",
                        "actionType": "BASE_ON_BALLS",
                        "responsibleRequired": true,
                        "multipleResponsibleRequired": true
                      },
                      {
                        "button": "Hit single",
                        "actionType": "HIT_SINGLE",
                        "responsibleRequired": true,
                        "multipleResponsibleRequired": true
                      },
                      {
                        "button": "Hit double",
                        "actionType": "HIT_DOUBLE",
                        "responsibleRequired": true,
                        "multipleResponsibleRequired": true
                      },
                      {
                        "button": "Hit triple",
                        "actionType": "HIT_TRIPLE",
                        "responsibleRequired": true,
                        "multipleResponsibleRequired": true
                      },
                      {
                        "button": "Home run",
                        "actionType": "HOME_RUN",
                        "responsibleRequired": true,
                        "multipleResponsibleRequired": true
                      }
                    ],
                    "error": [
                      {
                        "button": "Error",
                        "actionType": "ERROR",
                        "responsibleRequired": true,
                        "multipleResponsibleRequired": true
                      },
                      {
                        "button": "Assisted error",
                        "actionType": "ASSISTED_ERROR",
                        "responsibleRequired": true,
                        "multipleResponsibleRequired": true
                      }
                    ]
                  },
                  "firstBaseRunner": null,
                  "secondBaseRunner": null,
                  "thirdBaseRunner": null
                }
                """;
        this.checkAvailableActions(game.getId(), expectedJson);

        // Step 2: Three Balls and a Triple Hit
        for (int i = 1; i <= 3; i++) {
            this.createAction(game.getId(), 0, ActionEntity.Type.BALL, null, null);
            expectedJson = String.format("""
                    {
                      "game": 1,
                      "awayRunsTotal": 0,
                      "homeRunsTotal": 0,
                      "awayErrors": 0,
                      "homeErrors": 0,
                      "awayHits": 0,
                      "homeHits": 0,
                      "awayLob": 0,
                      "homeLob": 0,
                      "awayRuns": [],
                      "homeRuns": [],
                      "inning": 1,
                      "team": "AWAY",
                      "outs": 0,
                      "balls": %s,
                      "strikes": 0,
                      "onDeck": null,
                      "batter": {
                        "id": 1,
                        "firstName": "Bas",
                        "lastName": "Topiac",
                        "passnumber": 11
                      },
                      "firstBase": null,
                      "secondBase": null,
                      "thirdBase": null
                    }""", i);
            this.checkGameState(game.getId(), expectedJson);
        }

        this.createAction(game.getId(), 0, ActionEntity.Type.HIT_TRIPLE, null, null);

        expectedJson = "{\"game\":1,\"awayRunsTotal\":0,\"homeRunsTotal\":0,\"awayErrors\":0,\"homeErrors\":0,\"awayHits\":0,\"homeHits\":0,\"awayLob\":0,\"homeLob\":0,\"awayRuns\":[],\"homeRuns\":[],\"inning\":1,\"team\":\"AWAY\",\"outs\":0,\"balls\":0,\"strikes\":0,\"onDeck\":null,\"batter\":{\"id\":2,\"firstName\":\"Jack\",\"lastName\":\"Sluggard\",\"passnumber\":22},\"firstBase\":null,\"secondBase\":null,\"thirdBase\":{\"id\":1,\"firstName\":\"Bas\",\"lastName\":\"Topiac\",\"passnumber\":11}}";
        this.checkGameState(game.getId(), expectedJson);

        // Step 3: Single Hit and Hold Action
        this.createAction(game.getId(), 0, ActionEntity.Type.HIT_SINGLE, null, null);

        expectedJson = """
                {
                  "batter": null,
                  "firstBaseRunner": null,
                  "secondBaseRunner": null,
                  "thirdBaseRunner": {
                    "firstBase": null,
                    "secondBase": null,
                    "thirdBase": {
                      "out": [
                        {
                          "button": "Ground out",
                          "actionType": "GROUND_OUT",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Out by rule",
                          "actionType": "OUT_BY_RULE",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Appeal play",
                          "actionType": "APPEAL_PLAY",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Picked off",
                          "actionType": "PICKED_OFF",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Caught base",
                          "actionType": "CAUGHT_BASE",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Assisted out",
                          "actionType": "ASSISTED_OUT",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Unassisted out",
                          "actionType": "UNASSISTED_OUT",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        }
                      ],
                      "safe": [
                        {
                          "button": "Hold",
                          "actionType": "HOLD",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        }
                      ],
                      "error": [
                        {
                          "button": "Error",
                          "actionType": "ERROR",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Assisted error",
                          "actionType": "ASSISTED_ERROR",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        }
                      ]
                    },
                    "homeBase": {
                      "out": [
                        {
                          "button": "Ground out",
                          "actionType": "GROUND_OUT",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Out by rule",
                          "actionType": "OUT_BY_RULE",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Appeal play",
                          "actionType": "APPEAL_PLAY",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Picked off",
                          "actionType": "PICKED_OFF",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Caught base",
                          "actionType": "CAUGHT_BASE",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Assisted out",
                          "actionType": "ASSISTED_OUT",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Unassisted out",
                          "actionType": "UNASSISTED_OUT",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        }
                      ],
                      "safe": [
                        {
                          "button": "Stolen base",
                          "actionType": "STOLEN_BASE",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Wild pitch",
                          "actionType": "WILD_PITCH",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Passed ball",
                          "actionType": "PASSED_BALL",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Advanced by rule",
                          "actionType": "ADVANCED_BY_RULE",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Advanced by batter",
                          "actionType": "ADVANCED_BY_BATTER",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        }
                      ],
                      "error": [
                        {
                          "button": "Error",
                          "actionType": "ERROR",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Assisted error",
                          "actionType": "ASSISTED_ERROR",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        }
                      ]
                    }
                  }
                }""";
        this.checkAvailableActions(game.getId(), expectedJson);

        this.createAction(game.getId(), 3, ActionEntity.Type.HOLD, null, null);

        expectedJson = """
                {
                  "batter": {
                    "out": [
                      {
                        "button": "Flyout",
                        "actionType": "FLYOUT",
                        "responsibleRequired": true,
                        "multipleResponsibleRequired": true
                      },
                      {
                        "button": "Ground out",
                        "actionType": "GROUND_OUT",
                        "responsibleRequired": true,
                        "multipleResponsibleRequired": true
                      },
                      {
                        "button": "Out by rule",
                        "actionType": "OUT_BY_RULE",
                        "responsibleRequired": true,
                        "multipleResponsibleRequired": true
                      },
                      {
                        "button": "Appeal play",
                        "actionType": "APPEAL_PLAY",
                        "responsibleRequired": true,
                        "multipleResponsibleRequired": true
                      },
                      {
                        "button": "Picked off",
                        "actionType": "PICKED_OFF",
                        "responsibleRequired": true,
                        "multipleResponsibleRequired": true
                      },
                      {
                        "button": "Caught base",
                        "actionType": "CAUGHT_BASE",
                        "responsibleRequired": true,
                        "multipleResponsibleRequired": true
                      },
                      {
                        "button": "Assisted out",
                        "actionType": "ASSISTED_OUT",
                        "responsibleRequired": true,
                        "multipleResponsibleRequired": true
                      },
                      {
                        "button": "Unassisted out",
                        "actionType": "UNASSISTED_OUT",
                        "responsibleRequired": true,
                        "multipleResponsibleRequired": true
                      },
                      {
                        "button": "Strikeout",
                        "actionType": "STRIKEOUT",
                        "responsibleRequired": true,
                        "multipleResponsibleRequired": true
                      },
                      {
                        "button": "Sacrifice hit",
                        "actionType": "SACRIFICE_HIT",
                        "responsibleRequired": true,
                        "multipleResponsibleRequired": true
                      },
                      {
                        "button": "Sacrifice fly",
                        "actionType": "SACRIFICE_FLY",
                        "responsibleRequired": true,
                        "multipleResponsibleRequired": true
                      }
                    ],
                    "safe": [
                      {
                        "button": "Ball",
                        "actionType": "BALL",
                        "responsibleRequired": true,
                        "multipleResponsibleRequired": true
                      },
                      {
                        "button": "Strike",
                        "actionType": "STRIKE",
                        "responsibleRequired": true,
                        "multipleResponsibleRequired": true
                      },
                      {
                        "button": "Foul",
                        "actionType": "FOUL",
                        "responsibleRequired": true,
                        "multipleResponsibleRequired": true
                      },
                      {
                        "button": "Base on balls",
                        "actionType": "BASE_ON_BALLS",
                        "responsibleRequired": true,
                        "multipleResponsibleRequired": true
                      },
                      {
                        "button": "Hit single",
                        "actionType": "HIT_SINGLE",
                        "responsibleRequired": true,
                        "multipleResponsibleRequired": true
                      },
                      {
                        "button": "Hit double",
                        "actionType": "HIT_DOUBLE",
                        "responsibleRequired": true,
                        "multipleResponsibleRequired": true
                      },
                      {
                        "button": "Hit triple",
                        "actionType": "HIT_TRIPLE",
                        "responsibleRequired": true,
                        "multipleResponsibleRequired": true
                      },
                      {
                        "button": "Home run",
                        "actionType": "HOME_RUN",
                        "responsibleRequired": true,
                        "multipleResponsibleRequired": true
                      }
                    ],
                    "error": [
                      {
                        "button": "Error",
                        "actionType": "ERROR",
                        "responsibleRequired": true,
                        "multipleResponsibleRequired": true
                      },
                      {
                        "button": "Assisted error",
                        "actionType": "ASSISTED_ERROR",
                        "responsibleRequired": true,
                        "multipleResponsibleRequired": true
                      }
                    ]
                  },
                  "firstBaseRunner": {
                    "firstBase": null,
                    "secondBase": {
                      "out": [
                        {
                          "button": "Ground out",
                          "actionType": "GROUND_OUT",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Out by rule",
                          "actionType": "OUT_BY_RULE",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Appeal play",
                          "actionType": "APPEAL_PLAY",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Picked off",
                          "actionType": "PICKED_OFF",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Caught base",
                          "actionType": "CAUGHT_BASE",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Assisted out",
                          "actionType": "ASSISTED_OUT",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Unassisted out",
                          "actionType": "UNASSISTED_OUT",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        }
                      ],
                      "safe": [
                        {
                          "button": "Stolen base",
                          "actionType": "STOLEN_BASE",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Wild pitch",
                          "actionType": "WILD_PITCH",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Passed ball",
                          "actionType": "PASSED_BALL",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Advanced by rule",
                          "actionType": "ADVANCED_BY_RULE",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Advanced by batter",
                          "actionType": "ADVANCED_BY_BATTER",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        }
                      ],
                      "error": [
                        {
                          "button": "Error",
                          "actionType": "ERROR",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Assisted error",
                          "actionType": "ASSISTED_ERROR",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        }
                      ]
                    },
                    "thirdBase": null,
                    "homeBase": null
                  },
                  "secondBaseRunner": null,
                  "thirdBaseRunner": {
                    "firstBase": null,
                    "secondBase": null,
                    "thirdBase": null,
                    "homeBase": {
                      "out": [
                        {
                          "button": "Ground out",
                          "actionType": "GROUND_OUT",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Out by rule",
                          "actionType": "OUT_BY_RULE",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Appeal play",
                          "actionType": "APPEAL_PLAY",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Picked off",
                          "actionType": "PICKED_OFF",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Caught base",
                          "actionType": "CAUGHT_BASE",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Assisted out",
                          "actionType": "ASSISTED_OUT",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Unassisted out",
                          "actionType": "UNASSISTED_OUT",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        }
                      ],
                      "safe": [
                        {
                          "button": "Stolen base",
                          "actionType": "STOLEN_BASE",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Wild pitch",
                          "actionType": "WILD_PITCH",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Passed ball",
                          "actionType": "PASSED_BALL",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Advanced by rule",
                          "actionType": "ADVANCED_BY_RULE",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Advanced by batter",
                          "actionType": "ADVANCED_BY_BATTER",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        }
                      ],
                      "error": [
                        {
                          "button": "Error",
                          "actionType": "ERROR",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Assisted error",
                          "actionType": "ASSISTED_ERROR",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        }
                      ]
                    }
                  }
                }""";
        this.checkAvailableActions(game.getId(), expectedJson);

        // Step 4: Second Single Hit and Assisted Out
        this.createAction(game.getId(), 0, ActionEntity.Type.HIT_SINGLE, null, null);

        expectedJson = """
                {
                  "batter": null,
                  "firstBaseRunner": null,
                  "secondBaseRunner": null,
                  "thirdBaseRunner": {
                    "firstBase": null,
                    "secondBase": null,
                    "thirdBase": {
                      "out": [
                        {
                          "button": "Ground out",
                          "actionType": "GROUND_OUT",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Out by rule",
                          "actionType": "OUT_BY_RULE",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Appeal play",
                          "actionType": "APPEAL_PLAY",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Picked off",
                          "actionType": "PICKED_OFF",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Caught base",
                          "actionType": "CAUGHT_BASE",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Assisted out",
                          "actionType": "ASSISTED_OUT",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Unassisted out",
                          "actionType": "UNASSISTED_OUT",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        }
                      ],
                      "safe": [
                        {
                          "button": "Hold",
                          "actionType": "HOLD",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        }
                      ],
                      "error": [
                        {
                          "button": "Error",
                          "actionType": "ERROR",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Assisted error",
                          "actionType": "ASSISTED_ERROR",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        }
                      ]
                    },
                    "homeBase": {
                      "out": [
                        {
                          "button": "Ground out",
                          "actionType": "GROUND_OUT",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Out by rule",
                          "actionType": "OUT_BY_RULE",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Appeal play",
                          "actionType": "APPEAL_PLAY",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Picked off",
                          "actionType": "PICKED_OFF",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Caught base",
                          "actionType": "CAUGHT_BASE",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Assisted out",
                          "actionType": "ASSISTED_OUT",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Unassisted out",
                          "actionType": "UNASSISTED_OUT",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        }
                      ],
                      "safe": [
                        {
                          "button": "Stolen base",
                          "actionType": "STOLEN_BASE",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Wild pitch",
                          "actionType": "WILD_PITCH",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Passed ball",
                          "actionType": "PASSED_BALL",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Advanced by rule",
                          "actionType": "ADVANCED_BY_RULE",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Advanced by batter",
                          "actionType": "ADVANCED_BY_BATTER",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        }
                      ],
                      "error": [
                        {
                          "button": "Error",
                          "actionType": "ERROR",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Assisted error",
                          "actionType": "ASSISTED_ERROR",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        }
                      ]
                    }
                  }
                }""";
        this.checkAvailableActions(game.getId(), expectedJson);

        this.createAction(game.getId(), 3, ActionEntity.Type.HOLD, null, null);

        expectedJson = """
                {
                  "batter": null,
                  "firstBaseRunner": {
                    "firstBase": null,
                    "secondBase": {
                      "out": [
                        {
                          "button": "Ground out",
                          "actionType": "GROUND_OUT",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Out by rule",
                          "actionType": "OUT_BY_RULE",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Appeal play",
                          "actionType": "APPEAL_PLAY",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Picked off",
                          "actionType": "PICKED_OFF",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Caught base",
                          "actionType": "CAUGHT_BASE",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Assisted out",
                          "actionType": "ASSISTED_OUT",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Unassisted out",
                          "actionType": "UNASSISTED_OUT",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        }
                      ],
                      "safe": [
                        {
                          "button": "Stolen base",
                          "actionType": "STOLEN_BASE",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Wild pitch",
                          "actionType": "WILD_PITCH",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Passed ball",
                          "actionType": "PASSED_BALL",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Advanced by rule",
                          "actionType": "ADVANCED_BY_RULE",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Advanced by batter",
                          "actionType": "ADVANCED_BY_BATTER",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        }
                      ],
                      "error": [
                        {
                          "button": "Error",
                          "actionType": "ERROR",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        },
                        {
                          "button": "Assisted error",
                          "actionType": "ASSISTED_ERROR",
                          "responsibleRequired": true,
                          "multipleResponsibleRequired": true
                        }
                      ]
                    },
                    "thirdBase": null,
                    "homeBase": null
                  },
                  "secondBaseRunner": null,
                  "thirdBaseRunner": null
                }""";
        this.checkAvailableActions(game.getId(), expectedJson);

        this.createAction(game.getId(), 1, ActionEntity.Type.ASSISTED_OUT, null, List.of(Map.of("defence_position", ResponsibleEntity.Place.SHORTSTOP.toString()), Map.of("defence_position", ResponsibleEntity.Place.SECOND_BASE.toString())));

        expectedJson = """
                {
                  "game": 1,
                  "awayRunsTotal": 0,
                  "homeRunsTotal": 0,
                  "awayErrors": 0,
                  "homeErrors": 0,
                  "awayHits": 0,
                  "homeHits": 0,
                  "awayLob": 0,
                  "homeLob": 0,
                  "awayRuns": [],
                  "homeRuns": [],
                  "inning": 1,
                  "team": "AWAY",
                  "outs": 0,
                  "balls": 0,
                  "strikes": 0,
                  "onDeck": null,
                  "batter": {
                    "id": 4,
                    "firstName": "Sally",
                    "lastName": "Curveball",
                    "passnumber": 44
                  },
                  "firstBase": {
                    "id": 3,
                    "firstName": "Tommy",
                    "lastName": "Fastball",
                    "passnumber": 33
                  },
                  "secondBase": null,
                  "thirdBase": {
                    "id": 1,
                    "firstName": "Bas",
                    "lastName": "Topiac",
                    "passnumber": 11
                  }
                }""";
        this.checkGameState(game.getId(), expectedJson);
    }

    @Disabled(value = "Test is not yet fixed")
    @Test
    @Transactional
    public void testNotExistingRunner() throws Exception {
        GameEntity game = gameService.findGameByGameNr(1);

        // Step 1: Initial Game State
        String expectedJson = "";
        this.checkGameState(game.getId(), expectedJson);

        expectedJson = "";
        this.checkAvailableActions(game.getId(), expectedJson);

        // Step 2: Three Balls and a Triple Hit
        for (int i = 1; i <= 3; i++) {
            this.createAction(game.getId(), 0, ActionEntity.Type.BALL, null, null);
            expectedJson = "";
            this.checkGameState(game.getId(), expectedJson);
        }

        this.createAction(game.getId(), 0, ActionEntity.Type.HIT_TRIPLE, null, null);

        expectedJson = "";
        this.checkGameState(game.getId(), expectedJson);

        // Step 3: BAD CASE - stolen base for a runner at the second base
        assertThrows(Exception.class, () -> this.createAction(game.getId(), 2, ActionEntity.Type.STOLEN_BASE, 1, null));

        expectedJson = "";
        this.checkAvailableActions(game.getId(), expectedJson);
    }

    public void checkGameState(Long gameId, String expectedData) throws Exception {
        MvcResult result = mockMvc.perform(get("/game/" + gameId + "/state").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        String responseBody = result.getResponse().getContentAsString();
        JSONAssert.assertEquals(responseBody, expectedData, false);
    }

    public void checkAvailableActions(Long gameId, String expectedActions) throws Exception {
        // Perform the GET request to the endpoint
        MvcResult result = mockMvc.perform(get("/game/" + gameId + "/action").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        JSONAssert.assertEquals(jsonResponse, expectedActions, false);
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

        MvcResult result = mockMvc.perform(post("/game/" + gameId + "/action").contentType(MediaType.APPLICATION_JSON).content(requestJson)).andExpect(status().isOk()).andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Map<String, Object> responseMap = objectMapper.readValue(responseBody, Map.class);

        assertNotNull(responseMap, "Response JSON must have action data");
        assertEquals(1, responseMap.size(), "Response JSON must have 1 field");
        assertTrue(responseMap.containsKey("msg"), "Response JSON must have `msg` field");
    }
}