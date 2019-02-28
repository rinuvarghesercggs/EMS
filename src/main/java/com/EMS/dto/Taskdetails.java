package com.EMS.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Taskdetails {
	
	private String uname;
	private Date taskDate;
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public Date getTaskDate() {
		return taskDate;
	}
	public void setTaskDate(Date taskDate) {
		this.taskDate = taskDate;
	}

}
