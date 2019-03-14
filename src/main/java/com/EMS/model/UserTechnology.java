package com.EMS.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Past;

import org.hibernate.annotations.GeneratorType;

@Entity
@Table(name="userTechnology")
public class UserTechnology {
	
	@Id
	@Column(name="userTechnologyId")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long userTechnologyId;
	@OneToOne
	private UserModel user;
	private long technology;
	private int experience;
	
	
	public UserTechnology(long userTechnologyId, long technology, int experience) {
		super();
		this.userTechnologyId = userTechnologyId;
		this.technology = technology;
		this.experience = experience;
	}
	public long getUserTechnologyId() {
		return userTechnologyId;
	}
	public void setUserTechnologyId(long userTechnologyId) {
		this.userTechnologyId = userTechnologyId;
	}
	public UserModel getUser() {
		return user;
	}
	public void setUser(UserModel user) {
		this.user = user;
	}
	public long getTechnology() {
		return technology;
	}
	public void setTechnology(long technology) {
		this.technology = technology;
	}
	public int getExperience() {
		return experience;
	}
	public void setExperience(int experience) {
		this.experience = experience;
	}
	
	
	
	
	
}
