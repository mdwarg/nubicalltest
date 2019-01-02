package com.nubicalltest.users.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nubicalltest.users.ResourceNotFoundException;
import com.nubicalltest.users.model.User;
import com.nubicalltest.users.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	UserRepository userRepository;

	/**
	 * Create new user
	 * 
	 * @param user
	 * @return created user
	 */
	@PostMapping("/users")
	public User createUser(@Valid @RequestBody User user) {
		return userRepository.save(user);
	}

	/**
	 * Retrieve the user by username
	 * @param username
	 * @return found user
	 */
	@GetMapping("/users/{username}")
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
