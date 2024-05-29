package com.example.baseballscoresheet.logic;

import com.example.baseballscoresheet.model.entities.*;
import com.example.baseballscoresheet.repositories.truncate.TruncateRepository;
import com.example.baseballscoresheet.services.*;
import com.example.baseballscoresheet.testcontainers.PostgresContextInitializer;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private Map<String, TruncateRepository> truncateRepositories; // Autowire all beans implementing TruncateRepository by name

    @BeforeEach
    public void setUp() {
        truncateAndResetTables();

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
        turn.setPlayer(players.get(0));
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

    /**
     * Truncates all relevant tables and resets their auto-increment counters.
     * This method excludes non-entity repositories.
     */
    private void truncateAndResetTables() {
        // Exclude TruncateRepositoryImpl from the truncation list
        Map<String, TruncateRepository> actualRepositories = truncateRepositories.entrySet().stream()
                .filter(entry -> !entry.getKey().equals("truncateRepositoryImpl"))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        // Truncate each table and reset its auto-increment counter
        for (Map.Entry<String, TruncateRepository> entry : actualRepositories.entrySet()) {
            String beanName = entry.getKey();
            TruncateRepository repository = entry.getValue();
            String tableName = extractTableNameFromBeanName(beanName);
            repository.truncateTable(tableName);
            repository.resetAutoIncrement(tableName);
        }
    }

    /**
     * Extracts the table name from the given repository bean name.
     *
     * @param beanName the name of the repository bean
     * @return the corresponding table name in snake_case
     */
    private String extractTableNameFromBeanName(String beanName) {
        // Assuming the bean name is in the format entityNameRepository (e.g., userRepository)
        String className = beanName.replace("Repository", "");
        return convertToSnakeCase(className);
    }

    /**
     * Converts a camelCase string to snake_case.
     *
     * @param camelCase the camelCase string to be converted
     * @return the converted snake_case string
     */
    private String convertToSnakeCase(String camelCase) {
        StringBuilder result = new StringBuilder();
        char[] chars = camelCase.toCharArray();
        result.append(Character.toLowerCase(chars[0]));
        for (int i = 1; i < chars.length; i++) {
            if (Character.isUpperCase(chars[i])) {
                result.append('_').append(Character.toLowerCase(chars[i]));
            } else {
                result.append(chars[i]);
            }
        }
        return result.toString();
    }
}
