package com.example.baseballscoresheet.logic;

import com.example.baseballscoresheet.model.entities.*;
import com.example.baseballscoresheet.repositories.PositionRepository;
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

import static com.fasterxml.jackson.databind.cfg.CoercionInputShape.Array;

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
    @Autowired
    private TeamService teamService;
    @Autowired
    private ClubService clubService;
    @Autowired
    private LineupService lineupService;
    @Autowired
    private LineupTeamPlayerService lineupTeamPlayerService;
    @Autowired
    private TeamPlayerService teamPlayerService;
    @Autowired
    private PositionRepository positionRepository;

    @BeforeEach
    public void setUp() {
        truncateAndResetTables();

        // Create positions
        List<String> descriptions = List.of("Pitcher", "Catcher", "First Base", "Second Base", "Third Base", "Short Stop", "Leftfielder", "Centerfielder", "Rightfielder", "Designated Hitter");
        List<PositionEntity> positions = createPositions(descriptions);

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

        // create club for home team
        ClubEntity clubHome = new ClubEntity();
        clubHome.setName("Example Club Home");
        clubHome.setAssociation(association);
        clubHome = clubService.createClub(clubHome);

        // create club for away team
        ClubEntity clubAway = new ClubEntity();
        clubAway.setName("Example Club Away");
        clubAway.setAssociation(association);
        clubAway = clubService.createClub(clubAway);

        // create teams
        TeamEntity teamHome = new TeamEntity();
        teamHome.setName("Example Team Home");
        teamHome.setClub(clubHome);
        teamHome.setLeague(league);
        teamHome = teamService.createTeam(teamHome);

        TeamEntity teamAway = new TeamEntity();
        teamAway.setName("Example Team Away");
        teamAway.setClub(clubAway);
        teamAway.setLeague(league);
        teamAway = teamService.createTeam(teamAway);

        // create game
        GameEntity game = new GameEntity();
        game.setGameNr(1);
        game.setInnings(9);
        game.setAssociation(association);
        game.setLeague(league);
        game.setGameState(gameState);
        game.setGuest(teamAway);
        game.setHost(teamHome);
        gameService.createGame(game);

        // create players for home team
        String[][] homeTeamPlayerNames = {
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
        List<PlayerEntity> homePlayers = createPlayers(homeTeamPlayerNames);

        // create players for away team
        String[][] awayTeamPlayerNames = {
                {"00", "Alex", "Stealer"},
                {"01", "Chris", "Bunt"},
                {"02", "Jamie", "Outfield"},
                {"03", "Morgan", "Shortstop"},
                {"04", "Pat", "Doubleplay"},
                {"05", "Taylor", "Lefty"},
                {"06", "Jordan", "Righty"},
                {"07", "Casey", "Triple"},
                {"08", "Drew", "Fielder"}
        };
        List<PlayerEntity> awayPlayers = createPlayers(awayTeamPlayerNames);

        LineupEntity lineupHome = new LineupEntity();
        lineupHome.setTeam(teamHome);
        lineupHome.setGame(game);
        lineupHome = lineupService.saveLineup(lineupHome);

        LineupEntity lineupAway = new LineupEntity();
        lineupAway.setTeam(teamAway);
        lineupAway.setGame(game);
        lineupAway = lineupService.saveLineup(lineupAway);

        // create team players for home
        List<TeamPlayerEntity> homeTeamPlayers = createTeamPlayers(homePlayers, teamHome);

        // create team players for away
        List<TeamPlayerEntity> awayTeamPlayers = createTeamPlayers(awayPlayers, teamAway);

        // create lineup team players for home
        List<LineupTeamPlayerEntity> homeLineupTeamPlayers = createLineupTeamPlayers(homeTeamPlayers, lineupHome, positions);

        // create lineup team players for away
        List<LineupTeamPlayerEntity> awayLineupTeamPlayers = createLineupTeamPlayers(awayTeamPlayers, lineupAway, positions);

        // create 1st inning
        InningEntity inning = createInning(game);

        // create first turn
        TurnEntity turn = new TurnEntity();
        turn.setInning(inning);
        turn.setLineupTeamPlayer(awayLineupTeamPlayers.get(0));
        turnService.createNewTurn(turn);
    }

    protected List<PlayerEntity> createPlayers(String[][] playerNames) {
        List<PlayerEntity> players = new ArrayList<>();

        for (String[] batterData : playerNames) {
            PlayerEntity player = new PlayerEntity();
            player.setPassnumber(Integer.parseInt(batterData[0]));
            player.setFirstName(batterData[1]);
            player.setLastName(batterData[2]);
            players.add(playerService.createPlayer(player));
        }
        return players;
    }

    protected List<TeamPlayerEntity> createTeamPlayers(List<PlayerEntity> players, TeamEntity team) {
        List<TeamPlayerEntity> teamPlayers = new ArrayList<>();
        for (PlayerEntity player : players) {
            TeamPlayerEntity teamPlayer = new TeamPlayerEntity();
            teamPlayer.setTeam(team);
            teamPlayer.setPlayer(player);
            teamPlayer = teamPlayerService.saveTeamPlayer(teamPlayer);
            teamPlayers.add(teamPlayer);
        }
        return teamPlayers;
    }

    protected List<LineupTeamPlayerEntity> createLineupTeamPlayers(List<TeamPlayerEntity> teamPlayers, LineupEntity lineup, List<PositionEntity> positions) {
        List<LineupTeamPlayerEntity> lineupTeamPlayers = new ArrayList<>();
        int position = 1;
        for (TeamPlayerEntity teamPlayer : teamPlayers) {
            LineupTeamPlayerEntity lineupTeamPlayer = new LineupTeamPlayerEntity();
            lineupTeamPlayer.setLineup(lineup);
            lineupTeamPlayer.setTeamPlayer(teamPlayer);
            lineupTeamPlayer.setJerseyNr(teamPlayer.getPlayer().getPassnumber());
            lineupTeamPlayer.setPosition(positions.get(position));
            lineupTeamPlayer = lineupTeamPlayerService.saveLineupTeamPlayerEntity(lineupTeamPlayer);
            lineupTeamPlayers.add(lineupTeamPlayer);
            position += 1;  // increase position counter
        }
        return lineupTeamPlayers;
    }

    protected InningEntity createInning(GameEntity game) {
        InningEntity inning = new InningEntity();
        inning.setGame(game);
        inning.setInning(1);
        inning.setOuts(0);
        inning.setBattingTeam(InningEntity.Team.AWAY);
        return inningService.create(inning);
    }

    protected List<PositionEntity> createPositions(List<String> descriptions) {
        long id = 1;
        List<PositionEntity> positions = new ArrayList<>();

        for (String description : descriptions) {
            PositionEntity positionEntity = new PositionEntity();
            positionEntity.setId(id);
            positionEntity.setDescription(description);
            positionEntity = positionRepository.save(positionEntity);
            positions.add(positionEntity);
            id += 1;
        }
        return positions;
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
