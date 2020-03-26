package com.mrhenry.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.mrhenry.converter.UserConverter;
import com.mrhenry.dto.UserDTO;
import com.mrhenry.repository.IUserRepositoty;
import com.mrhenry.repository.impl.UserRepository;
import com.mrhenry.service.IUserService;

public class UserService implements IUserService{

//	@Inject
	IUserRepositoty userRepository;
	UserConverter userConverter;
	
	public UserService() {
		if (userRepository == null) {
			userRepository = new UserRepository();
		}
		if (userConverter == null) {
			userConverter = new UserConverter();
		}
	}
	
	@Override
	public List<UserDTO> findAllStaffs() {
		return userRepository.findAllStaffs().stream().map(item -> userConverter.convertToDTO(item)).collect(Collectors.toList());
	}
	
}
