package com.EMS.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Taskdetails {
	
	private Long uId;
	private Date taskDate;
	
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

}
