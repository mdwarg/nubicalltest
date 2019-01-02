package com.nubicalltest.users.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private UserController userController;

	@Test
	public void createUser() throws Exception {
	}
	
	@Test
	public void findUserByUsername() throws Exception {
	}
	
	@Test
	public void modifyUser() throws Exception {
	}
	
	@Test
	public void deleteUser() throws Exception {
	}
}
