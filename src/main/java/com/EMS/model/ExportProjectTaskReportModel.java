package com.EMS.model;


public class ExportProjectTaskReportModel {

	private long id;
	private long projectId;
	private String projectName ;
	private String taskDate;
	private String resourceName;
	private Double hours;
	private String taskDescription;
	private String billable;

	public ExportProjectTaskReportModel() {
		
	}
	
	public ExportProjectTaskReportModel(long id, long projectId, String projectName, String resourceName, String taskDate,Double hours, String taskDescription, String billable) 
	{
			this.id = id;
			this.projectId = projectId;
			this.projectName = projectName;
			this.taskDate = taskDate;
			this.resourceName = resourceName;
			this.hours = hours;
			this.taskDescription = taskDescription;
			this.billable = billable;
		}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getTaskDate() {
		return taskDate;
	}

	public void setTaskDate(String taskDate) {
		this.taskDate = taskDate;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public Double getHours() {
		return hours;
	}

	public void setHours(Double hours) {
		this.hours = hours;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public String getBillable() {
		return billable;
	}

	public void setBillable(String billable) {
		this.billable = billable;
	}
	
	
	
}

