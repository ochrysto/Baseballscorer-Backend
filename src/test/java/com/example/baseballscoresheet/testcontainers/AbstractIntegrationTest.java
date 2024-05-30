package com.example.baseballscoresheet.testcontainers;

import com.example.baseballscoresheet.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

/**
 * A fast slice test will only start jpa context.
 * <p>
 * To use other context beans use org.springframework.context.annotation.@Import annotation.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("it")
@ContextConfiguration(initializers = PostgresContextInitializer.class)
public class AbstractIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected AssociationRepository associationRepository;

    @Autowired
    protected ClubRepository clubRepository;

    @Autowired
    protected GameRepository gameRepository;

    @Autowired
    protected GameUmpireRepository gameUmpireRepository;

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
    protected UmpireRepository umpireRepository;

    @BeforeEach
    void setUp() {
        associationRepository.deleteAll();
        clubRepository.deleteAll();
        gameRepository.deleteAll();
        gameUmpireRepository.deleteAll();
        leagueRepository.deleteAll();
        lineupRepository.deleteAll();
        lineupTeamPlayerRepository.deleteAll();
        managerRepository.deleteAll();
        playerRepository.deleteAll();
        positionRepository.deleteAll();
        scorerRepository.deleteAll();
        teamPlayerRepository.deleteAll();
        teamRepository.deleteAll();
        umpireRepository.deleteAll();
    }
}
