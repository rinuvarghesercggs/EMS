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
	public List<String> getTaskList() {
		List<String> taskList = taskRepository.getTaskName();
		return taskList;
	}
	
	@Override
	public List<String> getTaskByUserId(Long id) {
		List<String> taskByUser= taskRepository.getTaskByUser(id);
		return taskByUser;
	}
	
	public List<TaskModel> getByDate(Date currentDate,Long uId){
		return  taskRepository.getByDate(currentDate,uId);
				
	}


}
