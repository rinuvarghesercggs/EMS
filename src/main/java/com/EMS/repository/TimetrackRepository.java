package com.EMS.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.EMS.dto.Taskdetails;
import com.EMS.model.TaskModel;
import com.EMS.model.Timetrack;

public interface TimetrackRepository extends JpaRepository<Timetrack, Long> {
	
	
	@Query("SELECT p.project_name,t.taskName FROM TaskModel t JOIN ProjectModel p  on p.id = t.projectId ")
	Taskdetails getByDate(Date currentDate,Long uId);
	
	
	@Query("SELECT p.project_name,t.taskName FROM TaskModel t JOIN ProjectModel p  on p.id = t.projectId WHERE t.userId = ?2")
	JSONObject getByDatenew(Date currentDate,Long uId);

}
