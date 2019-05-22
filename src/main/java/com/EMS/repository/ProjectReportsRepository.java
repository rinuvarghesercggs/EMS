package com.EMS.repository;

import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.EMS.model.ApprovalTimeTrackReportModel;
import com.EMS.model.BenchProjectReportModel;
import com.EMS.model.ExportProjectHourReportModel;
import com.EMS.model.ExportProjectTaskReportModel;
import com.EMS.model.ProjectReportModel;
import com.EMS.utility.ApprovalTimeTrackReportRowMapper;
import com.EMS.utility.BenchReportRowMapper;
import com.EMS.utility.DbConnectionUtility;
import com.EMS.utility.ExportProjectHourReportRowMapper;
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
	public List<ExportProjectTaskReportModel> GenerateProjectTaskReportForExporting (Date fromDate,Date toDate,Long projectId){
		String sql ="";
		List<ExportProjectTaskReportModel> list = null;

		//sql = " SELECT id,CAST(`date`  AS DATE) AS taskDate,CONCAT(u.first_name,' ',u.last_name) AS resourceName,  `description` as taskDescription,hours , p.project_name as projectName,p.project_id as projectId,  (CASE 	WHEN a.is_billable IS NULL THEN '-' 	WHEN a.is_billable = '1' THEN 'Yes' 	ELSE 'No' END) AS billable  from tasktrack t  LEFT JOIN `user` u ON  u.user_id = t. user_user_id  LEFT JOIN allocation a ON a.project_project_id = t.project_project_id and a.user_user_id = t.user_user_id  LEFT JOIN project p on p.project_id = t.project_project_id  where CAST(`date`  AS DATE) BETWEEN ? AND ?  and t.project_project_id = ? order by TaskDate,ResourceName,hours";
		sql = "SELECT CAST(`date`  AS DATE) AS taskDate,CONCAT(u.first_name,' ',u.last_name) AS resourceName,tm.task_name as taskCategory,  `description` as taskDescription,hours , p.project_name as projectName,  (CASE 	WHEN a.is_billable IS NULL THEN '-' 	WHEN a.is_billable = '1' THEN 'Yes' 	ELSE 'No' END) AS billable  from tasktrack t  LEFT JOIN `user` u ON  u.user_id = t. user_user_id  LEFT JOIN allocation a ON a.project_project_id = t.project_project_id and a.user_user_id = t.user_user_id  LEFT JOIN project p on p.project_id = t.project_project_id  LEFT JOIN task_master tm on tm.id = t.task_id  where CAST(`date`  AS DATE) BETWEEN ? AND ?  and t.project_project_id = ? order by TaskDate,ResourceName,hours";
		list = jdbcTemplate.query(sql, new ExportProjectTaskReportRowMapper(), new Object[] {fromDate,toDate,projectId});	

		return list;
	}
	public List<ApprovalTimeTrackReportModel> getApprovalStatusReportDetails (Date startDate,Date endDate,int monthIndex,int yearIndex){
		String sql ="";
		List<ApprovalTimeTrackReportModel> list = null;

		sql = "SELECT projectName,hours,label FROM (SELECT p.project_name AS projectName, SUM((COALESCE(day1,0)+COALESCE(day2,0)+COALESCE(day3,0)+COALESCE(day4,0)+COALESCE(day5,0)+COALESCE(day6,0)+COALESCE(day7,0)+COALESCE(day8,0)+COALESCE(day9,0)+COALESCE(day10,0) +COALESCE(day11,0)+COALESCE(day12,0)+COALESCE(day13,0)+COALESCE(day14,0)+COALESCE(day15,0)+COALESCE(day16,0)+COALESCE(day17,0)+COALESCE(day18,0)+COALESCE(day19,0)+COALESCE(day20,0) +COALESCE(day21,0)+COALESCE(day22,0)+COALESCE(day23,0)+COALESCE(day24,0)+COALESCE(day25,0)+COALESCE(day26,0)+COALESCE(day27,0)+COALESCE(day28,0)+COALESCE(day29,0)+COALESCE(day30,0) +COALESCE(day31,0))) AS hours, 'approved' AS label FROM tasktrack_approval ta LEFT JOIN `user` u ON u.user_id = ta.user_user_id LEFT JOIN project p ON p.project_id = ta.project_project_id WHERE ta.month=? AND ta.year=? AND ta.project_type ='Billable' GROUP BY 1,3 UNION SELECT p.project_name AS projectName,SUM(tt.hours) AS hours,'logged' AS label FROM tasktrack tt LEFT JOIN `user` u ON u.user_id = tt.user_user_id LEFT JOIN project p ON p.project_id = tt.project_project_id WHERE CAST(tt.`date`  AS DATE) BETWEEN ? AND ? GROUP BY 1,3 ) t ORDER BY 1,2,3";
		list = jdbcTemplate.query(sql, new ApprovalTimeTrackReportRowMapper(), new Object[] {monthIndex,yearIndex,startDate,endDate});	

		return list;
	}
	public List<ExportProjectHourReportModel> GenerateProjectHourReportForExporting (Date fromDate,Date toDate,int monthIndex, int yearIndex){
		String sql ="";
		List<ExportProjectHourReportModel> list = null;

		//sql = " SELECT id,CAST(`date`  AS DATE) AS taskDate,CONCAT(u.first_name,' ',u.last_name) AS resourceName,  `description` as taskDescription,hours , p.project_name as projectName,p.project_id as projectId,  (CASE 	WHEN a.is_billable IS NULL THEN '-' 	WHEN a.is_billable = '1' THEN 'Yes' 	ELSE 'No' END) AS billable  from tasktrack t  LEFT JOIN `user` u ON  u.user_id = t. user_user_id  LEFT JOIN allocation a ON a.project_project_id = t.project_project_id and a.user_user_id = t.user_user_id  LEFT JOIN project p on p.project_id = t.project_project_id  where CAST(`date`  AS DATE) BETWEEN ? AND ?  and t.project_project_id = ? order by TaskDate,ResourceName,hours";
		sql = "SELECT ems1.projectName, ems1.firstName, ems1.lastName, COALESCE(SUM(ems1.approved),0) AS 'approved', SUM(ems1.logged) AS 'logged' FROM ( SELECT ems.projectName,ems.firstName, ems.lastName, CASE WHEN ems.label = 'approved' THEN hours END AS 'approved', CASE WHEN ems.label = 'logged' THEN hours END AS 'logged' FROM ( SELECT p.project_name AS projectName,u.first_name AS firstName, u.last_name AS lastName, SUM((COALESCE(day1,0)+COALESCE(day2,0)+COALESCE(day3,0)+COALESCE(day4,0)+COALESCE(day5,0)+COALESCE(day6,0)+COALESCE(day7,0)+COALESCE(day8,0)+COALESCE(day9,0)+COALESCE(day10,0) +COALESCE(day11,0)+COALESCE(day12,0)+COALESCE(day13,0)+COALESCE(day14,0)+COALESCE(day15,0)+COALESCE(day16,0)+COALESCE(day17,0)+COALESCE(day18,0)+COALESCE(day19,0)+COALESCE(day20,0) +COALESCE(day21,0)+COALESCE(day22,0)+COALESCE(day23,0)+COALESCE(day24,0)+COALESCE(day25,0)+COALESCE(day26,0)+COALESCE(day27,0)+COALESCE(day28,0)+COALESCE(day29,0)+COALESCE(day30,0) +COALESCE(day31,0))) AS hours, 'approved' AS label FROM tasktrack_approval ta LEFT JOIN `user` u ON u.user_id = ta.user_user_id LEFT JOIN project p ON p.project_id = ta.project_project_id WHERE ta.month=? AND ta.year=? AND ta.project_type ='Billable' GROUP BY 1,2,3,5 UNION  SELECT p.project_name AS projectName,u.first_name AS firstName, u.last_name AS lastName,SUM(tt.hours) AS hours,'logged' AS label FROM tasktrack tt LEFT JOIN `user` u ON u.user_id = tt.user_user_id LEFT JOIN project p ON p.project_id = tt.project_project_id WHERE CAST(tt.`date`  AS DATE) BETWEEN ? AND ? GROUP BY 1,2,3,5 ) ems )ems1 GROUP BY 1,2,3";
		list = jdbcTemplate.query(sql, new ExportProjectHourReportRowMapper(), new Object[] {monthIndex,yearIndex,fromDate,toDate});	

		return list;
	}
	
}
