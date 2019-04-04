package com.EMS.model;


public class BenchProjectReportModel {

	private String userName;
	private int isBillable;
	private String  allocatedPerce;
	private String projectName ;
	private String departmentId;
	private String departmentName;
	private String userId;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getIsBillable() {
		return isBillable;
	}
	public void setIsBillable(int isBillable) {
		this.isBillable = isBillable;
	}
	public String getAllocatedPerce() {
		return allocatedPerce;
	}
	public void setAllocatedPerce(String allocatedPerce) {
		this.allocatedPerce = allocatedPerce;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}


	
}

