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
	private int resouce_count;
	
	
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
	public Resources(long id,String department, int resouce_count) {
		super();
		this.department = department;
		this.id=id;
		this.resouce_count = resouce_count;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public int getResouce_count() {
		return resouce_count;
	}
	public void setResouce_count(int resouce_count) {
		this.resouce_count = resouce_count;
	}
	
	
}
