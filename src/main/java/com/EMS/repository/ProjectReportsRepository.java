package com.EMS.repository;

import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.EMS.model.BenchProjectReportModel;
import com.EMS.model.ExportProjectAllocationReportModel;
import com.EMS.model.ExportProjectTaskReportModel;
import com.EMS.model.ProjectReportModel;
import com.EMS.utility.BenchReportRowMapper;
import com.EMS.utility.DbConnectionUtility;
import com.EMS.utility.ExportProjectAllocationReportRowMapper;
import com.EMS.utility.ExportProjectTaskReportRowMapper;
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

		sql = "SELECT CONCAT(u.first_name,' ',u.last_name) AS users ,u.user_id, a.is_billable, a.allocated_perce, p.project_name, d.department_id, d.department_name FROM allocation a LEFT JOIN `user` u ON u.user_id = a. user_user_id LEFT JOIN project p ON p.project_id = a.project_project_id LEFT JOIN department d ON d.department_id = u.department_department_id WHERE p.project_code = 'BP' AND u.user_id=? AND			 CAST(a.start_date  AS DATE) >= ? AND CAST(a.end_date  AS DATE)  <=  ? order by users";
		list = jdbcTemplate.query(sql, new BenchReportRowMapper(), new Object[] {uId,fromDate,toDate});

		return list;
	}
	public List<BenchProjectReportModel> GenerateBenchProjectReports (Date fromDate, Date toDate){
		String sql ="";
		List<BenchProjectReportModel> list = null;

		sql = "SELECT CONCAT(u.first_name,' ',u.last_name) AS users ,u.user_id, a.is_billable, a.allocated_perce, p.project_name, d.department_id, d.department_name FROM allocation a LEFT JOIN `user` u ON u.user_id = a. user_user_id LEFT JOIN project p ON p.project_id = a.project_project_id LEFT JOIN department d ON d.department_id = u.department_department_id WHERE p.project_code = 'BP' AND			 CAST(a.start_date  AS DATE) >= ? AND CAST(a.end_date  AS DATE)  <=  ? order by users";
		list = jdbcTemplate.query(sql, new BenchReportRowMapper(), new Object[] {fromDate,toDate});	

		return list;
	}
	public List<ExportProjectAllocationReportModel> GenerateProjectAllocationReportForExporting (){
		String sql ="";
		List<ExportProjectAllocationReportModel> list = null;

		sql = "SELECT a.alloc_id,a.project_project_id as projectId,p.project_name,a.user_user_id as userId,u.user_name,CONCAT(u.first_name,' ',u.last_name) AS FullName,a.allocated_perce AS percentage, a.start_date,a.end_date, (CASE 	WHEN COALESCE(a.is_billable) IS NULL THEN '-' 	WHEN COALESCE(a.is_billable) = '1' THEN 'Yes' 	ELSE 'No' END) AS Billable FROM allocation a LEFT JOIN `user` u ON u.user_id = a.user_user_id LEFT JOIN project p ON p.project_id = a.project_project_id ORDER BY project_name,user_name";
		list = jdbcTemplate.query(sql, new ExportProjectAllocationReportRowMapper(), new Object[] {});	

		return list;
	}
	public List<ExportProjectTaskReportModel> GenerateProjectTaskReportForExporting (Date fromDate,Date toDate,Long projectId){
		String sql ="";
		List<ExportProjectTaskReportModel> list = null;

		sql = " SELECT id,CAST(`date`  AS DATE) AS taskDate,CONCAT(u.first_name,' ',u.last_name) AS resourceName,  `description` as taskDescription,hours , p.project_name as projectName,p.project_id as projectId,  (CASE 	WHEN a.is_billable IS NULL THEN '-' 	WHEN a.is_billable = '1' THEN 'Yes' 	ELSE 'No' END) AS billable  from tasktrack t  LEFT JOIN `user` u ON  u.user_id = t. user_user_id  LEFT JOIN allocation a ON a.project_project_id = t.project_project_id and a.user_user_id = t.user_user_id  LEFT JOIN project p on p.project_id = t.project_project_id  where CAST(`date`  AS DATE) BETWEEN ? AND ?  and t.project_project_id = ? order by TaskDate,ResourceName,hours";
		list = jdbcTemplate.query(sql, new ExportProjectTaskReportRowMapper(), new Object[] {fromDate,toDate,projectId});	

		return list;
	}
}
