package com.EMS.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EMS.model.UserModel;
import com.EMS.repository.UserRepository;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	UserRepository user_repositary;
	
//	@Autowired
//	BCryptPasswordEncoder bCryptPasswordEncoder;

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

//	@Override
//	public ArrayList<UserModel> getAllUser() {
//		ArrayList<UserModel> userarray=(ArrayList<UserModel>) user_repositary.findAll();
//		return userarray;
//	}

	
	
//	@Override
//	public UserModel adduser(UserModel requestdata) {
//		UserModel user=null;
//		try {
//			System.out.println("start");
////			String newdata=bCryptPasswordEncoder.encode(requestdata.getPassword());
////			System.out.println("newdata : "+newdata);
////			requestdata.setPassword(newdata);
////			System.out.println("password : "+requestdata.getPassword());
//			user=user_repositary.save(requestdata);
//			System.out.println("completed");
//			return user;
//		}catch(Exception e) {
//			System.out.println("Exception : "+e);
//			return user;
//		}
//		
//	}

}
