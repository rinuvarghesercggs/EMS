package com.EMS.model;

import java.util.Date;
import java.util.List;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name="Project")
public class ProjectModel {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Cascade(CascadeType.ALL)
	private long id;
	
	private String projectName,projectOwner,projectDetails,contractType;
	private int estimatedHours;
	private Date startDate,endDate;
	
//	@OneToMany(targetEntity=Resources.class)
//	private List<Resources> resources;
//	
	
	public ProjectModel() {
		
	}
	public long getId() {
		return id;
	}

//	public List<Resources> getResources() {
//		return resources;
//	}
//	public void setResources(List<Resources> resources) {
//		this.resources = resources;
//	}
	public ProjectModel(long id, String projectName, String projectOwner, String projectDetails,
			String contractType, int estimatedHours, Date startDate, Date endDate) {
		super();
		this.id = id;
		this.projectName = projectName;
		this.projectOwner = projectOwner;
		this.projectDetails = projectDetails;
		this.contractType = contractType;
		this.estimatedHours = estimatedHours;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	public String getprojectOwner() {
		return projectOwner;
	}
	@Override
	public String toString() {
		return "ProjectModel [id=" + id + ", projectName=" + projectName + ", projectOwner=" + projectOwner
				+ ", projectDetails=" + projectDetails + ", contractType=" + contractType + ", estimatedHours="
				+ estimatedHours + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}
	public void setprojectOwner(String projectOwner) {
		this.projectOwner = projectOwner;
	}
	public String getprojectDetails() {
		return projectDetails;
	}
	public void setprojectDetails(String projectDetails) {
		this.projectDetails = projectDetails;
	}
	public String getcontractType() {
		return contractType;
	}
	public void setcontractType(String contractType) {
		this.contractType = contractType;
	}
	public int getestimatedHours() {
		return estimatedHours;
	}
	public void setestimatedHours(int estimatedHours) {
		this.estimatedHours = estimatedHours;
	}
	
	
	public void setId(long id) {
		this.id = id;
	}
	public String getprojectName() {
		return projectName;
	}
	public void setprojectName(String projectName) {
		this.projectName = projectName;
	}
	public Date getstartDate() {
		return startDate;
	}
	public void setstartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getendDate() {
		return endDate;
	}
	public void setendDate(Date endDate) {
		this.endDate = endDate;
	}
	
	
}
