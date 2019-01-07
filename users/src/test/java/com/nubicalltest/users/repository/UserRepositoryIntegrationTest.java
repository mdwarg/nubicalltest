package com.nubicalltest.users.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.mysql.cj.util.TimeUtil;
import com.nubicalltest.users.fixture.UserFixture;
import com.nubicalltest.users.model.Status;
import com.nubicalltest.users.model.User;
import com.nubicalltest.users.repository.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryIntegrationTest {

	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void findUserByUsername() {
		//given
		User basicUser = UserFixture.createBasicUser();
		userRepository.save(basicUser);
		
		//when
		Optional<User> found = userRepository.findByUsername(basicUser.getUsername());
		
		//then
		assertThat(found.get().getId()).isEqualTo(basicUser.getId());
		assertThat(found.get().getUsername()).isEqualTo(basicUser.getUsername());
	}
	
	@Test
	public void findNonexistentUser() {
		//given
		User basicUser = UserFixture.createBasicUser();
		// Database without users
		
		//when
		Optional<User> found = userRepository.findByUsername(basicUser.getUsername());
		
		//then
		assertThat(!found.isPresent());
	}
}
