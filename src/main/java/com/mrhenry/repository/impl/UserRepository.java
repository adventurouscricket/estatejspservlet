package com.mrhenry.repository.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.mrhenry.entity.UserEntity;
import com.mrhenry.mapper.ResultSetMapper;
import com.mrhenry.repository.IUserRepositoty;

public class UserRepository extends AbstractJDBC<UserEntity> implements IUserRepositoty {

	@Override
	public List<UserEntity> findAllStaffs() {
		Connection conn = null;
		Statement statement = null;
		ResultSetMapper<UserEntity> resultSetMapper = new ResultSetMapper<>();
		StringBuilder sql = new StringBuilder("SELECT u.id, u.username, u.password, u.fullname FROM USER u ");
		sql.append("JOIN USERROLE ur ON u.id = ur.userid ");
		sql.append("JOIN ROLE r ON ur.roleid = r.id and r.code = 'STAFF'");
		
		try{
			conn = super.getConnection();
			statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql.toString());
			if(conn != null) {
				List<UserEntity> users = resultSetMapper.mapRow(resultSet, UserEntity.class);
				return users;
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
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
	
}
