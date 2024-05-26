package com.example.baseballscoresheet.logic;

import com.example.baseballscoresheet.model.entities.*;
import com.example.baseballscoresheet.services.*;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public abstract class TestConfiguration {

    @Autowired
    protected GameService gameService;

    @Autowired
    protected PlayerService playerService;

    @Autowired
    protected TurnService turnService;

    @Autowired
    protected InningService inningService;

    @Autowired
    protected AssociationService associationService;

    @Autowired
    protected LeagueService leagueService;

    @Autowired
    protected GameStateService gameStateService;

    @BeforeEach
    public void setUp() {
        // Initialize the database with required entities
        // Create association
        AssociationEntity association = new AssociationEntity();
        association.setName("Example Association");
        association = associationService.create(association);

        LeagueEntity league = new LeagueEntity();
        league.setName("Example League");
        league.setAssociation(association);
        league = leagueService.create(league);

        GameStateEntity gameState = new GameStateEntity();
        gameState = gameStateService.create(gameState);

        GameEntity game = new GameEntity();
        game.setGameNr(1);
        game.setInnings(9);
        game.setAssociation(association);
        game.setLeague(league);
        game.setGameState(gameState);
        gameService.createGame(game);

        createBatters();
        createInning(game);
    }

    protected void createBatters() {
        String[][] battersData = {
                {"11", "Bas", "Topiac"},
                {"22", "Jack", "Sluggard"},
                {"33", "Tommy", " Fastball"},
                {"44", "Sally", "Curveball"},
                {"55", "Billy", "Homerun"},
                {"66", "Maggie", "Slider"},
                {"77", "Frankie", "Catcher"},
                {"88", "Jenny", "Pitcher"},
                {"99", "Timmy", "Baserunner"}
        };
        for (String[] batterData : battersData) {
            PlayerEntity player = new PlayerEntity();
            player.setPassnumber(Integer.parseInt(batterData[0]));
            player.setFirstName(batterData[1]);
            player.setLastName(batterData[2]);
            playerService.createPlayer(player);
        }
    }

    protected void createInning(GameEntity game) {
        InningEntity inning = new InningEntity();
        inning.setGame(game);
        inning.setInning(1);
        inning.setOuts(0);
        inning.setBattingTeam(InningEntity.Team.AWAY);
        inningService.create(inning);
    }
}
