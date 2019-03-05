package com.EMS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.EMS.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
	
	@Query("SELECT t.id,t.taskName FROM Task t")
	List<Object[]> getTaskNameId();

}
