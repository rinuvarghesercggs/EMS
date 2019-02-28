package com.EMS.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="Department")
public class DepartmentModel {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String department_name;

	public long getId() {
		return id;
	}

	@ManyToMany
	private List<ProjectModel> project;
	
	public List<ProjectModel> getProject() {
		return project;
	}

	public void setProject(List<ProjectModel> project) {
		this.project = project;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDepartment_name() {
		return department_name;
	}

	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}
	
	public DepartmentModel() {
		
	}
	
	public DepartmentModel(int id, String department_name) {
		super();
		this.id = id;
		this.department_name = department_name;
	}

	@Override
	public String toString() {
		return "ID "+id+" name : "+department_name;
	}
}
