package com.example.baseballscoresheet.logic;

import com.example.baseballscoresheet.model.entities.*;
import com.example.baseballscoresheet.repositories.*;
import com.example.baseballscoresheet.services.*;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import com.example.baseballscoresheet.testcontainers.PostgresContextInitializer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ContextConfiguration(initializers = PostgresContextInitializer.class)
public abstract class TestConfiguration {
    @Autowired
    protected MockMvc mockMvc;

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

    @Autowired
    protected ActionRepository actionRepository;
    @Autowired
    protected AssociationRepository associationRepository;
    @Autowired
    protected ClubRepository clubRepository;
    @Autowired
    protected GameRepository gameRepository;
    @Autowired
    protected GameStateRepository gameStateRepository;
    @Autowired
    protected GameUmpireRepository gameUmpireRepository;
    @Autowired
    protected InningRepository inningRepository;
    @Autowired
    protected LeagueRepository leagueRepository;
    @Autowired
    protected LineupRepository lineupRepository;
    @Autowired
    protected LineupTeamPlayerRepository lineupTeamPlayerRepository;
    @Autowired
    protected ManagerRepository managerRepository;
    @Autowired
    protected PlayerRepository playerRepository;
    @Autowired
    protected PositionRepository positionRepository;
    @Autowired
    protected ScorerRepository scorerRepository;
    @Autowired
    protected TeamPlayerRepository teamPlayerRepository;
    @Autowired
    protected TeamRepository teamRepository;
    @Autowired
    protected TurnRepository turnRepository;
    @Autowired
    protected UmpireRepository umpireRepository;

    @BeforeEach
    public void setUp() {
        actionRepository.deleteAll();
        associationRepository.deleteAll();
        clubRepository.deleteAll();
        gameRepository.deleteAll();
        gameStateRepository.deleteAll();
        gameUmpireRepository.deleteAll();
        inningRepository.deleteAll();
        leagueRepository.deleteAll();
        lineupRepository.deleteAll();
        lineupTeamPlayerRepository.deleteAll();
        managerRepository.deleteAll();
        playerRepository.deleteAll();
        positionRepository.deleteAll();
        scorerRepository.deleteAll();
        teamPlayerRepository.deleteAll();
        teamRepository.deleteAll();
        turnRepository.deleteAll();
        umpireRepository.deleteAll();

        // Initialize the database with required entities
        // Create association
        AssociationEntity association = new AssociationEntity();
        association.setName("Example Association");
        association = associationService.create(association);

        // create league
        LeagueEntity league = new LeagueEntity();
        league.setName("Example League");
        league.setAssociation(association);
        league = leagueService.create(league);

        // create game state
        GameStateEntity gameState = new GameStateEntity();
        gameState = gameStateService.create(gameState);

        // create game
        GameEntity game = new GameEntity();
        game.setGameNr(1);
        game.setInnings(9);
        game.setAssociation(association);
        game.setLeague(league);
        game.setGameState(gameState);
        gameService.createGame(game);

        // create 9 players and 1st inning
        List<PlayerEntity> players = createBatters();
        InningEntity inning = createInning(game);

        // create first turn
        TurnEntity turn = new TurnEntity();
        turn.setInning(inning);
        turn.setPlayer(players.getFirst());
        turnService.createNewTurn(turn);
    }

    protected List<PlayerEntity> createBatters() {
        List<PlayerEntity> players = new ArrayList<>();

        String[][] battersData = {
                {"11", "Bas", "Topiac"},
                {"22", "Jack", "Sluggard"},
                {"33", "Tommy", "Fastball"},
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
            players.add(playerService.createPlayer(player));
        }
        return players;
    }

    protected InningEntity createInning(GameEntity game) {
        InningEntity inning = new InningEntity();
        inning.setGame(game);
        inning.setInning(1);
        inning.setOuts(0);
        inning.setBattingTeam(InningEntity.Team.AWAY);
        return inningService.create(inning);
    }
}
