package com.EMS.service;

import java.util.Date;
import java.util.List;

import com.EMS.model.Tasktrack;

public interface TasktrackService {
	
	List<Object[]> getTaskList();
	List<String> getTaskByUserId(Long uId);
	List<Tasktrack> getByDate(Date currentDate,Long uId);
	Tasktrack saveTaskDetails(Tasktrack task);

}
