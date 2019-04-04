package com.EMS.utility;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.EMS.model.BenchProjectReportModel;
import com.EMS.model.ProjectReportModel;

public class BenchReportRowMapper implements RowMapper<BenchProjectReportModel> {

	@Override
	public BenchProjectReportModel mapRow(ResultSet rs, int rowNum) throws SQLException {

		BenchProjectReportModel rpt = new BenchProjectReportModel();
		
		rpt.setUserName(rs.getString("users"));
		rpt.setAllocatedPerce(rs.getString("allocated_perce"));
		rpt.setIsBillable(rs.getInt("is_billable"));
		rpt.setProjectName(rs.getString("project_name"));
		rpt.setDepartmentId(rs.getString("department_id"));
		rpt.setDepartmentName(rs.getString("department_name"));
		rpt.setUserId(rs.getString("user_id"));
		return rpt;
	}
	

}
