package com.vicentemartinez.user.ui.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vicentemartinez.user.service.UserService;
import com.vicentemartinez.user.service.dto.UserDTO;
import com.vicentemartinez.user.ui.model.CreateUserRequestModel;
import com.vicentemartinez.user.ui.model.CreateUserResponseModel;
import com.vicentemartinez.user.ui.model.UserResponseModel;
import com.vicentemartinez.user.util.ModelMapperUtil;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private Environment env;

	@Autowired
	private UserService userService;

	@Autowired
	ModelMapperUtil modelMapper;

	@GetMapping("/status/check")
	public String status() {
		return "Working on: " + env.getProperty("local.server.port");
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<CreateUserResponseModel> createUser(@Valid @RequestBody CreateUserRequestModel userDetails) {
		UserDTO userDTO = modelMapper.mapToObject(userDetails, UserDTO.class);
		UserDTO createdUserDTO = userService.createUser(userDTO);
		CreateUserResponseModel createdUser = modelMapper.mapToObject(createdUserDTO, CreateUserResponseModel.class);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
	}

	@GetMapping(value = "/{userId}", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE, }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE, })
	public ResponseEntity<UserResponseModel> getUser(@PathVariable String userId) {
		UserDTO userDto = userService.getUserByUserId(userId);
		UserResponseModel userResponseModel = modelMapper.mapToObject(userDto, UserResponseModel.class);
		return ResponseEntity.status(HttpStatus.OK).body(userResponseModel);
	}
}
