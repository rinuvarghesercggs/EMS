package com.EMS.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	private ProjectModel project;

//	@ManyToMany
//	private List<UserModel> userModel;
	
	@ManyToOne
	private UserModel user;
	
	private Double allocatedPerce;
	
	private Double freeAllocation;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
	public ProjectModel getproject() {
		return project;
	}
	public void setproject(ProjectModel project) {
		this.project = project;
	}
	
	
//	public List<UserModel> getuser() {
//		return user;
//	}
//	public void setuser(List<UserModel> user) {
//		this.user = user;
//	}
	
	
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
	
	public UserModel getuser() {
		return user;
	}
	public void setuser(UserModel user) {
		this.user = user;
	}
	
	
	
	
	
	
}
