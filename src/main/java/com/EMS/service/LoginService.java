package com.EMS.service;



import com.EMS.model.UserModel;

public interface LoginService {

	

//		abstract method declaration for user login authentication
	UserModel login_authentication(String userName, String password);
	

}
