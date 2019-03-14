package com.EMS.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;



@Entity
@Table(name = "User")
public class UserModel {

	@Id
	@Column(name = "userId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;
	
	@Column(unique = true)
	private long empId;

	@ManyToOne
	private DepartmentModel department;

	@ManyToOne
	private RoleModel role;
	
	@Size(min=2,max=35)
	private String firstName, lastName,password;
	
	@NotNull @Email
	@Size(min=10,max=50)
	private String email;
	
	@Column(unique = true)
	@Size(min=2,max=35)
	private String  userName;
	private long contact;

	@Past
	private Date dob;
	
	

	private Date joiningDate;
	private String bloodGroup;
	private String qualification;

//	private Address address;
//	private long mobileNo;
	

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Date getJoiningDate() {
		return joiningDate;
	}
	
	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public void setJoiningDate(Date joiningDate) {
		this.joiningDate = joiningDate;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public long getEmpId() {
		return empId;
	}

	public void setEmpId(long empId) {
		this.empId = empId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public DepartmentModel getdepartment() {
		return department;
	}

	public void setdepartment(DepartmentModel department) {
		this.department = department;
	}

	public RoleModel getrole() {
		return role;
	}

	public void setrole(RoleModel role) {
		this.role = role;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getContact() {
		return contact;
	}

	public void setContact(long contact) {
		this.contact = contact;
	}

	public UserModel() {

	}

	public UserModel(long userId, String firstName, String lastName, String userName, String password, String email,
			long contact,long empId) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.empId=empId;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.contact = contact;
	}

}
