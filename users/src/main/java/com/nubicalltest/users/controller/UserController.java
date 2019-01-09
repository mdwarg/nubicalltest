package com.nubicalltest.users.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nubicalltest.users.exception.ResourceNotFoundException;
import com.nubicalltest.users.model.User;
import com.nubicalltest.users.repository.UserRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/users")
@Api(value = "User API", tags = { "users" })
public class UserController {

	@Autowired
	UserRepository userRepository;

	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	/**
	 * Create new user
	 * 
	 * @param user
	 * @return
	 */
	@PostMapping("/")
	@ApiOperation(value = "Create New User")
	@ResponseStatus(HttpStatus.CREATED)
	public void createUser(@Valid @RequestBody User user) {
		log.info("Create user: " + user.toString());
		userRepository.save(user);
	}

	/**
	 * Retrieve the user by username
	 * 
	 * @param username
	 * @return found user
	 */
	@GetMapping("/{username}")
	@ApiOperation(value = "Get User")
	@ResponseBody
	public User findUserByUsername(@PathVariable(value = "username") String username) {
		log.info("Find by username: " + username);
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
	}

	/**
	 * Updates all the properties of the user sent
	 * 
	 * @param username
	 * @param modifiedUser
	 * @return updatedUser
	 */
	@PutMapping("/{username}")
	@ApiOperation(value = "Modify Complete User")
	@ResponseStatus(HttpStatus.OK)
	public void modifyUser(@PathVariable(value = "username") String username, @Valid @RequestBody User modifiedUser) {
		log.info("Modify user: " + username + " with data: " + modifiedUser.toString());
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

		user.setUsername(modifiedUser.getUsername());
		user.setFirstName(modifiedUser.getFirstName());
		user.setLastName(modifiedUser.getLastName());
		user.setEmail(modifiedUser.getEmail());
		user.setPassword(modifiedUser.getPassword());
		user.setPhone(modifiedUser.getPhone());
		user.setStatus(modifiedUser.getStatus());

		userRepository.save(user);
	}

	@DeleteMapping("/{username}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Delete User")
	public void deleteUser(@PathVariable(value = "username") String username) {
		log.info("Delete user: " + username);
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

		userRepository.delete(user);
	}
}
