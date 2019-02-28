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

@Entity
@Table(name="Project")
public class ProjectModel {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String project_name,project_owner,project_details,contract_type;
	private int estimated_hours;
	private Date start_date,end_date;
	
	@ManyToMany
	private List<DepartmentModel> department_id;
	private int resource_count;
	
	public ProjectModel() {
		
	}
	public long getId() {
		return id;
	}

	public ProjectModel(long id, String project_name, String project_owner, String project_details,
			String contract_type, int estimated_hours, Date start_date, Date end_date,int resource_count) {
		super();
		this.id = id;
		this.project_name = project_name;
		this.project_owner = project_owner;
		this.project_details = project_details;
		this.resource_count=resource_count;
		this.contract_type = contract_type;
		this.estimated_hours = estimated_hours;
		this.start_date = start_date;
		this.end_date = end_date;
	}
	public String getProject_owner() {
		return project_owner;
	}
	@Override
	public String toString() {
		return "ProjectModel [id=" + id + ", project_name=" + project_name + ", project_owner=" + project_owner
				+ ", project_details=" + project_details + ", contract_type=" + contract_type + ", estimated_hours="
				+ estimated_hours + ", start_date=" + start_date + ", end_date=" + end_date + "]";
	}
	public void setProject_owner(String project_owner) {
		this.project_owner = project_owner;
	}
	public String getProject_details() {
		return project_details;
	}
	public void setProject_details(String project_details) {
		this.project_details = project_details;
	}
	public String getContract_type() {
		return contract_type;
	}
	public void setContract_type(String contract_type) {
		this.contract_type = contract_type;
	}
	public int getEstimated_hours() {
		return estimated_hours;
	}
	public void setEstimated_hours(int estimated_hours) {
		this.estimated_hours = estimated_hours;
	}
	
	public List<DepartmentModel> getDepartment_id() {
		return department_id;
	}
	public void setDepartment_id(List<DepartmentModel> department_id) {
		this.department_id = department_id;
	}
	public int getResource_count() {
		return resource_count;
	}
	public void setResource_count(int resource_count) {
		this.resource_count = resource_count;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getProject_name() {
		return project_name;
	}
	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	
	
}
