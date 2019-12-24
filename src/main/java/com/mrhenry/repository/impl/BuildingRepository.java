package com.mrhenry.repository.impl;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.mrhenry.builder.BuildingSearchBuilder;
import com.mrhenry.entity.BuildingEntity;
import com.mrhenry.paging.Pageable;
import com.mrhenry.repository.IBuildingRepository;

public class BuildingRepository extends AbstractJDBC<BuildingEntity> implements IBuildingRepository{
	
	public List<BuildingEntity> findAll(BuildingSearchBuilder builder, Pageable pageable) {
		Map<String, Object> properties = buildingMapSearch(builder);
		StringBuilder whereClause = new StringBuilder();
		if(StringUtils.isNotBlank(builder.getCostRentFrom())) {
			whereClause.append("AND costrent >= "+builder.getCostRentFrom()+"");
		}
		if(StringUtils.isNotBlank(builder.getCostRentTo())) {
			whereClause.append("AND costrent <= "+builder.getCostRentTo()+"");
		}
		
		if(StringUtils.isNotBlank(builder.getAreaRentFrom()) || StringUtils.isNotBlank(builder.getAreaRentTo())) {
			whereClause.append(" AND EXISTS (SELECT * FROM rentarea ra WHERE (ra.buildingid = dao.id ");
			if(StringUtils.isNotBlank(builder.getAreaRentFrom())) {
				whereClause.append(" AND ra.value >= '"+builder.getAreaRentFrom()+"'");
			}
			if(StringUtils.isNotBlank(builder.getAreaRentTo())) {
				whereClause.append(" AND ra.value <= '"+builder.getAreaRentTo()+"'");
			}
			whereClause.append(" ))");
		}
		
		/*if(builder.getBuildingTypes().length > 0) {
			for(String type: builder.getBuildingTypes()) {
				whereClause.append(" AND (dao.type LIKE '%"+type+"%')");
			}
		}*/
		String[] buildingTypes = builder.getBuildingTypes();
		//java 7
		/*if(buildingTypes.length > 0) {
			for(int i = 0; i < buildingTypes.length; i++) {
				if(i == 0) {
					whereClause.append(" AND (dao.type LIKE '%"+buildingTypes[i]+"%'");
				} else {
					whereClause.append(" OR dao.type LIKE '%"+buildingTypes[i]+"%'");
				}
			}
			whereClause.append(")");
		}*/
		
		//jva 8
		if(buildingTypes.length > 0) {
			whereClause.append(" AND (dao.type LIKE '%"+buildingTypes[0]+"%'");
			Arrays.stream(buildingTypes).filter(item -> !item.equals(buildingTypes[0]))
				.forEach(item -> whereClause.append(" OR dao.type LIKE '%"+item+"%'"));
			whereClause.append(")");
		}
		return super.findAll(properties, pageable, whereClause);
	}
	
	private Map<String, Object> buildingMapSearch(BuildingSearchBuilder builder) {
		Map<String, Object> properties = new HashMap<>();
		Field[] fields = BuildingSearchBuilder.class.getDeclaredFields();
		for(Field field : fields) {
			if(!field.getName().equals("buildingTypes") && !field.getName().startsWith("areaRent") 
			&& !field.getName().startsWith("costRent")) {
//				result.put(field.getName().toLowerCase(), getValue(field, builder));
				field.setAccessible(true);
				try {
					Object value = field.get(builder);
					if(value != null) {
						properties.put(field.getName().toLowerCase(), value);
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	/*private Object getValue(Field field, BuildingSearchBuilder builder) {
		Method getter = getGetter(field, builder);
		if(getter != null) {
			try {
				return getter.invoke(builder);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	private Method getGetter(Field field, BuildingSearchBuilder builder) {
		String strMethod = "get"+ StringUtils.capitalize(field.getName());
		try {
			return builder.getClass().getMethod(strMethod);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}*/
}
