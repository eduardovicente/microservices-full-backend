package com.vicentemartinez.user.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.vicentemartinez.user.service.dto.UserDTO;

public interface UserService extends UserDetailsService{
	UserDTO createUser (UserDTO userDetails);
	UserDTO getUserDetailsByEmail (String email);
	UserDTO getUserByUserId(String userId);
}
