package com.nubicalltest.users.fixture;

import com.nubicalltest.users.model.Status;
import com.nubicalltest.users.model.User;

public class UserFixture {
	public static User createBasicUser() {
		User user = new User();
		user.setUsername("Nubicall");
		user.setFirstName("Nubi");
		user.setLastName("Call");
		user.setEmail("nubicall@example.com");
		user.setPassword("P@sswo0rd");
		user.setPhone("1111-2222");
		user.setStatus(Status.Active);
		return user;
	}

}
