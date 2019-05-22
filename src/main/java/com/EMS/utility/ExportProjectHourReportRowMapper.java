package com.EMS.utility;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.EMS.model.ExportProjectHourReportModel;

public class ExportProjectHourReportRowMapper implements RowMapper<ExportProjectHourReportModel> {

	@Override
	public ExportProjectHourReportModel mapRow(ResultSet rs, int rowNum) throws SQLException {

		ExportProjectHourReportModel rpt = new ExportProjectHourReportModel();
	
		rpt.setApproved(rs.getString("approved")!=null ? Double.parseDouble(rs.getString("approved")) : 0);
		rpt.setLogged(rs.getString("logged")!=null ? Double.parseDouble(rs.getString("logged")) : 0);
		rpt.setProjectName(rs.getString("projectName"));
		rpt.setFirstName(rs.getString("firstName"));
		rpt.setLastName(rs.getString("lastName"));
		return rpt;
	}
	

}
