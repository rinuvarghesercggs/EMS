package com.EMS.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EMS.model.TaskModel;
import com.EMS.repository.TaskRepository;

@Service
public class TaskServiceImpl implements TaskService {
	
	@Autowired
	TaskRepository taskRepository;
	
	@Override
	public List<Object[]> getTaskList() {
		List<Object[]> taskList = taskRepository.getTaskNameId();
		return taskList;
	}
	
	@Override
	public List<String> getTaskByUserId(Long id) {
		List<String> taskByUser= taskRepository.getTaskByUser(id);
		return taskByUser;
	}
	@Override
	public List<TaskModel> getByDate(Date currentDate,Long uId){
		return  taskRepository.getByDate(currentDate,uId);
				
	}
	@Override
	public TaskModel saveTaskDetails(TaskModel task){
		return  taskRepository.save(task);
				
	}
	


}
