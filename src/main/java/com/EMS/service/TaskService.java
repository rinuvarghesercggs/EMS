package com.EMS.service;

import java.util.List;

public interface TaskService {
	
	List<String> getTaskList();
	List<String> getTaskByUserId(Long uId);

}
