package com.pramod.file.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pramod.file.entity.LoginForm;
import com.pramod.file.entity.UserEntity;
import com.pramod.file.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/msg")
	public String getMessage(){
		return "Hello Welcome...!";
	}

	@PostMapping("/create")
	public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity user) {

		UserEntity createUser = userService.createUser(user);
		if (createUser != null) {
			return new ResponseEntity<>(createUser, HttpStatus.CREATED);
		}
		return new ResponseEntity<>(createUser, HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/login")
	public ResponseEntity<UserEntity> login(@RequestBody LoginForm login) {
		System.out.println(login.getEmail());
		System.out.println(login.getPazz());
		UserEntity user = userService.login(login);
		if (user != null) {
			return new ResponseEntity<>(user, HttpStatus.OK);

		}
		return new ResponseEntity<>(user, HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/userId/{userId}")
	public ResponseEntity<UserEntity> getByUserId(@PathVariable Long userId) {
		UserEntity byUserId = userService.getByUserId(userId);
		if (byUserId != null) {

			return new ResponseEntity<>(byUserId, HttpStatus.OK);

		}
		return new ResponseEntity<>(byUserId, HttpStatus.NOT_FOUND);

	}

}
