package com.mrhenry.service.impl;

import java.util.List;

import javax.inject.Inject;

import com.mrhenry.dto.AssignmentBuildingDTO;
import com.mrhenry.entity.AssignmentBuildingEntity;
import com.mrhenry.paging.Pageable;
import com.mrhenry.repository.IAssignmentBuildingRepository;
import com.mrhenry.service.IAssignmentBuildingService;

public class AssignmentBuildingService implements IAssignmentBuildingService {

	@Inject
	IAssignmentBuildingRepository assignmentBuildingRepository;
	
	@Override
	public List<AssignmentBuildingDTO> findAllByBuildingId(Long buildingId, Pageable pageable) {
		List<AssignmentBuildingDTO> assignments = assignmentBuildingRepository.findAllByBuildingId(buildingId, pageable);
		return assignments;
	}

	@Override
	public Integer countAllByBuildingId(Long buildingId) {
		Integer count = assignmentBuildingRepository.countAllByBuildingId(buildingId);
		return count;
	}

	@Override
	public void save(AssignmentBuildingDTO assignment) {
			Long buildingId = assignment.getBuildingId();
			if (buildingId != null) {
				assignmentBuildingRepository.deleteByBuildingId(buildingId);
				for(Long staffId: assignment.getStaffIds()) {
//					assignmentBuildingRepository.deleteByBuildingId(assignment.getBuildingId());
					AssignmentBuildingEntity entity = new AssignmentBuildingEntity();
					entity.setStaffId(staffId);
					entity.setBuildingId(assignment.getBuildingId());
					assignmentBuildingRepository.insert(entity);
				}
			}
	}
	
}
