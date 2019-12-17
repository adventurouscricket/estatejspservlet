package com.mrhenry.mapper;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import com.mrhenry.annotation.Entity;

public class ResultSetMapper<T> {
	public List<T> mapRow(ResultSet rs, Class<T> zClass) {
		List<T> resutls = new ArrayList<>();
		try {
			if(zClass.isAnnotationPresent(Entity.class)) {
				ResultSetMetaData rsMetaData = rs.getMetaData();
				Field[] fields = zClass.getDeclaredFields();
				
				//row
				while(rs.next()) {
					T object = (T) zClass.newInstance();
					//get metadata in row
					for(int i = 0; i < rsMetaData.getColumnCount(); i++) {
						String columnName = rsMetaData.getColumnName(i + 1);
						Object columnValue = rs.getObject(i+1);
						
						//current class
						convertResultSetToEntity(fields, columnName, columnValue, object);
						
						//parent class
						Class<? super T> parentClass = zClass.getSuperclass();
						while(parentClass != null) {
							Field[] parentFields = parentClass.getDeclaredFields();
							convertResultSetToEntity(parentFields, columnName, columnValue, object);
							parentClass = zClass.getSuperclass();
						}
					}
					resutls.add(object);
				}
			}
		} catch (Exception e) {
			resutls = null;
			e.printStackTrace();
		}
		
		return resutls;
	}

	private void convertResultSetToEntity(Field[] fields, String columnName, Object columnValue, T object) {
		for(Field field: fields) {
			
		}
	}
}
