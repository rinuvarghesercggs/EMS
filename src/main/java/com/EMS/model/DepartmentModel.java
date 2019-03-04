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
	
	private String departmentName;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getdepartmentName() {
		return departmentName;
	}

	public void setdepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	
	public DepartmentModel() {
		
	}
	
	public DepartmentModel(int id, String departmentName) {
		super();
		this.id = id;
		this.departmentName = departmentName;
	}

	@Override
	public String toString() {
		return "ID "+id+" name : "+departmentName;
	}
}
