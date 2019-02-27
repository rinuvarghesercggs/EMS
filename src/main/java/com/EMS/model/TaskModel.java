package com.EMS.model;


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
@Table(name="Task")
public class TaskModel {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	private ProjectModel projectId;
	
	@ManyToMany(targetEntity=UserModel.class)
	private Set userId;
	
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

	public Set getUserId() {
		return userId;
	}

	public void setUserId(Set userId) {
		this.userId = userId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public TaskModel(long id,String taskName,Set userId) {
		super();
		this.userId=userId;
		this.id = id;
		this.taskName = taskName;
	}
	
	public TaskModel() {

	}
	
	
}
