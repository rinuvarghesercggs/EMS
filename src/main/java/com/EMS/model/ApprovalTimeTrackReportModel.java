package com.EMS.model;


public class ApprovalTimeTrackReportModel {

	private String projectName ;
	private Double billableHours;
	private Double loggedHours;

	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Double getBillableHours() {
		return billableHours;
	}
	public void setBillableHours(Double billableHours) {
		this.billableHours = billableHours;
	}
	public Double getLoggedHours() {
		return loggedHours;
	}
	public void setLoggedHours(Double loggedHours) {
		this.loggedHours = loggedHours;
	}
	
	
}

