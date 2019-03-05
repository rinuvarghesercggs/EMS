package com.EMS.service;

import java.util.Date;
import java.util.List;

import com.EMS.model.Task;
import com.EMS.model.Tasktrack;

public interface TasktrackService {

//	For Task track Model	
	List<Tasktrack> getByDate(Date currentDate, Long uId);

	Tasktrack saveTaskDetails(Tasktrack task);

//	For Task Model
	List<Object[]> getTaskList();

	Task getTaskById(Long taskId);

}
