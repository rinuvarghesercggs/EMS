package com.EMS.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EMS.model.Tasktrack;
import com.EMS.repository.TasktrackRepository;

@Service
public class TasktrackServiceImpl implements TasktrackService {
	
	@Autowired
	TasktrackRepository tasktrackRepository;
	
	@Override
	public List<Object[]> getTaskList() {
		List<Object[]> taskList = tasktrackRepository.getTaskNameId();
		return taskList;
	}
	
	@Override
	public List<String> getTaskByUserId(Long id) {
		List<String> taskByUser= tasktrackRepository.getTaskByUser(id);
		return taskByUser;
	}
	@Override
	public List<Tasktrack> getByDate(Date currentDate,Long uId){
		return  tasktrackRepository.getByDate(currentDate,uId);
				
	}
	@Override
	public Tasktrack saveTaskDetails(Tasktrack task){
		return  tasktrackRepository.save(task);
				
	}
	


}
