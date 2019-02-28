package com.EMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.EMS.model.TaskModel;


public interface TaskRepository extends JpaRepository<TaskModel, Long> {

}
