package com.EMS.utility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.springframework.jdbc.core.RowMapper;

import com.EMS.model.ApprovalTimeTrackReportModel;
import com.EMS.model.ExportProjectTaskReportModel;

public class ApprovalTimeTrackReportRowMapper implements RowMapper<ApprovalTimeTrackReportModel> {

	@Override
	public ApprovalTimeTrackReportModel mapRow(ResultSet rs, int rowNum) throws SQLException {

		ApprovalTimeTrackReportModel rpt = new ApprovalTimeTrackReportModel();
		rpt.setProjectName(rs.getString("projectName"));
		if(rs.getString("label").equalsIgnoreCase("approved")) {
			rpt.setBillableHours(rs.getString("hours")!=null ? Double.parseDouble(rs.getString("hours")) : 0);
		}
		else if (rs.getString("label").equalsIgnoreCase("logged")) {
			rpt.setLoggedHours(rs.getString("hours")!=null ? Double.parseDouble(rs.getString("hours")) : 0);
		}
		rpt.setLabel(rs.getString("label"));
		return rpt;
	}
	

}
