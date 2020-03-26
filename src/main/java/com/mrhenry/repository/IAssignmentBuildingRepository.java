package com.mrhenry.repository;

import java.util.List;

import com.mrhenry.dto.AssignmentBuildingDTO;
import com.mrhenry.entity.AssignmentBuildingEntity;
import com.mrhenry.paging.Pageable;

public interface IAssignmentBuildingRepository extends GenericJDBC<AssignmentBuildingEntity>{
	List<AssignmentBuildingDTO> findAllByBuildingId(Long buildingId, Pageable pageable);
	Integer countAllByBuildingId(Long buildingId);
	void deleteByBuildingId(Long buildingId);
}
