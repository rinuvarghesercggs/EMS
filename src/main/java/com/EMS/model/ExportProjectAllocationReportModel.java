package com.EMS.model;


public class ExportProjectAllocationReportModel {

	private long allocId;
	private long projectId;
	private String projectName ;
	private long userId;
	private String userName;
	private String FullName;
	private Double percentage;
	private String startDate;
	private String endDate;
	private String billable;
	

	public ExportProjectAllocationReportModel() {
		
	}
	
	public ExportProjectAllocationReportModel(long allocId, long projectId, String projectName, long userId, String userName, String FullName,Double percentage, String startDate,
	 String endDate, String billable) 
	{
			this.allocId = allocId;
			this.projectId = projectId;
			this.projectName = projectName;
			this.userId = userId;
			this.userName = userName;
			this.FullName = FullName;
			this.percentage = percentage;
			this.startDate = startDate;
			this.endDate = endDate;
			this.billable = billable;
		}
	
	public long getAllocId() {
		return allocId;
	}
	public void setAllocId(long allocId) {
		this.allocId = allocId;
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
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFullName() {
		return FullName;
	}
	public void setFullName(String fullName) {
		FullName = fullName;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getBillable() {
		return billable;
	}

	public void setBillable(String billable) {
		this.billable = billable;
	}

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	
	
}

