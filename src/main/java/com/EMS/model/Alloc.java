package com.EMS.model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="Alloc")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Alloc {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	@ManyToOne
	private ProjectModel projectModel;
	
	@ManyToOne
	private DepartmentModel departmentModel;

	@ManyToMany
	private List<UserModel> userModel;
	
	private Double allocatedPerce;
	
	private Double freeAllocation;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
	public ProjectModel getProjectModel() {
		return projectModel;
	}
	public void setProjectModel(ProjectModel projectModel) {
		this.projectModel = projectModel;
	}
	
	
	public DepartmentModel getDepartmentModel() {
		return departmentModel;
	}
	public void setDepartmentModel(DepartmentModel departmentModel) {
		this.departmentModel = departmentModel;
	}
	
	
	public List<UserModel> getUserModel() {
		return userModel;
	}
	public void setUserModel(List<UserModel> userModel) {
		this.userModel = userModel;
	}
	
	
	public Double getAllocatedPerce() {
		return allocatedPerce;
	}
	public void setAllocatedPerce(Double allocatedPerce) {
		this.allocatedPerce = allocatedPerce;
	}
	public Double getFreeAllocation() {
		return freeAllocation;
	}
	public void setFreeAllocation(Double freeAllocation) {
		this.freeAllocation = freeAllocation;
	}
	
	
	
	
	
	
}
