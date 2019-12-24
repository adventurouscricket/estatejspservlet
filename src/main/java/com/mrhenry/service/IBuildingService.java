package com.mrhenry.service;

import java.util.List;

import com.mrhenry.builder.BuildingSearchBuilder;
import com.mrhenry.dto.BuildingDTO;
import com.mrhenry.paging.Pageable;

public interface IBuildingService {
	BuildingDTO save(BuildingDTO building);
	List<BuildingDTO> findAll(BuildingSearchBuilder builder, Pageable pageable);
}
