package com.EMS.service;



import java.util.ArrayList;

import com.EMS.model.DepartmentModel;
import com.EMS.model.RoleModel;
import com.EMS.model.Technology;
import com.EMS.model.UserModel;
import com.EMS.model.UserTechnology;

public interface LoginService {

	

//		abstract method declaration for user login authentication
	UserModel login_authentication(String userName, String password);

	UserModel adduser(UserModel requestdata);

	//abstract method declaration for add user technology data
	UserTechnology addusertechnology(UserTechnology usertech);
	
	//find technology by given ID
	Technology findtechnology(Long parseInt);
	
	//find department by given ID
	DepartmentModel getDepartment(long parseLong);
	
	//find role by given ID
	RoleModel getRole(long parseLong);

	

}
