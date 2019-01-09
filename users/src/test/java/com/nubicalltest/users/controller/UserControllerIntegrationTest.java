package com.nubicalltest.users.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.apache.commons.lang.RandomStringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.nubicalltest.users.UsersApplication;
import com.nubicalltest.users.fixture.UserFixture;
import com.nubicalltest.users.model.User;
import com.nubicalltest.users.repository.UserRepository;

@RunWith(SpringRunner.class)
//@WebMvcTest(UserController.class)
@SpringBootTest(classes = UsersApplication.class)
@PropertySource("classpath:config.properties")
public class UserControllerIntegrationTest {

	@Value("${http.auth-token-header-name}")
	private String principalRequestHeader;

	@Value("${http.auth-token}")
	private String principalRequestValue;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private FilterChainProxy springSecurityFilterChain;

	private MockMvc mvc;

	@MockBean
	private UserRepository userRepository;

	@Before
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(context).addFilter(springSecurityFilterChain).build();
	}

	@Test
	public void createUser() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/users/").contentType(MediaType.APPLICATION_JSON)
				.header(principalRequestHeader, principalRequestValue).content(UserFixture.jsonUser().toString()))
				.andExpect(status().isCreated());

		verify(userRepository, times(1)).save(any(User.class));
	}

	@Test
	public void createDuplicateUser() throws Exception {
		when(userRepository.save(any(User.class))).thenThrow(ConstraintViolationException.class);

		mvc.perform(MockMvcRequestBuilders.post("/users/").contentType(MediaType.APPLICATION_JSON)
				.header(principalRequestHeader, principalRequestValue).content(UserFixture.jsonUser().toString()))
				.andExpect(status().isBadRequest());

		verify(userRepository, times(1)).save(any(User.class));
	}

	@Test
	public void createUserWithoutBody() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/users/").contentType(MediaType.APPLICATION_JSON)
				.header(principalRequestHeader, principalRequestValue)).andExpect(status().isBadRequest());

		verify(userRepository, times(0)).save(any(User.class));
	}

	@Test
	public void createUserInvalidJson() throws Exception {
		JSONObject jsonInvalidUser = UserFixture.jsonUser();
		jsonInvalidUser.put("username", "");
		mvc.perform(MockMvcRequestBuilders.post("/users/").contentType(MediaType.APPLICATION_JSON)
				.header(principalRequestHeader, principalRequestValue).content(jsonInvalidUser.toString()))
				.andExpect(status().isBadRequest());

		verify(userRepository, times(0)).save(any(User.class));
	}

	@Test
	public void createUserUnauthorized() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/users/").contentType(MediaType.APPLICATION_JSON)
				.content(UserFixture.jsonUser().toString())).andExpect(status().isForbidden());

		verify(userRepository, times(0)).save(any(User.class));
	}

	@Test
	public void findUserByUsername() throws Exception {
		User basicUser = UserFixture.createBasicUser();
		when(userRepository.findByUsername(basicUser.getUsername())).thenReturn(Optional.of(basicUser));

		mvc.perform(MockMvcRequestBuilders.get("/users/" + basicUser.getUsername())
				.contentType(MediaType.APPLICATION_JSON).header(principalRequestHeader, principalRequestValue))
				.andExpect(status().isOk());

		verify(userRepository, times(1)).findByUsername(basicUser.getUsername());
	}

	@Test
	public void findNonExistentUserByUsername() throws Exception {
		String username = RandomStringUtils.randomAlphabetic(8);

		mvc.perform(MockMvcRequestBuilders.get("/users/" + username).contentType(MediaType.APPLICATION_JSON)
				.header(principalRequestHeader, principalRequestValue)).andExpect(status().isNotFound());

		verify(userRepository, times(1)).findByUsername(username);
	}

	@Test
	public void findUserByUsernameUnauthorized() throws Exception {
		User basicUser = UserFixture.createBasicUser();
		when(userRepository.findByUsername(basicUser.getUsername())).thenReturn(Optional.of(basicUser));

		mvc.perform(
				MockMvcRequestBuilders.get("/users/" + basicUser.getUsername()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden());

		verify(userRepository, times(0)).findByUsername(basicUser.getUsername());
	}

	@Test
	public void modifyUser() throws Exception {
		User basicUser = UserFixture.createBasicUser();
		when(userRepository.findByUsername(basicUser.getUsername())).thenReturn(Optional.of(basicUser));

		mvc.perform(MockMvcRequestBuilders.put("/users/" + basicUser.getUsername())
				.contentType(MediaType.APPLICATION_JSON).header(principalRequestHeader, principalRequestValue)
				.content(UserFixture.jsonUser().toString())).andExpect(status().isOk());

		verify(userRepository, times(1)).save(any(User.class));
	}

	@Test
	public void modifyNonexistentUser() throws Exception {
		String username = RandomStringUtils.randomAlphabetic(8);

		mvc.perform(MockMvcRequestBuilders.put("/users/" + username).contentType(MediaType.APPLICATION_JSON)
				.header(principalRequestHeader, principalRequestValue).content(UserFixture.jsonUser().toString()))
				.andExpect(status().isNotFound());

		verify(userRepository, times(1)).findByUsername(username);
	}

	@Test
	public void modifyUserInvalidData() throws Exception {
		User basicUser = UserFixture.createBasicUser();
		when(userRepository.findByUsername(basicUser.getUsername())).thenReturn(Optional.of(basicUser));
		JSONObject jsonInvalidUser = UserFixture.jsonUser();
		jsonInvalidUser.put("username", "");

		mvc.perform(
				MockMvcRequestBuilders.put("/users/" + basicUser.getUsername()).contentType(MediaType.APPLICATION_JSON)
						.header(principalRequestHeader, principalRequestValue).content(jsonInvalidUser.toString()))
				.andExpect(status().isBadRequest());

		verify(userRepository, times(0)).save(any(User.class));
	}

	@Test
	public void modifyUserUnauthorized() throws Exception {
		User basicUser = UserFixture.createBasicUser();
		when(userRepository.findByUsername(basicUser.getUsername())).thenReturn(Optional.of(basicUser));

		mvc.perform(MockMvcRequestBuilders.put("/users/" + basicUser.getUsername())
				.contentType(MediaType.APPLICATION_JSON).content(UserFixture.jsonUser().toString()))
				.andExpect(status().isForbidden());

		verify(userRepository, times(0)).save(any(User.class));
	}

	@Test
	public void deleteUser() throws Exception {
		User basicUser = UserFixture.createBasicUser();
		when(userRepository.findByUsername(basicUser.getUsername())).thenReturn(Optional.of(basicUser));

		mvc.perform(MockMvcRequestBuilders.delete("/users/" + basicUser.getUsername())
				.contentType(MediaType.APPLICATION_JSON).header(principalRequestHeader, principalRequestValue))
				.andExpect(status().isOk());

		verify(userRepository, times(1)).delete(any(User.class));
	}

	@Test
	public void deleteNonExistentUser() throws Exception {
		String username = RandomStringUtils.randomAlphabetic(8);

		mvc.perform(MockMvcRequestBuilders.delete("/users/" + username).contentType(MediaType.APPLICATION_JSON)
				.header(principalRequestHeader, principalRequestValue)).andExpect(status().isNotFound());

		verify(userRepository, times(1)).findByUsername(username);
		verify(userRepository, times(0)).delete(any(User.class));
	}

	@Test
	public void deleteUserUnauthorized() throws Exception {
		User basicUser = UserFixture.createBasicUser();
		when(userRepository.findByUsername(basicUser.getUsername())).thenReturn(Optional.of(basicUser));

		mvc.perform(MockMvcRequestBuilders.delete("/users/" + basicUser.getUsername())
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());

		verify(userRepository, times(0)).delete(any(User.class));
	}

}
