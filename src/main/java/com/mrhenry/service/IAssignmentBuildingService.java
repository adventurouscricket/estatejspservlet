package com.mrhenry.service;

import java.util.List;

import com.mrhenry.dto.AssignmentBuildingDTO;
import com.mrhenry.paging.Pageable;

public interface IAssignmentBuildingService {
	List<AssignmentBuildingDTO> findAllByBuildingId(Long buildingId, Pageable pageable);
	Integer countAllByBuildingId(Long buildingId);
	void save(AssignmentBuildingDTO assignments);
}
