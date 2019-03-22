package com.EMS.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EMS.model.AllocationModel;
import com.EMS.model.ProjectModel;
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
	public List<Tasktrack> getByDate(Date startDate, Date endDate, Long uId) {
		List<Tasktrack> list = tasktrackRepository.getByDate(startDate, endDate, uId);

		LocalDate localStartDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate localEndDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		for (LocalDate date = localStartDate; date.isBefore(localEndDate)
				| date.isEqual(localEndDate); date = date.plusDays(1)) {
			LocalDate locaDate = date;
			Tasktrack obj = list.stream()
					.filter(taskTrack -> locaDate
							.equals(taskTrack.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()))
					.findAny().orElse(null);
			if (obj == null) {
				Tasktrack tasktrack = new Tasktrack();
				tasktrack.setDate(Date.from(locaDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
				list.add(tasktrack);
			}
		}

		Collections.sort(list);

		return list;

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
		// return tasktrackRepository.getTasks();
		return new ArrayList<Tasktrack>();
	}

	public boolean updateTaskById(Tasktrack task) {
		boolean result = false;
		try {
			tasktrackRepository.updateTaskById(task.getDescription(), task.getId(), task.getDate(), task.getHours(),
					task.getProject(), task.getTask());
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
		} catch (Exception exc) {
			exc.printStackTrace();
		}

		return result;
	}

	public boolean createTask(Tasktrack task) {
		boolean result = false;
		try {
			tasktrackRepository.createTask(task);
			result = true;
		} catch (Exception exc) {
		}

		return result;
	}

	public List<AllocationModel> getProjectNames(long uId) {
		try {
			return tasktrackRepository.getProjectNames(uId);
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<AllocationModel>();
		}
	}

	public List<Task> getTaskCategory(long uId) {
		try {
			return tasktrackRepository.getTaskCategories(uId);
		} catch (Exception exc) {
			exc.printStackTrace();
			return new ArrayList<Task>();
		}
	}

	public ProjectModel getProjectModelById(long id) {
		return tasktrackRepository.getProjectById(id);
	}
}
