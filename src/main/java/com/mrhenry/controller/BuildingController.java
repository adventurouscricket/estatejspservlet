package com.mrhenry.controller;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mrhenry.builder.BuildingSearchBuilder;
import com.mrhenry.dto.BuildingDTO;
import com.mrhenry.service.IBuildingService;
import com.mrhenry.utils.DataUtil;
import com.mrhenry.utils.FormUtil;

@WebServlet(urlPatterns = "/admin-building")
public class BuildingController extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private IBuildingService buildingService;
	
	/*public BuildingController() {
		buildingService = new BuildingService();
	}*/
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BuildingDTO model = FormUtil.toModel(BuildingDTO.class, request);
		String action = request.getParameter("action");
		String url = "";
		if(action.equals("list")) {
			url="/views/building/list.jsp";
			BuildingSearchBuilder builder = initBuilder(model);
			model.setResults(buildingService.findAll(builder, null));
		} else if(action.equals("edit")) {
			url="/views/building/edit.jsp";
		}
		request.setAttribute("districts", DataUtil.getDistricts());
		request.setAttribute("buildingTypes", DataUtil.getBuildingTypes());
		request.setAttribute("models", model.getResults());
		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
	}
	
	private BuildingSearchBuilder initBuilder(BuildingDTO model) {
		BuildingSearchBuilder builder = new BuildingSearchBuilder.Builder()
				.setName(model.getName()).setWard(model.getWard()).setStreet(model.getStreet())
				.setAreaRentFrom(model.getAreaRentFrom()).setAreaRentTo(model.getAreaRentTo())
				.setCostRentFrom(model.getCostRentFrom()).setCostRentTo(model.getCostRentTo())
				.setNumberOfBasement(model.getNumberOfBasement()).setBuildingTypes(model.getBuildingTypes()).build();
		return builder;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/views/building/list.jsp");
		rd.forward(request, response);
	}
}
