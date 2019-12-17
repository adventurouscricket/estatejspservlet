package com.mrhenry.repository.impl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.mrhenry.annotation.Column;
import com.mrhenry.annotation.Entity;
import com.mrhenry.mapper.ResultSetMapper;
import com.mrhenry.repository.GenericJDBC;

public class AbstractJDBC<T> implements GenericJDBC<T>{

	private Class<T> zClass;
	
	AbstractJDBC(){
		Type type = this.getClass().getGenericSuperclass();
		ParameterizedType paramerterizedType = (ParameterizedType) type;
		zClass = (Class<T>) paramerterizedType.getActualTypeArguments()[0];
	}
	
	private Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String databaseURL = "jdbc:mysql://localhost:3306/estatejspservlet";
			String user = "root";
			String password = "vuphuong2811";
			return DriverManager.getConnection(databaseURL, user, password);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<T> query(String sql, Object... parameters) {
		ResultSetMapper<T> resultSetMapper = new ResultSetMapper<>();
		try(Connection conn = getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery()){
			
			if(conn != null) {
				return resultSetMapper.mapRow(resultSet, zClass);
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public Long insert(Object entity) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			String sql = createSQLInsert();
			
			statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			if(conn != null) {
				//set parameter to statement
				Class<?> entityClass = entity.getClass();
				Field[] fields = entityClass.getDeclaredFields();
				for(int i=0; i < fields.length; i++) {
					int index = i + 1;
					Field field = fields[i];
					field.setAccessible(true);
					statement.setObject(index, field.get(entity));
				}
				
				int parentIndex = fields.length;
				Class<?> parentClass = entityClass.getSuperclass();
				while(parentClass != null) {
					Field[] parentFields = parentClass.getDeclaredFields();
					for(int i=0; i < parentFields.length; i++) {
						Field field = parentFields[i];
						field.setAccessible(true);
						statement.setObject(parentIndex, field.get(entity));
						parentIndex++;
					}
					parentClass = zClass.getSuperclass();
				}
				
				int rowsInserted = statement.executeUpdate();
				conn.commit();
				resultSet = statement.getGeneratedKeys();
				if(rowsInserted > 0) {
					while(resultSet.next()) {
						Long id = resultSet.getLong(1);
						return id;
					}
				}
			}
			
		} catch(SQLException | IllegalAccessException e) {
			if(conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			try {
				if(conn != null) {
					conn.close();
				}
				if(statement != null) {
					statement.close();
				}
			} catch(SQLException e2) {
				e2.printStackTrace();
			}
		}
		return null;
	}

	private String createSQLInsert() {
		
		String tableName = "";
		
		if(zClass.isAnnotationPresent(Entity.class)) {
			tableName = zClass.getAnnotation(Entity.class).name();
		}
		
		StringBuilder fields = new StringBuilder("");
		StringBuilder params = new StringBuilder("");
		
		for(Field field : zClass.getDeclaredFields()) {
			if(field.isAnnotationPresent(Column.class)) {
				Column column = field.getAnnotation(Column.class);
				
				if(fields.length() > 0) {
					fields.append(",");
					params.append(",");
				}
				
				fields.append(column.name());
				params.append("?");
			}
		}
		
		//fields in parent class
		Class<?> parentClass = zClass.getSuperclass();
		while(parentClass != null) {
			for(Field field: parentClass.getDeclaredFields()) {
				if(field.isAnnotationPresent(Column.class)) {
					Column column = field.getAnnotation(Column.class);
					
					if(fields.length() > 0) {
						fields.append(",");
						params.append(",");
					}
					
					fields.append(column.name());
					params.append("?");
				}
			}
			parentClass = zClass.getSuperclass();
		}
		
		
		String sql = "INSERT INTO "+tableName+"("+fields.toString()+")"+"VALUES("+params.toString()+")";
		
		return sql;
	}

	@Override
	public void update(Object entity, Long id) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			String sql = createSQLUpdate();
			
			statement = conn.prepareStatement(sql);
			
			if(conn != null) {
				//set parameter to statement
				Class<?> entityClass = entity.getClass();
				Field[] fields = entityClass.getDeclaredFields();
				for(int i=0; i < fields.length; i++) {
					int index = i + 1;
					Field field = fields[i];
					field.setAccessible(true);
					statement.setObject(index, field.get(entity));
				}
				
				int parentIndex = fields.length;
				Class<?> parentClass = entityClass.getSuperclass();
				while(parentClass != null) {
					Field[] parentFields = parentClass.getDeclaredFields();
					for(int i=0; i < parentFields.length; i++) {
						Field field = parentFields[i];
						field.setAccessible(true);
						if(!field.getAnnotation(Column.class).equals("id")) {
							statement.setObject(parentIndex, field.get(entity));
							parentIndex++;
						} 
					}
					parentClass = zClass.getSuperclass();
				}
				
				statement.setObject(parentIndex, id);
				statement.executeUpdate();
				conn.commit();
			}
			
		} catch(SQLException | IllegalAccessException e) {
			if(conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			try {
				if(conn != null) {
					conn.close();
				}
				if(statement != null) {
					statement.close();
				}
			} catch(SQLException e2) {
				e2.printStackTrace();
			}
		}
	}
	
	private String createSQLUpdate() {
		
		String tableName = "";
		
		if(zClass.isAnnotationPresent(Entity.class)) {
			tableName = zClass.getAnnotation(Entity.class).name();
		}
		
		StringBuilder sets = new StringBuilder("");
		String where = " WHERE ";
		
		for(Field field : zClass.getDeclaredFields()) {
			if(field.isAnnotationPresent(Column.class)) {
				Column column = field.getAnnotation(Column.class);
				String columnName = column.name();
				String setValue = columnName +"= ?"; 
				if(!columnName.equals("id")){
					if(sets.length() > 0) {
						sets.append(",");
					}
					sets.append(setValue);
				} else {
					where += setValue;
				}
			}
		}
		
		//fields in parent class
		Class<?> parentClass = zClass.getSuperclass();
		while(parentClass != null) {
			for(Field field: parentClass.getDeclaredFields()) {
				if(field.isAnnotationPresent(Column.class)) {
					
					Column column = field.getAnnotation(Column.class);
					String columnName = column.name();
					String setValue = columnName +"= ?"; 
					if(!columnName.equals("id")){
						if(sets.length() > 0) {
							sets.append(",");
						}
						sets.append(setValue);
					} else {
						where += setValue;
					}
				}
			}
			parentClass = zClass.getSuperclass();
		}
		
		
		String sql = "UPDATE "+tableName+" SET"+"VALUES("+sets.toString()+")";
		
		return sql;
	}
}
