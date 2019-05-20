package com.EMS.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.EMS.model.BenchProjectReportModel;
import com.EMS.model.ExportProjectTaskReportModel;
import com.EMS.model.ProjectModel;
import com.EMS.model.ProjectReportModel;
import com.EMS.model.TaskTrackApproval;
import com.EMS.utility.BenchReportRowMapper;
import com.EMS.utility.DbConnectionUtility;
import com.EMS.utility.ExportProjectTaskReportRowMapper;
import com.EMS.utility.JsonNodeRowMapper;
import com.EMS.utility.ReportRowMapper;
import com.EMS.utility.TimeTrackApprovalRowMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class TimeTrackApprovalRepository extends DbConnectionUtility {


	public List<TaskTrackApproval> getUserListForApproval (Long id,Long projectId,Integer monthIndex,Integer yearIndex){
	
		List<TaskTrackApproval> list = null;
				
		String sql = "SELECT id,u.first_name as firstName,u.last_name as lastName,TA.project_type as projectType,updated_by as updatedBy,day1,day2,day3,day4,day5,day6,day7,day8,day9,day10,day11,day12,"+
		"day13,day14,day15,day16,day17,day18,day19,day20,day21,day22,day23,day24,day25,day26,day27,day28,day29,day30,day31"+
		" FROM tasktrack_approval TA LEFT JOIN user u ON u.user_id = TA.user_user_id where TA.month =? and TA.year=? and TA.project_project_id = ? and TA.user_user_id=?";
	
		list = jdbcTemplate.query(sql, new TimeTrackApprovalRowMapper(), new Object[] {monthIndex,yearIndex,projectId,id});	
		
		return list;
	}
}
