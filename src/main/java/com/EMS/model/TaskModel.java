package com.EMS.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Task")
public class TaskModel {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "description")
	private String description;

	@Column(name = "date")
	private Date date;

	@Column(name = "hours")
	private Integer hours;

	@ManyToOne
	private ProjectModel projectId;

	@ManyToOne
	private UserModel userId;

	private String taskName;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ProjectModel getProjectId() {
		return projectId;
	}

	public void setProjectId(ProjectModel projectId) {
		this.projectId = projectId;
	}

	public UserModel getUserId() {
		return userId;
	}

	public void setUserId(UserModel userId) {
		this.userId = userId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getHours() {
		return hours;
	}

	public void setHours(Integer hours) {
		this.hours = hours;
	}
	
	


}
