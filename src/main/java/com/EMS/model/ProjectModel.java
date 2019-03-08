package com.EMS.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name="Project")
public class ProjectModel {
	
	@Id
	@Column(name="projectId")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Cascade(CascadeType.ALL)
	private long projectId;
	
	private String projectName,projectDetails;
	private int estimatedHours;
	private Date startDate,endDate;
	private int billable;
	private String projectCode;
	private int phase;
	private int type;
	
	
	@ManyToOne
	private UserModel projectOwner;
	
	@ManyToOne
	private ContractModel contract;

	
	
	
	
	
	public ProjectModel(long projectId, String projectName, String projectDetails, int estimatedHours, Date startDate,
			Date endDate, int billable, String projectCode, int phase, int type, UserModel projectOwner,
			ContractModel contract) {
		super();
		this.projectId = projectId;
		this.projectName = projectName;
		this.projectDetails = projectDetails;
		this.estimatedHours = estimatedHours;
		this.startDate = startDate;
		this.endDate = endDate;
		this.billable = billable;
		this.projectCode = projectCode;
		this.phase = phase;
		this.type = type;
		this.projectOwner = projectOwner;
		this.contract = contract;
	}
	
	public ProjectModel() {
		
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

	public String getProjectDetails() {
		return projectDetails;
	}

	public void setProjectDetails(String projectDetails) {
		this.projectDetails = projectDetails;
	}

	public int getEstimatedHours() {
		return estimatedHours;
	}

	public void setEstimatedHours(int estimatedHours) {
		this.estimatedHours = estimatedHours;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getBillable() {
		return billable;
	}

	public void setBillable(int billable) {
		this.billable = billable;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public int getPhase() {
		return phase;
	}

	public void setPhase(int phase) {
		this.phase = phase;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public UserModel getProjectOwner() {
		return projectOwner;
	}

	public void setProjectOwner(UserModel projectOwner) {
		this.projectOwner = projectOwner;
	}

	public ContractModel getContract() {
		return contract;
	}

	public void setContract(ContractModel contract) {
		this.contract = contract;
	}
	


	
}
