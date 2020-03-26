package com.mrhenry.repository.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.mrhenry.dto.AssignmentBuildingDTO;
import com.mrhenry.entity.AssignmentBuildingEntity;
import com.mrhenry.paging.PageRequest;
import com.mrhenry.paging.Pageable;
import com.mrhenry.paging.Sorter;
import com.mrhenry.repository.IAssignmentBuildingRepository;

public class AssignmentBuildingRepository extends AbstractJDBC<AssignmentBuildingEntity> implements IAssignmentBuildingRepository{

	@Override
	public List<AssignmentBuildingDTO> findAllByBuildingId(Long buildingId, Pageable pageable) {
		Connection conn = null;
		Statement statement = null;
		try {
			StringBuilder sql = this.querySQL(buildingId, pageable);
			
//			sql.append(this.querySQL(buildingId, pageable));
			
			conn = super.getConnection();
			statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql.toString());
			if(conn != null) {
				List<AssignmentBuildingDTO> assignments = new ArrayList<AssignmentBuildingDTO>();
				while (rs.next()) {
					AssignmentBuildingDTO assignment = new AssignmentBuildingDTO();
					assignment.setId(rs.getLong("id"));
					assignment.setStaffId(rs.getLong("staffid"));
					assignment.setFullName(rs.getString("fullname"));
					assignment.setIsChecked(rs.getBoolean("ischecked"));
					assignments.add(assignment);
				}
				return assignments;
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

	@Override
	public Integer countAllByBuildingId(Long buildingId) {
		Connection conn = null;
		Statement statement = null;
		try {
			StringBuilder sql = new StringBuilder("select count(*) from (");
			
			sql.append(this.querySQL(buildingId, null));
			
			sql.append(") als");
			
			conn = super.getConnection();
			statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql.toString());
			if(conn != null) {
				while (rs.next()) {
					return rs.getInt(1);
				}
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
	
	private StringBuilder querySQL (Long buildingId, Pageable pageable) {
		StringBuilder sql = new StringBuilder("select asg.id, u.id staffid, u.fullname, ");
		sql.append("Case when Exists (");
		sql.append("select asg1.id from assignmentbuilding asg1 where asg1.buildingid = b.id and asg1.buildingid ="+buildingId);
		sql.append(" ) then true else false end ischecked ");
		sql.append("from user u ");
		sql.append("left join assignmentbuilding asg on asg.staffid = u.id ");
		sql.append("left join building b on b.id = asg.buildingid ");
		sql.append("Join userrole ur on u.id = ur.userid ");
		sql.append("Join role r on ur.roleid = r.id ");
		sql.append("where u.status = 1 and r.code = 'STAFF' ");
		sql.append("group by fullname, ischecked ");
		if(pageable != null) {
			if(pageable.getSorter() != null && StringUtils.isNotBlank(pageable.getSorter().getSortName())) {
				Sorter sorter = pageable.getSorter();
				sql.append(" ORDER BY "+sorter.getSortName()+" "+sorter.getSortBy());
			}
			if(pageable.getLimit() != null && pageable.getOffset() != null) {
				sql.append(" LIMIT "+pageable.getOffset()+" , "+pageable.getLimit());
			}
		}
		return sql;
	}
	
	public void deleteByBuildingId(Long buildingId) {
		String sql = "DELETE FROM assignmentbuilding where buildingId = ?";
		super.delete(buildingId, sql);
	}
}
