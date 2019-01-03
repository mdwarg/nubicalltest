package com.nubicalltest.users.controller;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nubicalltest.users.exception.ResourceNotFoundException;
import com.nubicalltest.users.model.User;
import com.nubicalltest.users.repository.UserRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Scope;

@RestController
@RequestMapping("/api")
@Api(value="User API" )
public class UserController {

	@Autowired
	UserRepository userRepository;

	/**
	 * Create new user
	 * @param user
	 * @return
	 */
	@PostMapping("/users")
	@ApiOperation(value = "Create New User")
	public ResponseEntity<String> createUser(@Valid @RequestBody User user) {
		userRepository.save(user);
		return new ResponseEntity<String>("User Created", HttpStatus.CREATED);
	}

	/**
	 * Retrieve the user by username
	 * @param username
	 * @return found user
	 */
	@GetMapping("/users/{username}")
	@ApiOperation(value = "Create New User", response = User.class)
	public User findUserByUsername(@PathVariable(value = "username") String username) {
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
	}

	/**
	 * Updates all the properties of the user sent
	 * @param username
	 * @param modifiedUser
	 * @return updatedUser
	 */
	@PutMapping("/users/{username}")
	public User modifyUser(@PathVariable(value = "username") String username, @Valid @RequestBody User modifiedUser) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
		
		user.setUsername(modifiedUser.getUsername());
		user.setFirstName(modifiedUser.getFirstName());
		user.setLastName(modifiedUser.getLastName());
		user.setEmail(modifiedUser.getEmail());
		user.setPassword(modifiedUser.getPassword());
		user.setPhone(modifiedUser.getPhone());
		user.setStatus(modifiedUser.getStatus());
		
		return userRepository.save(user);
	}

	@DeleteMapping("/users/{username}")
	public ResponseEntity<?> deleteUser(@PathVariable(value = "username") String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
		
		userRepository.delete(user);
		
		return ResponseEntity.ok().build();
	}
}
