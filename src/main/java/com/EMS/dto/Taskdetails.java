package com.EMS.dto;

import java.util.Date;
import java.util.List;


public class Taskdetails {
	
	private Long uId;
	private Date taskDate;
	private List<String> taskName;
	private List<String> description;
	
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
	public List<String> getDescription() {
		return description;
	}
	public void setDescription(List<String> description) {
		this.description = description;
	}
	

	
	

}
