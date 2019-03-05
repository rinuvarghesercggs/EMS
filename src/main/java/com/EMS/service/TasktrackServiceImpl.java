package com.EMS.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EMS.model.Task;
import com.EMS.model.Tasktrack;
import com.EMS.repository.TaskRepository;
import com.EMS.repository.TasktrackRepository;

@Service
public class TasktrackServiceImpl implements TasktrackService {

	@Autowired
	TasktrackRepository tasktrackRepository;
	@Autowired
	TaskRepository taskRepository;

//	For Task track Model

	@Override
	public List<Tasktrack> getByDate(Date currentDate, Long uId) {
		return tasktrackRepository.getByDate(currentDate, uId);

	}

	@Override
	public Tasktrack saveTaskDetails(Tasktrack task) {
		return tasktrackRepository.save(task);

	}
//	For Task Model

	@Override
	public List<Object[]> getTaskList() {
		List<Object[]> taskList = taskRepository.getTaskNameId();
		return taskList;
	}

	@Override
	public Task getTaskById(Long taskId) {
		Task task = taskRepository.getOne(taskId);
		return task;
	}

}
