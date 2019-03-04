package com.EMS.repository;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.EMS.model.TaskModel;


public interface TaskRepository extends JpaRepository<TaskModel, Long> {
	
	@Query("SELECT t.taskName FROM TaskModel t")
	List<String> getTaskName();
	
	
	@Query("SELECT t.taskName FROM TaskModel t WHERE t.user=?1")
	List<String> getTaskByUser(Long id);
	
	
	@Query("SELECT t FROM TaskModel t WHERE t.date =?1 and t.user.id=?2")
	List<TaskModel> getByDate(Date currentDate,Long id);

}
