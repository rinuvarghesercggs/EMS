package com.EMS.service;

import java.util.ArrayList;

import com.EMS.model.DepartmentModel;
import com.EMS.model.UserModel;

public interface LoginService {
		

		//abstract method declaration for user login authentication
		public UserModel login_authentication(UserModel user);

}
