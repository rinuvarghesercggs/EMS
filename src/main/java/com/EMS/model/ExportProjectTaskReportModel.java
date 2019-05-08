package com.EMS.model;


public class ExportProjectTaskReportModel {

	private String projectName ;
	private String taskDate;
	private String resourceName;
	private Double hours;
	private String taskDescription;
	private String billable;
	private String taskCategory;
	
	
	public ExportProjectTaskReportModel() {
		
	}
	
	public ExportProjectTaskReportModel( String projectName, String resourceName, String taskDate,Double hours,String taskCategory, String taskDescription, String billable) 
	{

			this.projectName = projectName;
			this.taskDate = taskDate;
			this.resourceName = resourceName;
			this.hours = hours;
			this.taskCategory = taskCategory;
			this.taskDescription = taskDescription;
			this.billable = billable;
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

	public String getTaskCategory() {
		return taskCategory;
	}

	public void setTaskCategory(String taskCategory) {
		this.taskCategory = taskCategory;
	}
	
	
	
}

