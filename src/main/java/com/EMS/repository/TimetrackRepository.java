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
	
	
	
	
	@Query("SELECT t FROM Timetrack t WHERE t.date =?1 ")
	List<Timetrack>getByDate(Date currentDate);

}
