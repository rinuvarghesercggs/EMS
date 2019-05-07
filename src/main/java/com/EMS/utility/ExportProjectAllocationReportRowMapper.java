package com.EMS.utility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.springframework.jdbc.core.RowMapper;

import com.EMS.model.ExportProjectAllocationReportModel;

public class ExportProjectAllocationReportRowMapper implements RowMapper<ExportProjectAllocationReportModel> {

	@Override
	public ExportProjectAllocationReportModel mapRow(ResultSet rs, int rowNum) throws SQLException {

		ExportProjectAllocationReportModel rpt = new ExportProjectAllocationReportModel();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		
		rpt.setAllocId(Long.parseLong(rs.getString("alloc_id")));
		rpt.setBillable(rs.getString("Billable"));
		rpt.setEndDate(simpleDateFormat.format(rs.getDate("end_date")));
		rpt.setFullName(rs.getString("FullName"));
		rpt.setProjectId(Long.parseLong(rs.getString("projectId")));
		rpt.setProjectName(rs.getString("project_name"));
		rpt.setPercentage(Double.parseDouble(rs.getString("percentage")));
		rpt.setStartDate(simpleDateFormat.format(rs.getDate("start_date")));
		rpt.setUserId(Long.parseLong(rs.getString("userId")));
		rpt.setUserName(rs.getString("user_name"));
		return rpt;
	}
	

}
