package com.EMS.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;



@Entity
@Table(name = "User")
public class UserModel {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	private DepartmentModel department;

	@ManyToOne
	private RoleModel role;
	private String firstName, lastName,password, email;
	
	@Column(unique = true)
	private String  userName;
	
	private int contact;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public int getContact() {
		return contact;
	}

	public void setContact(int contact) {
		this.contact = contact;
	}

	public UserModel() {

	}

	public UserModel(long id, String firstName, String lastName, String userName, String password, String email,
			int contact) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.contact = contact;
	}

}
