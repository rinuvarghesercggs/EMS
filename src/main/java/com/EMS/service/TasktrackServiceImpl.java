package com.EMS.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EMS.model.ProjectModel;
import com.EMS.model.Task;
import com.EMS.model.TaskCategory;
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

	@Override
	public List<Tasktrack> getTasks() {
		//return tasktrackRepository.getTasks();
		return new ArrayList<Tasktrack>();
	}

	public boolean updateTaskById(Tasktrack task) {
		boolean result = false;
		try {
			System.out.println(task.getId());
			System.out.println(task.getDescription());
			tasktrackRepository.updateTaskById(task.getDescription(),task.getId(),task.getDate());
			result = true;
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		
		return result;
	}

	public boolean deleteTaskById(long id) {
		boolean result = false;
		try {
			tasktrackRepository.deleteTaskById(id);
			result = true;
		}catch(Exception exc) {
			exc.printStackTrace();
		}
		
		return result;
	}

	public boolean createTask(Tasktrack task) {
		boolean result = false;
		try {
			tasktrackRepository.createTask(task);
			result = true;
		}catch(Exception exc) {
		}
		
		return result;
	}

	public List<ProjectModel> getProjectNames() {
      try {
		return tasktrackRepository.getProjectNames();
	} catch (Exception e) {
		return new ArrayList<ProjectModel>();
	}
	}

	public List<Task> getTaskCategory() {
     try {
    	 return tasktrackRepository.getTaskCategories();
     }catch(Exception exc) {
    	 exc.printStackTrace();
    	 return new ArrayList<Task>();
     }
	}
}
