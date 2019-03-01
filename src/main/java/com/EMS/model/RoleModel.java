package com.EMS.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Role")
public class RoleModel {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String roleName;
	
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getroleName() {
		return roleName;
	}
	public void setroleName(String roleName) {
		this.roleName = roleName;
	}
	public RoleModel(long id, String roleName) {
		super();
		this.id = id;
		this.roleName = roleName;
	}
	
	public RoleModel() {
		
	}
	
}
