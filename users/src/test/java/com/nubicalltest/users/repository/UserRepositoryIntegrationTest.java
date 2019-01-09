package com.nubicalltest.users.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.nubicalltest.users.fixture.UserFixture;
import com.nubicalltest.users.model.User;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryIntegrationTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void findUserByUsername() {
		User basicUser = UserFixture.createBasicUser();
		userRepository.save(basicUser);

		Optional<User> found = userRepository.findByUsername(basicUser.getUsername());

		assertThat(found.get().getId()).isEqualTo(basicUser.getId());
		assertThat(found.get().getUsername()).isEqualTo(basicUser.getUsername());
	}

	@Test
	public void findNonexistentUser() {
		User basicUser = UserFixture.createBasicUser();
		// Database without users

		Optional<User> found = userRepository.findByUsername(basicUser.getUsername());

		assertFalse(found.isPresent());
	}
}
