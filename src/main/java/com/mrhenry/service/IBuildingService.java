package com.mrhenry.service;

import java.util.List;

import com.mrhenry.builder.BuildingSearchBuilder;
import com.mrhenry.dto.BuildingDTO;
import com.mrhenry.paging.Pageable;

public interface IBuildingService {
	BuildingDTO save(BuildingDTO building);
	void update(BuildingDTO building, Long id);
	List<BuildingDTO> findAll(BuildingSearchBuilder builder, Pageable pageable);
	BuildingDTO findById(Long id);
	void delete(Long[] ids);
	Integer countAll(BuildingSearchBuilder builder);
}
