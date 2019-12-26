package com.vicentemartinez.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.vicentemartinez.user.client.AlbumsServiceClient;
import com.vicentemartinez.user.model.UserEntity;
import com.vicentemartinez.user.repository.UserRepository;
import com.vicentemartinez.user.service.dto.UserDTO;
import com.vicentemartinez.user.ui.model.AlbumResponseModel;
import com.vicentemartinez.user.util.ModelMapperUtil;

import feign.FeignException;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCrytPaswordEncoder;

	@Autowired
	private ModelMapperUtil modelMapper;

	@Autowired
	private AlbumsServiceClient albumsServiceClient;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public UserDTO createUser(UserDTO userDetails) {
		userDetails.setUserId(UUID.randomUUID().toString());
		userDetails.setEncryptedPassword(bCrytPaswordEncoder.encode(userDetails.getPassword()));
		UserEntity userEntity = modelMapper.mapToObject(userDetails, UserEntity.class);
		userRepository.save(userEntity);
		UserDTO returnUserDTO = modelMapper.mapToObject(userEntity, UserDTO.class);
		return returnUserDTO;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(username);
		if (userEntity == null)
			throw new UsernameNotFoundException("Username not found: " + username);
		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true, true, true, true,
				new ArrayList());
	}

	@Override
	public UserDTO getUserDetailsByEmail(String email) {
		UserEntity userEntity = userRepository.findByEmail(email);
		if (userEntity == null)
			throw new UsernameNotFoundException("Username not found: " + email);
		return modelMapper.mapToObject(userEntity, UserDTO.class);
	}

	@Override
	public UserDTO getUserByUserId(String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId);
		if (userEntity == null)
			throw new UsernameNotFoundException("Username not found: " + userId);
		UserDTO returnUserDTO = modelMapper.mapToObject(userEntity, UserDTO.class);
		logger.info("Before calling albums microservice");
		List<AlbumResponseModel> albumsList = albumsServiceClient.getAlbums(userId);
		logger.info("After calling albums microservice");
		returnUserDTO.setAlbums(albumsList);
		return returnUserDTO;
	}

}
