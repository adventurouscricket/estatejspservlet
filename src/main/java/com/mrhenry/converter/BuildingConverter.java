package com.mrhenry.converter;

import org.modelmapper.ModelMapper;

import com.mrhenry.dto.BuildingDTO;
import com.mrhenry.entity.BuildingEntity;

public class BuildingConverter {
	public BuildingDTO convertToDTO(BuildingEntity entity) {
		ModelMapper modelMapper = new ModelMapper();
		BuildingDTO result = modelMapper.map(entity, BuildingDTO.class);
		return result;
	}
	
	public BuildingEntity convertToEntity(BuildingDTO dto) {
		ModelMapper modelMapper = new ModelMapper();
		BuildingEntity result = modelMapper.map(dto, BuildingEntity.class);
		return result;
	}
}
