package com.vicentemartinez.user.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.vicentemartinez.user.model.UserEntity;
import com.vicentemartinez.user.repository.UserRepository;
import com.vicentemartinez.user.service.dto.UserDTO;
import com.vicentemartinez.user.util.ModelMapperUtil;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BCryptPasswordEncoder bCrytPaswordEncoder;
	
	@Autowired
	ModelMapperUtil modelMapper;

	@Override
	public UserDTO createUser(UserDTO userDetails) {
		userDetails.setUserId(UUID.randomUUID().toString());
		userDetails.setEncryptedPassword(bCrytPaswordEncoder.encode(userDetails.getPassword()));
		UserEntity user = modelMapper.mapToObject(userDetails, UserEntity.class);
		userRepository.save(user);
		UserDTO returnUserDTO = modelMapper.mapToObject(user, UserDTO.class);
		return returnUserDTO;
	}

}
