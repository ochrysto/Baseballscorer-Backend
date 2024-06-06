package com.example.baseballscoresheet.controllerTests;

import com.example.baseballscoresheet.model.entities.*;
import com.example.baseballscoresheet.model.entities.AssociationEntity;
import com.example.baseballscoresheet.model.entities.ClubEntity;
import com.example.baseballscoresheet.model.entities.LeagueEntity;
import com.example.baseballscoresheet.model.entities.TeamEntity;
import com.example.baseballscoresheet.testcontainers.AbstractIntegrationTest;

import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

public class TeamController_IT extends AbstractIntegrationTest {

	// TODO Sefa

	@Test
	void authorization() throws Exception {
		this.mockMvc.perform(get("/team"))
					.andExpect(status().isUnauthorized());
	}

	// TODO Test durchlaufen lassen
	@Test
	void createTeam() throws Exception {
		var association = new AssociationEntity();
		association.setId(1L);
		association.setName("TestAssociation");
		this.associationRepository.save(association);

		var league = new LeagueEntity();
		league.setId(1L);
		league.setName("TestLeague");
		league.setAssociation(association);
		this.leagueRepository.save(league);

		var club = new ClubEntity();
		club.setId(1L);
		club.setName("TestClub");
		club.setCity("TestCity");
		club.setClubLogo("TestLogo");
		club.setEmail("TestEmail");
		club.setAssociation(association);
		this.clubRepository.save(club);

		var manager = new ManagerEntity();
		manager.setId(1L);
		manager.setFirstName("TestManagerFirstname");
		manager.setLastName("TestManagerLastname");
		manager.setEmail("manager@gmail.com");
		this.managerRepository.save(manager);

		String content = """
						         {
						             "name": "TestTeam",
						             "managerId": "1",
						             "clubId": "1",
						             "leagueId": 1,
						             "teamLogo": "TestLogo"
						         }
						 """;

		this.mockMvc.perform(post("/team").content(content))
					.andExpect(status().isCreated())
					.andExpect(jsonPath("$.teamId", is("1")))
					.andExpect(jsonPath("$.name", is("TestTeam")))
					.andExpect(jsonPath("$.teamLogo", is("TestLogo")))
					.andExpect(jsonPath("$.club.name", is("TestClub")))
					.andExpect(jsonPath("$.club.city", is("TestCity")))
					.andExpect(jsonPath("$.club.logo", is("TestLogo")))
					.andExpect(jsonPath("$.club.email", is("TestEmail")))
					.andExpect(jsonPath("$.club.association.id", is("1")))
					.andExpect(jsonPath("$.club.association.name", is("TestAssociation")))
					.andExpect(jsonPath("$.manager.id", is("1")))
					.andExpect(jsonPath("$.manager.id", is("1")))
					.andExpect(jsonPath("$.manager.firstName", is("TestManagerFirstname")))
					.andExpect(jsonPath("$.manager.lastName", is("TestManagerLastname")))
					.andExpect(jsonPath("$.manager.email", is("manager@gmail.com")))
					.andExpect(jsonPath("$.league.id", is("1")))
					.andExpect(jsonPath("$.league.name", is("TestLeague")))
					.andExpect(jsonPath("$.league.association.id", is("1")))
					.andExpect(jsonPath("$.league.association.name", is("TestAssociation")))
					.andReturn()
					.getResponse()
					.getContentAsString();
	}

	// TODO Test durchlaufen lassen
	@Test
	@WithMockUser
	void findAllTeams() throws Exception {
		var association = new AssociationEntity();
		association.setId(1L);

		var league = new LeagueEntity();
		league.setName("Test League");
		league.setAssociation(association);

		var club = new ClubEntity();
		club.setName("Test Club");
		club.setAssociation(association);

		var team = new TeamEntity();
		team.setId(1L);
		team.setClub(club);
		team.setLeague(league);

		this.associationRepository.save(association);
		this.clubRepository.save(club);
		this.leagueRepository.save(league);
		this.teamRepository.save(team);

		this.mockMvc.perform(get("/team"))
					.andExpect(status().is2xxSuccessful())
					.andExpect(jsonPath("$", hasSize(1)))
					.andExpect(jsonPath("$[0].teamId", is(1)))
					.andExpect(jsonPath("$[0].club.name", is("Test Club")))
					.andExpect(jsonPath("$[0].league.id", is(1)))
					.andReturn()
					.getResponse()
					.getContentAsString();
	}

	// TODO Test durchlaufen lassen
	@Test
	void findTeamById() throws Exception {
		var association = new AssociationEntity();
		association.setId(1L);
		association.setName("TestAssociation");
		this.associationRepository.save(association);

		var league = new LeagueEntity();
		league.setId(1L);
		league.setName("TestLeague");
		league.setAssociation(association);
		this.leagueRepository.save(league);

		var club = new ClubEntity();
		club.setId(1L);
		club.setName("TestClub");
		club.setCity("TestCity");
		club.setClubLogo("TestLogo");
		club.setEmail("TestEmail");
		club.setAssociation(association);
		this.clubRepository.save(club);

		var manager = new ManagerEntity();
		manager.setId(1L);
		manager.setFirstName("TestManagerFirstname");
		manager.setLastName("TestManagerLastname");
		manager.setEmail("manager@gmail.com");
		this.managerRepository.save(manager);

		var team = new TeamEntity();
		team.setId(1L);
		team.setName("TestTeam");
		team.setManager(manager);
		team.setClub(club);
		team.setLeague(league);
		team.setTeamLogo("TestTeamLogo");
		this.teamRepository.save(team);

		this.mockMvc.perform(get("/team/1"))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.teamId", is("1")))
					.andExpect(jsonPath("$.name", is("TestTeam")))
					.andExpect(jsonPath("$.teamLogo", is("TestLogo")))
					.andExpect(jsonPath("$.club.name", is("TestClub")))
					.andExpect(jsonPath("$.club.city", is("TestCity")))
					.andExpect(jsonPath("$.club.logo", is("TestLogo")))
					.andExpect(jsonPath("$.club.email", is("TestEmail")))
					.andExpect(jsonPath("$.club.association.id", is("1")))
					.andExpect(jsonPath("$.club.association.name", is("TestAssociation")))
					.andExpect(jsonPath("$.manager.id", is("1")))
					.andExpect(jsonPath("$.manager.firstName", is("TestManagerFirstname")))
					.andExpect(jsonPath("$.manager.lastName", is("TestManagerLastname")))
					.andExpect(jsonPath("$.manager.email", is("manager@gmail.com")))
					.andExpect(jsonPath("$.league.id", is("1")))
					.andExpect(jsonPath("$.league.name", is("TestLeague")))
					.andExpect(jsonPath("$.league.association.id", is("1")))
					.andExpect(jsonPath("$.league.association.name", is("TestAssociation")))
					.andReturn()
					.getResponse()
					.getContentAsString();
	}

	// TODO Test durchlaufen lassen
	@Test
	void updateTeamInfo() throws Exception {
		var association = new AssociationEntity();
		association.setId(1L);
		association.setName("TestAssociation");
		this.associationRepository.save(association);

		var league = new LeagueEntity();
		league.setId(1L);
		league.setName("TestLeague");
		league.setAssociation(association);
		this.leagueRepository.save(league);

		var league2 = new LeagueEntity();
		league2.setId(2L);
		league2.setName("TestLeague2");
		league2.setAssociation(association);
		this.leagueRepository.save(league2);

		var club = new ClubEntity();
		club.setId(1L);
		club.setName("TestClub");
		club.setCity("TestCity");
		club.setClubLogo("TestLogo");
		club.setEmail("TestEmail");
		club.setAssociation(association);
		this.clubRepository.save(club);

		var club2 = new ClubEntity();
		club2.setId(2L);
		club2.setName("TestClub2");
		club2.setCity("TestCity2");
		club2.setClubLogo("TestLogo2");
		club2.setEmail("TestEmail2");
		club2.setAssociation(association);
		this.clubRepository.save(club2);

		var manager = new ManagerEntity();
		manager.setId(1L);
		manager.setFirstName("TestManagerFirstname");
		manager.setLastName("TestManagerLastname");
		manager.setEmail("manager@gmail.com");
		this.managerRepository.save(manager);

		var manager2 = new ManagerEntity();
		manager2.setId(2L);
		manager2.setFirstName("TestManagerFirstname2");
		manager2.setLastName("TestManagerLastname2");
		manager2.setEmail("manager@gmail.com2");
		this.managerRepository.save(manager2);

		var team = new TeamEntity();
		team.setId(1L);
		team.setName("TestTeam");
		team.setManager(manager);
		team.setClub(club);
		team.setLeague(league);
		team.setTeamLogo("TestTeamLogo");
		this.teamRepository.save(team);

		String content = """
						         {
						             "name": "UpdateTestTeam",
						             "managerId": "2",
						             "clubId": "2",
						             "leagueId": 2,
						             "teamLogo": "UpdateTestLogo"
						         }
						 """;

		this.mockMvc.perform(put("/team/1").content(content))
					.andExpect(status().isCreated())
					.andExpect(jsonPath("$.teamId", is("1")))
					.andExpect(jsonPath("$.name", is("UpdateTestTeam")))
					.andExpect(jsonPath("$.teamLogo", is("UpdateTestLogo")))
					.andExpect(jsonPath("$.club.name", is("TestClub2")))
					.andExpect(jsonPath("$.club.city", is("TestCity2")))
					.andExpect(jsonPath("$.club.logo", is("TestLogo2")))
					.andExpect(jsonPath("$.club.email", is("TestEmail2")))
					.andExpect(jsonPath("$.club.association.id", is("1")))
					.andExpect(jsonPath("$.club.association.name", is("TestAssociation")))
					.andExpect(jsonPath("$.manager.id", is("2")))
					.andExpect(jsonPath("$.manager.firstName", is("TestManagerFirstname2")))
					.andExpect(jsonPath("$.manager.lastName", is("TestManagerLastname2")))
					.andExpect(jsonPath("$.manager.email", is("manager@gmail.com2")))
					.andExpect(jsonPath("$.league.id", is("2")))
					.andExpect(jsonPath("$.league.name", is("TestLeague2")))
					.andExpect(jsonPath("$.league.association.id", is("1")))
					.andExpect(jsonPath("$.league.association.name", is("TestAssociation")))
					.andReturn()
					.getResponse()
					.getContentAsString();

	}

	// TODO Test durchlaufen lassen
	@Test
	void deleteTeamById() throws Exception {
		var association = new AssociationEntity();
		association.setId(1L);
		association.setName("TestAssociation");
		this.associationRepository.save(association);

		var league = new LeagueEntity();
		league.setId(1L);
		league.setName("TestLeague");
		league.setAssociation(association);
		this.leagueRepository.save(league);

		var club = new ClubEntity();
		club.setId(1L);
		club.setName("TestClub");
		club.setCity("TestCity");
		club.setClubLogo("TestLogo");
		club.setEmail("TestEmail");
		club.setAssociation(association);
		this.clubRepository.save(club);

		var manager = new ManagerEntity();
		manager.setId(1L);
		manager.setFirstName("TestManagerFirstname");
		manager.setLastName("TestManagerLastname");
		manager.setEmail("manager@gmail.com");
		this.managerRepository.save(manager);

		var team = new TeamEntity();
		team.setId(1L);
		team.setName("TestTeam");
		team.setManager(manager);
		team.setClub(club);
		team.setLeague(league);
		team.setTeamLogo("TestTeamLogo");
		this.teamRepository.save(team);

		this.mockMvc.perform(delete("/team/1"))
					.andExpect(status().isOk())
					.andReturn()
					.getResponse()
					.getContentAsString();
	}

	// TODO Test noch NICHT implementiert
	@Test
	void addPlayersToTeam() throws Exception {
		var association = new AssociationEntity();
		association.setId(1L);
		association.setName("TestAssociation");
		this.associationRepository.save(association);

		var league = new LeagueEntity();
		league.setId(1L);
		league.setName("TestLeague");
		league.setAssociation(association);
		this.leagueRepository.save(league);

		var club = new ClubEntity();
		club.setId(1L);
		club.setName("TestClub");
		club.setCity("TestCity");
		club.setClubLogo("TestLogo");
		club.setEmail("TestEmail");
		club.setAssociation(association);
		this.clubRepository.save(club);

		var manager = new ManagerEntity();
		manager.setId(1L);
		manager.setFirstName("TestManagerFirstname");
		manager.setLastName("TestManagerLastname");
		manager.setEmail("manager@gmail.com");
		this.managerRepository.save(manager);

		var team = new TeamEntity();
		team.setId(1L);
		team.setName("TestTeam");
		team.setManager(manager);
		team.setClub(club);
		team.setLeague(league);
		team.setTeamLogo("TestTeamLogo");
		this.teamRepository.save(team);

	}

	// TODO Test noch NICHT implementiert
	@Test
	void removePlayerFromTeam() throws Exception {
		var association = new AssociationEntity();
		association.setId(1L);

		var league = new LeagueEntity();
		league.setName("Test League");
		league.setAssociation(association);

		var club = new ClubEntity();
		club.setName("Test Club");
		club.setAssociation(association);

		var team = new TeamEntity();
		team.setId(1L);
		team.setClub(club);
		team.setLeague(league);

		this.associationRepository.save(association);
		this.clubRepository.save(club);
		this.leagueRepository.save(league);
		this.teamRepository.save(team);

	}

	// TODO Test durchlaufen lassen
	@Test
	void getAllPlayersFromTeam() throws Exception {
		var association = new AssociationEntity();
		association.setId(1L);
		this.associationRepository.save(association);

		var league = new LeagueEntity();
		league.setName("Test League");
		league.setAssociation(association);
		this.leagueRepository.save(league);

		var club = new ClubEntity();
		club.setName("Test Club");
		club.setAssociation(association);
		this.clubRepository.save(club);

		var manager = new ManagerEntity();
		manager.setId(1L);
		manager.setFirstName("TestManagerFirstname");
		manager.setLastName("TestManagerLastname");
		manager.setEmail("manager@gmail.com");
		this.managerRepository.save(manager);

		var team = new TeamEntity();
		team.setId(1L);
		team.setName("TestTeam");
		team.setManager(manager);
		team.setClub(club);
		team.setLeague(league);
		team.setTeamLogo("TestTeamLogo");
		this.teamRepository.save(team);

		var player1 = new PlayerEntity();
		player1.setId(1L);
		player1.setPassnumber(1);
		player1.setFirstName("TestPlayerFirstname1");
		player1.setLastName("TestPlayerLastname1");
		this.playerRepository.save(player1);

		var player2 = new PlayerEntity();
		player2.setId(2L);
		player2.setPassnumber(2);
		player2.setFirstName("TestPlayerFirstname2");
		player2.setLastName("TestPlayerLastname2");
		this.playerRepository.save(player2);

		var player3 = new PlayerEntity();
		player3.setId(3L);
		player3.setPassnumber(3);
		player3.setFirstName("TestPlayerFirstname3");
		player3.setLastName("TestPlayerLastname3");
		this.playerRepository.save(player3);

		var teamPlayer1 = new TeamPlayerEntity();
		teamPlayer1.setId(1L);
		teamPlayer1.setTeam(team);
		teamPlayer1.setPlayer(player1);
		this.teamPlayerRepository.save(teamPlayer1);

		var teamPlayer2 = new TeamPlayerEntity();
		teamPlayer2.setId(2L);
		teamPlayer2.setTeam(team);
		teamPlayer2.setPlayer(player2);
		this.teamPlayerRepository.save(teamPlayer2);

		var teamPlayer3 = new TeamPlayerEntity();
		teamPlayer3.setId(3L);
		teamPlayer3.setTeam(team);
		teamPlayer3.setPlayer(player3);
		this.teamPlayerRepository.save(teamPlayer3);

		Set<TeamPlayerEntity> teamPlayerSet = new HashSet<>();
		teamPlayerSet.add(teamPlayer1);
		teamPlayerSet.add(teamPlayer2);
		teamPlayerSet.add(teamPlayer3);

		team.setTeamplayer(teamPlayerSet);
		this.teamRepository.save(team);

		this.mockMvc.perform(get("/team/1/players"))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$[0].id", is("1")))
					.andExpect(jsonPath("$[0].firstName", is("TestPlayerFirstname1")))
					.andExpect(jsonPath("$[0].lastName", is("TestPlayerLastname1")))
					.andExpect(jsonPath("$[0].passnumber", is("1")))
					.andExpect(jsonPath("$[1].id", is("2")))
					.andExpect(jsonPath("$[1].firstName", is("TestPlayerFirstname2")))
					.andExpect(jsonPath("$[1].lastName", is("TestPlayerLastname2")))
					.andExpect(jsonPath("$[1].passnumber", is("2")))
					.andExpect(jsonPath("$[2].id", is("3")))
					.andExpect(jsonPath("$[2].firstName", is("TestPlayerFirstname3")))
					.andExpect(jsonPath("$[2].lastName", is("TestPlayerLastname3")))
					.andExpect(jsonPath("$[2].passnumber", is("3")))
					.andReturn()
					.getResponse()
					.getContentAsString();
	}
}
