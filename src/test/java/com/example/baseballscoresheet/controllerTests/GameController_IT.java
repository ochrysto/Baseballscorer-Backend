package com.example.baseballscoresheet.controllerTests;

import com.example.baseballscoresheet.model.entities.*;
import com.example.baseballscoresheet.testcontainers.AbstractIntegrationTest;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// TODO ich

public class GameController_IT extends AbstractIntegrationTest {

	@Test
	void authorization() throws Exception {
		this.mockMvc.perform(get("/game")).andExpect(status().isUnauthorized());
	}

	// TODO Test durchlaufen lassen
	@Test
	@WithMockUser(roles = "user")
	void createGame() throws Exception {
		var association = new AssociationEntity();
		association.setId(1L);
		association.setName("TestAssociation");
		this.associationRepository.save(association);

		var league = new LeagueEntity();
		league.setId(1L);
		league.setName("TestLeague");
		league.setAssociation(association);
		this.leagueRepository.save(league);

		var hostTeam = new TeamEntity();
		hostTeam.setId(1L);
		hostTeam.setName("TestGuestTeam");
		this.teamRepository.save(hostTeam);

		var guestTeam = new TeamEntity();
		guestTeam.setId(2L);
		guestTeam.setName("TestGuestTeam");
		this.teamRepository.save(guestTeam);

		var umpire1 = new UmpireEntity();
		umpire1.setId(1L);
		umpire1.setPassnumber(123L);
		umpire1.setFirstName("TestUmpire1Firstname");
		umpire1.setLastName("TestUmpire1Lastname");
		this.umpireRepository.save(umpire1);

		var umpire2 = new UmpireEntity();
		umpire2.setId(2L);
		umpire2.setPassnumber(456L);
		umpire2.setFirstName("TestUmpire2Firstname");
		umpire2.setLastName("TestUmpire2Lastname");
		this.umpireRepository.save(umpire2);

		var scorer = new ScorerEntity();
		scorer.setId(1L);
		scorer.setPassnumber(123L);
		scorer.setFirstName("TestScorerFirstname");
		scorer.setLastName("TestScorerLastname");
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

		this.mockMvc.perform(post("/game").content(content))
					.andExpect(status().is2xxSuccessful())
					.andExpect(jsonPath("$.gameNr", is("1")))
					.andExpect(jsonPath("$.date", is("2024-05-21")))
					.andExpect(jsonPath("$.location", is("Bremen")))
					.andExpect(jsonPath("$.innings", is("9")))
					.andExpect(jsonPath("$.association.id", is("1")))
					.andExpect(jsonPath("$.association.name", is("TestAssociation")))
					.andExpect(jsonPath("$.league.id", is("1")))
					.andExpect(jsonPath("$.league.name", is("TestLeague")))
					.andExpect(jsonPath("$.league.association.id", is("1")))
					.andExpect(jsonPath("$.league.association.name", is("TestAssociation")))
					.andExpect(jsonPath("$.umpireList[0].umpireId", is("1")))
					.andExpect(jsonPath("$.umpireList[0].passnumber", is("123")))
					.andExpect(jsonPath("$.umpireList[0].name", is("TestUmpire1Firstname TestUmpire1Lastname")))
					.andExpect(jsonPath("$.umpireList[1].umpireId", is("2")))
					.andExpect(jsonPath("$.umpireList[1].passnumber", is("456")))
					.andExpect(jsonPath("$.umpireList[1].name", is("TestUmpire2Firstname TestUmpire2Lastname")))
					.andExpect(jsonPath("$.scorer.scorerId", is("1")))
					.andExpect(jsonPath("$.scorer.passnumber", is("123")))
					.andExpect(jsonPath("$.scorer.name", is("TestScorerFirstname TestScorerLastname")))
					.andReturn()
					.getResponse()
					.getContentAsString();
	}

	// TODO Test noch NICHT FERTIG implementiert
	@Test
	@Disabled(value = "Test is not yet fixed")
	@WithMockUser(roles = "user")
	void finishGame() throws Exception {
		var association = new AssociationEntity();
		association.setId(1L);
		association.setName("TestAssociation");
		this.associationRepository.save(association);

		var league = new LeagueEntity();
		league.setId(1L);
		league.setName("TestLeague");
		league.setAssociation(association);
		this.leagueRepository.save(league);

		var hostTeam = new TeamEntity();
		hostTeam.setId(1L);
		hostTeam.setName("TestHostTeam");
//		hostTeam.setManager(manager);
//		hostTeam.setClub(club);
		hostTeam.setLeague(league);
		hostTeam.setTeamLogo("TestTeamLogo");
//		this.teamRepository.save(team);

		var guestTeam = new TeamEntity();
		guestTeam.setId(1L);
		guestTeam.setName("TestGuestTeam");
//		guestTeam.setManager(manager);
//		guestTeam.setClub(club);
		guestTeam.setLeague(league);
		guestTeam.setTeamLogo("TestTeamLogo");
//		this.teamRepository.save(team);

		var umpire1 = new UmpireEntity();
		umpire1.setId(1L);
		umpire1.setPassnumber(123L);
		umpire1.setFirstName("TestUmpire1Firstname");
		umpire1.setLastName("TestUmpire1Lastname");
		this.umpireRepository.save(umpire1);

		var umpire2 = new UmpireEntity();
		umpire2.setId(2L);
		umpire2.setPassnumber(456L);
		umpire2.setFirstName("TestUmpire2Firstname");
		umpire2.setLastName("TestUmpire2Lastname");
		this.umpireRepository.save(umpire2);

		var scorer = new ScorerEntity();
		scorer.setId(1L);
		scorer.setPassnumber(123L);
		scorer.setFirstName("TestScorerFirstname");
		scorer.setLastName("TestScorerLastname");
		this.scorerRepository.save(scorer);

		var hostLineup = new LineupEntity();
		hostLineup.setId(1L);
		hostLineup.setTeam(hostTeam);

		var guestLineup = new LineupEntity();
		guestLineup.setId(1L);
		guestLineup.setTeam(guestTeam);

		var game = new GameEntity();
		game.setId(1L);
		game.setGameNr(1);
		game.setDate(LocalDate.of(2024, 05, 21));
		game.setLocation("Bremen");
		game.setInnings(9);
		game.setScorer(scorer);
		game.setAssociation(association);
//		game.setHost(hostLineup);
//		game.setGuest(guestLineup);
		game.setLeague(league);
		this.gameRepository.save(game);

		var gameUmpire1 = new GameUmpireEntity();
		gameUmpire1.setId(1L);
		gameUmpire1.setGame(game);
		gameUmpire1.setUmpire(umpire1);
		this.gameUmpireRepository.save(gameUmpire1);

		var gameUmpire2 = new GameUmpireEntity();
		gameUmpire2.setId(2L);
		gameUmpire2.setGame(game);
		gameUmpire2.setUmpire(umpire2);
		this.gameUmpireRepository.save(gameUmpire2);

		Set<GameUmpireEntity> gameUmpireList = new HashSet<>();
		gameUmpireList.add(gameUmpire1);
		gameUmpireList.add(gameUmpire2);

		game.setGameUmpire(gameUmpireList);
		this.gameRepository.save(game);

		String content = """
						         {
						             "innings": 9,
						             "attendance": 15000,
						             "startTime": "14:30:00.000000",
						             "endTime": "17:00:00.000000",
						             "durationInMinutes": 150
						         }
						 """;

		final String response = this.mockMvc.perform(post("/game/1").content(content))
											.andExpect(status().isCreated())
											.andExpect(jsonPath("$.gameNr", is("1")))
											.andExpect(jsonPath("$.date", is("2024-05-21")))
											.andExpect(jsonPath("$.location", is("Bremen")))
											.andExpect(jsonPath("$.innings", is("9")))

											.andExpect(jsonPath("$.association.id", is("1")))
											.andExpect(jsonPath("$.association.name", is("TestAssociation")))

											.andExpect(jsonPath("$.league.name", is("1")))
											.andExpect(jsonPath("$.league.id", is("TestLeague")))
											.andExpect(jsonPath("$.league.association.id", is("1")))
											.andExpect(jsonPath("$.league.association.name", is("TestAssociation")))

											.andExpect(jsonPath("$.hostTeam.teamId", is("1")))
											.andExpect(jsonPath("$.hostTeam.name", is("1")))
											.andExpect(jsonPath("$.hostTeam.club.name", is("1")))
											.andExpect(jsonPath("$.hostTeam.club.city", is("1")))
											.andExpect(jsonPath("$.hostTeam.club.logo", is("1")))
											.andExpect(jsonPath("$.hostTeam.club.email", is("1")))
											.andExpect(jsonPath("$.hostTeam.club.association", is("1")))
											.andExpect(jsonPath("$.hostTeam.manager.id", is("1")))
											.andExpect(jsonPath("$.hostTeam.manager.firstName", is("1")))
											.andExpect(jsonPath("$.hostTeam.manager.lastName", is("1")))
											.andExpect(jsonPath("$.hostTeam.manager.email", is("1")))
											.andExpect(jsonPath("$.hostTeam.league.id", is("1")))
											.andExpect(jsonPath("$.hostTeam.league.name", is("1")))
											.andExpect(jsonPath("$.hostTeam.league.association", is("1")))
											.andExpect(jsonPath("$.hostTeam.teamLogo", is("1")))

											.andExpect(jsonPath("$.guestTeam.teamId", is("1")))
											.andExpect(jsonPath("$.guestTeam.name", is("1")))
											.andExpect(jsonPath("$.guestTeam.club.name", is("1")))
											.andExpect(jsonPath("$.guestTeam.club.city", is("1")))
											.andExpect(jsonPath("$.guestTeam.club.logo", is("1")))
											.andExpect(jsonPath("$.guestTeam.club.email", is("1")))
											.andExpect(jsonPath("$.guestTeam.club.association", is("1")))
											.andExpect(jsonPath("$.guestTeam.manager.id", is("1")))
											.andExpect(jsonPath("$.guestTeam.manager.firstName", is("1")))
											.andExpect(jsonPath("$.guestTeam.manager.lastName", is("1")))
											.andExpect(jsonPath("$.guestTeam.manager.email", is("1")))
											.andExpect(jsonPath("$.guestTeam.league.id", is("1")))
											.andExpect(jsonPath("$.guestTeam.league.name", is("1")))
											.andExpect(jsonPath("$.guestTeam.league.association", is("1")))
											.andExpect(jsonPath("$.guestTeam.teamLogo", is("1")))

											.andExpect(jsonPath("$.umpireList[0].umpireId", is("1")))
											.andExpect(jsonPath("$.umpireList[0].passnumber", is("1")))
											.andExpect(jsonPath("$.umpireList[0].name", is("1")))

											.andExpect(jsonPath("$.umpireList[1].umpireId", is("1")))
											.andExpect(jsonPath("$.umpireList[1].passnumber", is("1")))
											.andExpect(jsonPath("$.umpireList[1].name", is("1")))

											.andExpect(jsonPath("$.scorer.scorerId", is("1")))
											.andExpect(jsonPath("$.scorer.passnumber", is("1")))
											.andExpect(jsonPath("$.scorer.name", is("1")))

											.andExpect(jsonPath("$.attendance", is("15000")))
											// TODO TIME
											.andExpect(jsonPath("$.startTime", is("1")))
											.andExpect(jsonPath("$.endTime", is("1")))
											.andExpect(jsonPath("$.durationInMinutes", is("150")))
											.andReturn()
											.getResponse()
											.getContentAsString();
	}

	// TODO Test noch NICHT FERTIG implementiert
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
