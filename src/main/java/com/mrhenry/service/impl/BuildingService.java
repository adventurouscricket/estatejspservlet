package com.mrhenry.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.mrhenry.builder.BuildingSearchBuilder;
import com.mrhenry.converter.BuildingConverter;
import com.mrhenry.dto.BuildingDTO;
import com.mrhenry.paging.Pageable;
import com.mrhenry.repository.IBuildingRepository;
import com.mrhenry.service.IBuildingService;

public class BuildingService implements IBuildingService{
	
	@Inject
	private IBuildingRepository buildingRepository;

	@Inject
	private BuildingConverter buildingConverter;
	
	/*public BuildingService() {
		if(buildingRepository == null)
			buildingRepository = new BuildingRepository();
		if(buildingConverter == null)
			buildingConverter = new BuildingConverter();
	}*/
	
	@Override
	public BuildingDTO save(BuildingDTO building) {
		Long id = buildingRepository.insert(buildingConverter.convertToEntity(building));
		
		return null;
	}

	@Override
	public List<BuildingDTO> findAll(BuildingSearchBuilder builder, Pageable pageable) {
		List<BuildingDTO> results= buildingRepository.findAll(builder, pageable).stream()
				.map(item -> buildingConverter.convertToDTO(item)).collect(Collectors.toList());
		return results;
	}
}
