package com.mrhenry.controller;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.mrhenry.builder.BuildingSearchBuilder;
import com.mrhenry.dto.BuildingDTO;
import com.mrhenry.paging.PageRequest;
import com.mrhenry.paging.Pageable;
import com.mrhenry.paging.Sorter;
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
		String url = "";
		if(model.getAction().equals("list")) {
			url="/views/building/list.jsp";
			BuildingSearchBuilder builder = initBuilder(model);
			Pageable pageable = new PageRequest(model.getPage(), model.getMaxPageItem(), new Sorter(model.getSortName(), model.getSortBy()));
			model.setTotalItem(buildingService.countAll(builder));
			model.setTotalPage((int) Math.ceil((double) model.getTotalItem()/model.getMaxPageItem()));
			model.setResults(buildingService.findAll(builder, pageable));
		} else if(model.getAction().equals("edit")) {
			if(model.getId() != null) {
				model = buildingService.findById(model.getId());
			}
			url="/views/building/edit.jsp";
		}
		request.setAttribute("model", model);
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
				.setNumberOfBasement(model.getNumberOfBasement()).setBuildingTypes(model.getBuildingTypes())
				.setDistrict(model.getDistrict()).setBuildingArea(model.getBuildingArea()).setDirection(model.getDirection())
				.build();
		return builder;
	}
}
