package com.mrhenry.service.impl;

import com.mrhenry.converter.BuildingConverter;
import com.mrhenry.dto.BuildingDTO;
import com.mrhenry.repository.IBuildingRepository;
import com.mrhenry.repository.impl.BuildingRepository;
import com.mrhenry.service.IBuildingService;

public class BuildingService implements IBuildingService{

	private IBuildingRepository buildingRepository;
	
	public BuildingService() {
		buildingRepository = new BuildingRepository();
	}
	
	@Override
	public BuildingDTO save(BuildingDTO building) {
		BuildingConverter buildingConverter = new BuildingConverter();
		Long id = buildingRepository.insert(buildingConverter.convertToEntity(building));
		
		return null;
	}
	
}
