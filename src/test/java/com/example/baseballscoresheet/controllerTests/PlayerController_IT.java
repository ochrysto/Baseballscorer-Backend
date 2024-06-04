package com.example.baseballscoresheet.controllerTests;

import com.example.baseballscoresheet.model.entities.PlayerEntity;
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

public class PlayerController_IT extends AbstractIntegrationTest {

	@Test
	void authorization() throws Exception {
		this.mockMvc.perform(get("/player"))
					.andExpect(status().isUnauthorized());
	}

	@Test
	@WithMockUser(roles = "user")
	void createPlayer() throws Exception {
		String content = """
						 		 {
						 		 	"firstName": "Firstname",
						 		 	"lastName": "Lastname",
						 		 	"passnumber": "456"
						 		 }
						 """;

		this.mockMvc.perform(post("/player").content(content))
					.andExpect(status().isCreated())
					.andExpect(jsonPath("$.id", is("1")))
					.andExpect(jsonPath("$.firstName", is("Firstname")))
					.andExpect(jsonPath("$.lastName", is("Lastname")))
					.andExpect(jsonPath("$.passnumber", is("456")))
					.andReturn()
					.getResponse()
					.getContentAsString();
	}

	// TODO Test durchlaufen lassen
	@Test
	@WithMockUser(roles = "user")
	void findAllPlayers() throws Exception {
		var player1 = new PlayerEntity();
		player1.setId(1L);
		player1.setFirstName("Test Player 1");
		player1.setLastName("Test Player 1");
		player1.setPassnumber(123);
		this.playerRepository.save(player1);

		var player2 = new PlayerEntity();
		player2.setId(2L);
		player2.setFirstName("Test Player 2");
		player2.setLastName("Test Player 2");
		player2.setPassnumber(456);
		this.playerRepository.save(player2);

		this.mockMvc.perform(get("/player"))
					.andExpect(status().is2xxSuccessful())
					.andExpect(jsonPath("$", hasSize(2)))
					.andExpect(jsonPath("$[0].id", is((1))))
					.andExpect(jsonPath("$[0].firstName", is("Test Player 1")))
					.andExpect(jsonPath("$[0].lastName", is("Test Player 1")))
					.andExpect(jsonPath("$[0].passnumber", is(123)))
					.andExpect(jsonPath("$[1].id", is(2)))
					.andExpect(jsonPath("$[1].firstName", is("Test Player 2")))
					.andExpect(jsonPath("$[1].lastName", is("Test Player 2")))
					.andExpect(jsonPath("$[1].passnumber", is(456)))
					.andReturn()
					.getResponse()
					.getContentAsString();
	}

	// TODO Test durchlaufen lassen
	@Test
	@WithMockUser(roles = "user")
	void findPlayerById() throws Exception {
		var player = new PlayerEntity();
		player.setId(1L);
		player.setFirstName("Test Player");
		player.setLastName("Test Player");
		player.setPassnumber(123);
		this.playerRepository.save(player);

		this.mockMvc.perform(get("/player//1"))
					.andExpect(status().is2xxSuccessful())
					.andExpect(jsonPath("id", is(1)))
					.andExpect(jsonPath("firstName", is("Test Player")))
					.andExpect(jsonPath("lastName", is("Test Player")))
					.andExpect(jsonPath("passnumber", is(123)))
					.andReturn()
					.getResponse()
					.getContentAsString();

	}

	// TODO Test durchlaufen lassen
	@Test
	@WithMockUser(roles = "user")
	void updatePlayer() throws Exception {
		var player = new PlayerEntity();
		player.setId(1L);
		player.setFirstName("Firstname");
		player.setLastName("Lastname");
		player.setPassnumber(123);
		this.playerRepository.save(player);

		String content = """
						 		 {
						 		 	"firstName": "New Firstname",
						 		 	"lastName": "New Lastname",
						 		 	"passnumber": "456"
						 		 }
						 """;

		this.mockMvc.perform(put("/player/1").content(content))
					.andExpect(status().is2xxSuccessful())
					.andExpect(jsonPath("id", is(1)))
					.andExpect(jsonPath("firstName", is("New Firstname")))
					.andExpect(jsonPath("lastName", is("New Lastname")))
					.andExpect(jsonPath("passnumber", is(456)))
					.andReturn()
					.getResponse()
					.getContentAsString();
	}

	// TODO Test durchlaufen lassen
	@Test
	@WithMockUser(roles = "user")
	void deletePlayerById() throws Exception {
		var player = new PlayerEntity();
		player.setId(1L);
		player.setFirstName("Test Player");
		player.setLastName("Test Player");
		player.setPassnumber(123);
		this.playerRepository.save(player);

		this.mockMvc.perform(delete("/player/1"))
					.andExpect(status().is2xxSuccessful())
					.andReturn()
					.getResponse()
					.getContentAsString();
	}
}
