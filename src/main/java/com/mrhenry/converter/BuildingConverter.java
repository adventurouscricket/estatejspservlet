package com.mrhenry.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;

import com.mrhenry.dto.BuildingDTO;
import com.mrhenry.entity.BuildingEntity;
import com.mrhenry.entity.RentAreaEntity;
import com.mrhenry.repository.IRentAreaRepository;

public class BuildingConverter {
	

	@Inject
	private IRentAreaRepository rentAreaRepository;
	
	
	public BuildingDTO convertToDTO(BuildingEntity entity) {
		ModelMapper modelMapper = new ModelMapper();
		BuildingDTO result = modelMapper.map(entity, BuildingDTO.class);
		
		Map<String, Object> properties = new HashMap<>();
		properties.put("buildingid", entity.getId());
		
		List<RentAreaEntity> rentAreas = rentAreaRepository.findAll(properties, null);
		List<String> lstRentArea = new ArrayList<String>();
		for(RentAreaEntity item: rentAreas) {
			lstRentArea.add(item.getValue());
		}
		if(lstRentArea.size() > 0) {
			result.setRentarea(StringUtils.join(lstRentArea, ","));
		}
		return result;
	}
	
	public BuildingEntity convertToEntity(BuildingDTO dto) {
		ModelMapper modelMapper = new ModelMapper();
		BuildingEntity result = modelMapper.map(dto, BuildingEntity.class);
		return result;
	}
}
