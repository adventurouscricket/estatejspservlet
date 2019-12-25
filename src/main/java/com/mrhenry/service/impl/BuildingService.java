package com.mrhenry.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;

import com.mrhenry.builder.BuildingSearchBuilder;
import com.mrhenry.converter.BuildingConverter;
import com.mrhenry.dto.BuildingDTO;
import com.mrhenry.entity.BuildingEntity;
import com.mrhenry.entity.RentAreaEntity;
import com.mrhenry.paging.Pageable;
import com.mrhenry.repository.IBuildingRepository;
import com.mrhenry.repository.IRentAreaRepository;
import com.mrhenry.repository.impl.BuildingRepository;
import com.mrhenry.repository.impl.RentAreaRepository;
import com.mrhenry.service.IBuildingService;

public class BuildingService implements IBuildingService{
	
//	@Inject
	private IBuildingRepository buildingRepository;

//	@Inject
	private IRentAreaRepository rentAreaRepository;
	
//	@Inject
	private BuildingConverter buildingConverter;
	
	public BuildingService() {
		if(buildingRepository == null) {
			buildingRepository = new BuildingRepository();
		}
		
		if(rentAreaRepository == null) {
			rentAreaRepository = new RentAreaRepository();
		}
		
		if(buildingConverter == null) {
			buildingConverter = new BuildingConverter();
		}
	}
	
	@Override
	public BuildingDTO save(BuildingDTO building) {
		BuildingEntity buildingEntity = buildingConverter.convertToEntity(building);
		buildingEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		buildingEntity.setCreatedBy("Hello");
		buildingEntity.setType(StringUtils.join(building.getBuildingTypes(), ","));
		

		Long id = buildingRepository.insert(buildingEntity);
		
		// insert rent area
		if(StringUtils.isNotBlank(building.getRentArea())) {
			for(String item: building.getRentArea().split(",")) {
				RentAreaEntity rentAreaEntity = new RentAreaEntity();
				rentAreaEntity.setBuildingId(id);
				rentAreaEntity.setValue(item);
				rentAreaEntity.setCreatedBy("Hello");
				rentAreaEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
				
				rentAreaRepository.insert(rentAreaEntity);
			}
		}
		return buildingConverter.convertToDTO(buildingRepository.findById(id));
	}

	@Override
	public List<BuildingDTO> findAll(BuildingSearchBuilder builder, Pageable pageable) {
		List<BuildingDTO> results= buildingRepository.findAll(builder, pageable).stream()
				.map(item -> buildingConverter.convertToDTO(item)).collect(Collectors.toList());
		return results;
	}

	@Override
	public BuildingDTO findById(Long id) {
		return buildingConverter.convertToDTO(buildingRepository.findById(id));
	}
}
