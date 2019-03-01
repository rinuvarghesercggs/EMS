package com.EMS.service;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EMS.model.DepartmentModel;
import com.EMS.model.UserModel;
import com.EMS.repository.DepartmentRepository;
import com.EMS.repository.UserRepositary;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	UserRepositary login_repositary;

	
	

//	 Implementation for authenticating user with role
	
	@Override
	public UserModel login_authentication(String userName, String password) {
		
//		Initializing usermodel for returning		
		UserModel checkuserid=null;															
		try {
//			calling sql query by passing parameters			
			checkuserid=login_repositary.getUserdetails(userName,password);
			System.out.print("user : "+checkuserid.getId());
			return checkuserid;
		}catch(Exception e) {
			System.out.println("Exception : "+e);
			return checkuserid;
		}
	}


}
