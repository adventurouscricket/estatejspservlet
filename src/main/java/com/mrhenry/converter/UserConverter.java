package com.mrhenry.converter;

import org.modelmapper.ModelMapper;

import com.mrhenry.dto.UserDTO;
import com.mrhenry.entity.UserEntity;

public class UserConverter {
	public UserDTO convertToDTO(UserEntity entity) {
		ModelMapper modelMapper = new ModelMapper();
		UserDTO result = modelMapper.map(entity, UserDTO.class);
		return result;
	}
	
	public UserEntity convertToEntity(UserDTO entity) {
		ModelMapper modelMapper = new ModelMapper();
		UserEntity result = modelMapper.map(entity, UserEntity.class);
		return result;
	}
}
