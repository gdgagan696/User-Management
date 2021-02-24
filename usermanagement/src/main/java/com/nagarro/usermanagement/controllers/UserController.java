package com.nagarro.usermanagement.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.usermanagement.dto.AppUserDto;
import com.nagarro.usermanagement.dto.ResponseDto;
import com.nagarro.usermanagement.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@GetMapping("/getUser/{userName}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<AppUserDto> getUserInfo(@PathVariable String userName) {
		LOG.debug("Inside getUserInfo method,userName:{}", userName);
		return new ResponseEntity<>(userService.getUserDto(userName), HttpStatus.OK);
	}

	@PostMapping("/login")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<ResponseDto> logInUser(@RequestBody AppUserDto appUserDto) {
		LOG.debug("Inside logInUser method,appUser:{}", appUserDto);
		return new ResponseEntity<>(new ResponseDto(userService.authenticateUser(appUserDto)), HttpStatus.OK);
	}

	@GetMapping("/userByUserType/{userType}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<AppUserDto>> getUserByUserType(@PathVariable String userType) {
		LOG.debug("Inside getUserByUserType method,userType:{}", userType);
		return new ResponseEntity<>(userService.getUserByUserType(userType), HttpStatus.OK);
	}

}
