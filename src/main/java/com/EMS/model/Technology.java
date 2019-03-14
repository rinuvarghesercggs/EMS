package com.EMS.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="technology")
public class Technology {

	@Id
	@Column(name="technology")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long technologyId;
	private String technologyName;
	
	
	public long getTechnologyId() {
		return technologyId;
	}
	public void setTechnologyId(long technologyId) {
		this.technologyId = technologyId;
	}
	public String getTechnologyName() {
		return technologyName;
	}
	public void setTechnologyName(String technologyName) {
		this.technologyName = technologyName;
	}
	
	
	public Technology(long technologyId, String technologyName) {
		super();
		this.technologyId = technologyId;
		this.technologyName = technologyName;
	}
	
	
}
