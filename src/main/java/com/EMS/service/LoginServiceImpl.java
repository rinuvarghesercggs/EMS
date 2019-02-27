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
	

	// Implementation for authenticating user with role
	
	@Override
	public UserModel login_authentication(UserModel user) {
		
		UserModel checkuser=null;															//Initializing usermodel for returning
		try {
			
			checkuser=login_repositary.getUserdetails(user.getUserName(),user.getPassword());//calling sql query by passing parameters
			return checkuser;
		}catch(Exception e) {
			System.out.println("Exception : "+e);
			return checkuser;
		}
		
	}


}
