package com.EMS.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Taskdetails {
	
	private Long uId;
	private Date taskDate;
	private List<String> taskName;
	private List<String> project_name;
	
	public Long getuId() {
		return uId;
	}
	public void setuId(Long uId) {
		this.uId = uId;
	}
	public Date getTaskDate() {
		return taskDate;
	}
	public void setTaskDate(Date taskDate) {
		this.taskDate = taskDate;
	}
	public List<String> getTaskName() {
		return taskName;
	}
	public void setTaskName(List<String> taskName) {
		this.taskName = taskName;
	}
	public List<String> getProject_name() {
		return project_name;
	}
	public void setProject_name(List<String> project_name) {
		this.project_name = project_name;
	}

	
	

}
