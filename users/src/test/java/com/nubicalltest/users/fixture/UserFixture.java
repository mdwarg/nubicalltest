package com.nubicalltest.users.fixture;

import org.json.JSONObject;

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

	public static JSONObject jsonUser() throws Exception {
		JSONObject jsonUser = new JSONObject();
		jsonUser.put("username", "Nubicall");
		jsonUser.put("firstName", "Nubi");
		jsonUser.put("lastName", "Call");
		jsonUser.put("email", "nubicall@example.com");
		jsonUser.put("password", "P@ssw0rd");
		jsonUser.put("phone", "1111-2222");
		jsonUser.put("status", "Active");
		return jsonUser;
	}

}
