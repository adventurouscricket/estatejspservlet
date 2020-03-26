package com.mrhenry.controller;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mrhenry.dto.AssignmentBuildingDTO;
import com.mrhenry.paging.PageRequest;
import com.mrhenry.paging.Pageable;
import com.mrhenry.paging.Sorter;
import com.mrhenry.service.IAssignmentBuildingService;
import com.mrhenry.utils.FormUtil;

@WebServlet(urlPatterns = { "/admin-asgnbuilding" })
public class AssignmentBuildingController extends HttpServlet {

	private static final long serialVersionUID = -77606861423213187L;

	@Inject
	IAssignmentBuildingService assignmentBuildingService;
	
	// add new
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AssignmentBuildingDTO model = FormUtil.toModel(AssignmentBuildingDTO.class, request);
		String url = "";
		if(model.getAction().equals("list")) {			
			url="/views/assignment/assignmentbuilding.jsp";
			Pageable pageable = new PageRequest(model.getPage(), model.getMaxPageItem(), new Sorter(model.getSortName(), model.getSortBy()));
			model.setResults(assignmentBuildingService.findAllByBuildingId(model.getBuildingId(), pageable));
			model.setTotalItem(assignmentBuildingService.countAllByBuildingId(model.getBuildingId()));
			model.setTotalPage((int) Math.ceil((double) model.getTotalItem()/model.getMaxPageItem()));
			
			request.setAttribute("model", model);
			request.setAttribute("models", model.getResults());
			RequestDispatcher rd = request.getRequestDispatcher(url);
			
			rd.forward(request, response);
		}
	}
}
