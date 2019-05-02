package com.EMS.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EMS.model.DepartmentModel;
import com.EMS.model.RoleModel;
import com.EMS.model.Technology;
import com.EMS.model.UserModel;
import com.EMS.model.UserTechnology;
import com.EMS.repository.UserTechnologyRepository;

import com.EMS.repository.DepartmentRepository;
import com.EMS.repository.RoleRepository;
import com.EMS.repository.TechnologyRepository;
import com.EMS.repository.UserRepository;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	UserRepository user_repositary;
	
	@Autowired
	UserTechnologyRepository usertechnology_repository;
	
	@Autowired
	TechnologyRepository technology_repository;
	
	@Autowired
	DepartmentRepository department_repository;
	
	@Autowired
	RoleRepository role_repository;


//	 Implementation for authenticating user with role

	@Override
	public UserModel login_authentication(String userName, String password) {

//		Initializing usermodel for returning		
		UserModel checkuserid = null;
		
		try {
			
//			calling sql query by passing parameters	
			checkuserid = user_repositary.getUserdetails(userName, password);

			return checkuserid;
		} catch (Exception e) {
			System.out.println("Exception : " + e);
			return checkuserid;
		}
	}

	
	// method for creating user record
	@Override
	public UserModel adduser(UserModel requestdata) {
		UserModel user=null;
		try {

			user=user_repositary.save(requestdata);
			return user;
		}catch(Exception e) {
			System.out.println("Exception : "+e);
			return user;
		}
		
	}
	
	//method for creating records on usertechnology
	@Override
	public UserTechnology addusertechnology(UserTechnology usertech) {
		
		UserTechnology usertechno=usertechnology_repository.save(usertech);
		return usertechno;
	}

	//method for finding technology by ID
	@Override
	public Technology findtechnology(Long id) {

		Technology technology=technology_repository.getOne(id);
		return technology;
	}
	
	//method for finding department by ID
	@Override
	public DepartmentModel getDepartment(long id) {

		DepartmentModel department=department_repository.getOne(id);
		return department;
	}
	
	//method for finding department by Id
	@Override
	public RoleModel getRole(long id) {
		
		RoleModel role=role_repository.getOne(id);
		return role;
	}


	@Override
	public UserModel changePasswordAuthentication(long userId, String password) {
		//Initializing usermodel for returning		
				UserModel checkuserid = null;
				try {

				//calling sql query by passing parameters			
					checkuserid = user_repositary.getUserByUserId(userId, password);
					return checkuserid;
				} catch (Exception e) {
					System.out.println("Exception : " + e);
					return checkuserid;
				}
	}


	@Override
	public Boolean checkUsernameDuplication(String userName) {
		Boolean isUsernameExist = user_repositary.checkExistanceOfUserName(userName);
		return isUsernameExist;
	}


	@Override
	public List<Technology> getTechnology() {
		List<Technology> techlist=technology_repository.findAll();
		return techlist;
	}

	

}
