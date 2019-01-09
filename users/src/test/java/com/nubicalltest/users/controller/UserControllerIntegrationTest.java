package com.nubicalltest.users.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.apache.commons.lang.RandomStringUtils;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.nubicalltest.users.fixture.UserFixture;
import com.nubicalltest.users.model.User;
import com.nubicalltest.users.repository.UserRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private UserRepository userRepository;

	@Test
	public void createUser() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/users/").contentType(MediaType.APPLICATION_JSON)
				.content(UserFixture.jsonUser().toString())).andExpect(status().isCreated());

		verify(userRepository, times(1)).save(any(User.class));
	}

	@Test
	public void createUserWithoutBody() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/users/").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());

		verify(userRepository, times(0)).save(any(User.class));
	}

	@Test
	public void createUserInvalidJson() throws Exception {
		JSONObject jsonInvalidUser = UserFixture.jsonUser();
		jsonInvalidUser.put("username", "");
		mvc.perform(MockMvcRequestBuilders.post("/users/").contentType(MediaType.APPLICATION_JSON)
				.content(jsonInvalidUser.toString())).andExpect(status().isBadRequest());

		verify(userRepository, times(0)).save(any(User.class));
	}

	@Test
	public void findUserByUsername() throws Exception {
		User basicUser = UserFixture.createBasicUser();
		when(userRepository.findByUsername(basicUser.getUsername())).thenReturn(Optional.of(basicUser));

		mvc.perform(MockMvcRequestBuilders.get("/users/" + basicUser.getUsername())).andExpect(status().isOk());

		verify(userRepository, times(1)).findByUsername(basicUser.getUsername());
	}

	@Test
	public void findNonExistentUserByUsername() throws Exception {
		String username = RandomStringUtils.randomAlphabetic(8);

		mvc.perform(MockMvcRequestBuilders.get("/users/" + username)).andExpect(status().isNotFound());

		verify(userRepository, times(1)).findByUsername(username);
	}

	@Test
	public void modifyUser() throws Exception {
		User basicUser = UserFixture.createBasicUser();
		when(userRepository.findByUsername(basicUser.getUsername())).thenReturn(Optional.of(basicUser));

		mvc.perform(MockMvcRequestBuilders.put("/users/" + basicUser.getUsername())
				.contentType(MediaType.APPLICATION_JSON).content(UserFixture.jsonUser().toString()))
				.andExpect(status().isOk());

		verify(userRepository, times(1)).save(any(User.class));
	}

	@Test
	public void modifyNonexistentUser() throws Exception {
		String username = RandomStringUtils.randomAlphabetic(8);

		mvc.perform(MockMvcRequestBuilders.put("/users/" + username).contentType(MediaType.APPLICATION_JSON)
				.content(UserFixture.jsonUser().toString())).andExpect(status().isNotFound());

		verify(userRepository, times(1)).findByUsername(username);
	}

	@Test
	public void modifyUserInvalidData() throws Exception {
		User basicUser = UserFixture.createBasicUser();
		when(userRepository.findByUsername(basicUser.getUsername())).thenReturn(Optional.of(basicUser));
		JSONObject jsonInvalidUser = UserFixture.jsonUser();
		jsonInvalidUser.put("username", "");

		mvc.perform(MockMvcRequestBuilders.put("/users/" + basicUser.getUsername())
				.contentType(MediaType.APPLICATION_JSON).content(jsonInvalidUser.toString()))
				.andExpect(status().isBadRequest());

		verify(userRepository, times(0)).save(any(User.class));
	}

	@Test
	public void deleteUser() throws Exception {
		User basicUser = UserFixture.createBasicUser();
		when(userRepository.findByUsername(basicUser.getUsername())).thenReturn(Optional.of(basicUser));

		mvc.perform(MockMvcRequestBuilders.delete("/users/" + basicUser.getUsername())).andExpect(status().isOk());

		verify(userRepository, times(1)).delete(any(User.class));
	}

	@Test
	public void deleteNonExixtentUser() throws Exception {
		String username = RandomStringUtils.randomAlphabetic(8);

		mvc.perform(MockMvcRequestBuilders.delete("/users/" + username)).andExpect(status().isNotFound());

		verify(userRepository, times(1)).findByUsername(username);
		verify(userRepository, times(0)).delete(any(User.class));
	}
}
