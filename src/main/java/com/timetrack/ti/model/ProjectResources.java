package com.timetrack.ti.model;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class ProjectResources {
	

	@ManyToOne
	private DepartmentModel department;
	
	private int resouce_count;


	public DepartmentModel getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentModel department) {
		this.department = department;
	}

	public int getResouce_Count() {
		return resouce_count;
	}

	public void setResouce_Count(int resouce_count) {
		this.resouce_count = resouce_count;
	}

	public ProjectResources() {
		
	}
	
	
}
