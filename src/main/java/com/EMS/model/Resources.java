package com.EMS.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Resources")
public class Resources {
	
	
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String department;
	private int resourceCount;
	private long project;
	
	public long getProject() {
		return project;
	}

	public void setProject(long project) {
		this.project = project;
	}

	public Resources() {
		
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDepartment() {
		return department;
	}
	public Resources(long id,String department, int resourceCount) {
		super();
		this.department = department;
		this.id=id;
		this.resourceCount = resourceCount;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public int getresourceCount() {
		return resourceCount;
	}
	public void setresourceCount(int resourceCount) {
		this.resourceCount = resourceCount;
	}
	
	
}
