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
	@Column(name="personelId")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long personalId;
	@OneToOne
	private UserModel user;
	private String technology,qualification;
	
	@Past
	private Date dob;
	
	private Date joiningDate;
//	private Address address;
	private long mobileNo;
	private int experience;
	private String bloodGroup;
	public long getPersonalId() {
		return personalId;
	}
	public void setPersonalId(long personalId) {
		this.personalId = personalId;
	}
	public UserModel getUser() {
		return user;
	}
	public void setUser(UserModel user) {
		this.user = user;
	}
	public String getTechnology() {
		return technology;
	}
	public void setTechnology(String technology) {
		this.technology = technology;
	}
	public String getQualification() {
		return qualification;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public Date getJoiningDate() {
		return joiningDate;
	}
	public void setJoiningDate(Date joiningDate) {
		this.joiningDate = joiningDate;
	}
	public long getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(long mobileNo) {
		this.mobileNo = mobileNo;
	}
	public int getExperience() {
		return experience;
	}
	public void setExperience(int experience) {
		this.experience = experience;
	}
	public String getBloodGroup() {
		return bloodGroup;
	}
	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}
	public UserTechnology(long personalId, String technology, String qualification, @Past Date dob, Date joiningDate,
			long mobileNo, int experience, String bloodGroup) {
		super();
		this.personalId = personalId;
		this.technology = technology;
		this.qualification = qualification;
		this.dob = dob;
		this.joiningDate = joiningDate;
		this.mobileNo = mobileNo;
		this.experience = experience;
		this.bloodGroup = bloodGroup;
	}
	

	
}
