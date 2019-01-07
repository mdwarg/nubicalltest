package com.nubicalltest.users;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.nubicalltest.users.controller.UserController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersApplicationTests {

	@Autowired
	private UserController userController;
	
	@Test
	public void contextLoads() {
		assertThat(userController).isNotNull();
	}

}

