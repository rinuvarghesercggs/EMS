package com.EMS.service;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EMS.model.UserModel;
import com.EMS.repository.UserRepository;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	UserRepository user_repositary;

	
	

//	 Implementation for authenticating user with role
	
	@Override
	public UserModel login_authentication(String userName, String password) {
		
//		Initializing usermodel for returning		
		UserModel checkuserid=null;															
		try {
//			calling sql query by passing parameters			
			checkuserid=user_repositary.getUserdetails(userName,password);
			return checkuserid;
		}catch(Exception e) {
			System.out.println("Exception : "+e);
			return checkuserid;
		}
	}


}
