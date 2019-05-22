package com.EMS.model;


public class ExportProjectHourReportModel {

	private String projectName ;
	private String firstName;
	private String lastName;
	private Double logged;
	private Double approved;
		
	
	public ExportProjectHourReportModel() {
		
	}
	
	public ExportProjectHourReportModel( String projectName, String firstName, String lastName,Double approved,Double logged) 
	{

			this.projectName = projectName;
			this.firstName = firstName;
			this.lastName = lastName;
			this.logged = logged;
			this.approved = approved;
		}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Double getLogged() {
		return logged;
	}

	public void setLogged(Double logged) {
		this.logged = logged;
	}

	public Double getApproved() {
		return approved;
	}

	public void setApproved(Double approved) {
		this.approved = approved;
	}

}

