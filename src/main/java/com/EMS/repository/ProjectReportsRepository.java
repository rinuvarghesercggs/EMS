package com.EMS.repository;

import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.EMS.model.BenchProjectReportModel;
import com.EMS.model.ProjectReportModel;
import com.EMS.utility.BenchReportRowMapper;
import com.EMS.utility.DbConnectionUtility;
import com.EMS.utility.JsonNodeRowMapper;
import com.EMS.utility.ReportRowMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class ProjectReportsRepository extends DbConnectionUtility {

	/*
	public List<JsonNode> GenerateProjectReports (){
		String sql = "SELECT CONCAT(u.first_name,' ',u.last_name) AS users , u.qualification, a.is_billable, a.allocated_perce, a.start_date, a.end_date, p.project_name FROM allocation a LEFT JOIN `user` u ON u.user_id = a. user_user_id LEFT JOIN project p ON p.project_id = a.project_project_id WHERE CAST(a.start_date  AS DATE) >= '2019/04/01' AND CAST(a.end_date  AS DATE)  <=  '2019/04/30' ";
		List<JsonNode> list = jdbcTemplate.query(sql, new JsonNodeRowMapper(new ObjectMapper()));
		return list;
	}
	*/
	public List<ProjectReportModel> GenerateProjectReports (long projectId,Date fromDate, Date toDate){
		String sql ="";
		List<ProjectReportModel> list = null;
		if(projectId == 0) 
		{
			sql = "SELECT CONCAT(u.first_name,' ',u.last_name) AS users ,  a.is_billable, a.allocated_perce, p.project_name FROM allocation a LEFT JOIN `user` u ON u.user_id = a. user_user_id LEFT JOIN project p ON p.project_id = a.project_project_id WHERE CAST(a.start_date  AS DATE) >= ? AND CAST(a.end_date  AS DATE)  <=  ? order by project_name ";
			list = jdbcTemplate.query(sql, new ReportRowMapper(), new Object[] {fromDate,toDate});
		}
		else 
		{
		sql = "SELECT CONCAT(u.first_name,' ',u.last_name) AS users ,  a.is_billable, a.allocated_perce, p.project_name FROM allocation a LEFT JOIN `user` u ON u.user_id = a. user_user_id LEFT JOIN project p ON p.project_id = a.project_project_id WHERE p.project_id = ? AND CAST(a.start_date  AS DATE) >= ? AND CAST(a.end_date  AS DATE)  <=  ? order by project_name";
		list = jdbcTemplate.query(sql, new ReportRowMapper(), new Object[] {projectId,fromDate,toDate});
		}
		return list;
	}
	public List<BenchProjectReportModel> GenerateBenchProjectReports (Long uId,Date fromDate, Date toDate){
		String sql ="";
		List<BenchProjectReportModel> list = null;

		sql = "SELECT CONCAT(u.first_name,' ',u.last_name) AS users ,u.user_id, a.is_billable, a.allocated_perce, p.project_name, d.department_id, d.department_name FROM allocation a LEFT JOIN `user` u ON u.user_id = a. user_user_id LEFT JOIN project p ON p.project_id = a.project_project_id LEFT JOIN department d ON d.department_id = u.department_department_id WHERE p.project_code = 'BP' AND u.user_id=? AND			 CAST(a.start_date  AS DATE) >= ? AND CAST(a.end_date  AS DATE)  <=  ? ";
		list = jdbcTemplate.query(sql, new BenchReportRowMapper(), new Object[] {uId,fromDate,toDate});

		return list;
	}
	public List<BenchProjectReportModel> GenerateBenchProjectReports (Date fromDate, Date toDate){
		String sql ="";
		List<BenchProjectReportModel> list = null;

		sql = "SELECT CONCAT(u.first_name,' ',u.last_name) AS users ,u.user_id, a.is_billable, a.allocated_perce, p.project_name, d.department_id, d.department_name FROM allocation a LEFT JOIN `user` u ON u.user_id = a. user_user_id LEFT JOIN project p ON p.project_id = a.project_project_id LEFT JOIN department d ON d.department_id = u.department_department_id WHERE p.project_code = 'BP' AND			 CAST(a.start_date  AS DATE) >= ? AND CAST(a.end_date  AS DATE)  <=  ? ";
		list = jdbcTemplate.query(sql, new BenchReportRowMapper(), new Object[] {fromDate,toDate});	

		return list;
	}
}
