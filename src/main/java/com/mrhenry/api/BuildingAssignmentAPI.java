package com.mrhenry.api;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrhenry.dto.AssignmentBuildingDTO;
import com.mrhenry.service.IAssignmentBuildingService;
import com.mrhenry.utils.HttpUtil;

@WebServlet(urlPatterns = {"/api-admin-assignmentbuilding"})
public class BuildingAssignmentAPI extends HttpServlet {
	
	private static final long serialVersionUID = -77606861423213187L;

	@Inject
	IAssignmentBuildingService assignmentBuildingService;
	
	// edit
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		AssignmentBuildingDTO assignment = HttpUtil.of(request.getReader()).toModel(AssignmentBuildingDTO.class);
		assignmentBuildingService.save(assignment);
		mapper.writeValue(response.getOutputStream(), assignment);
	}
}
