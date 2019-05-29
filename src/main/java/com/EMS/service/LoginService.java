package com.EMS.service;



import java.util.ArrayList;
import java.util.List;

import com.EMS.model.DepartmentModel;
import com.EMS.model.RoleModel;
import com.EMS.model.Technology;
import com.EMS.model.UserModel;
import com.EMS.model.UserTechnology;

public interface LoginService {

	

//		abstract method declaration for user login authentication
	UserModel login_authentication(String userName);

	UserModel adduser(UserModel requestdata);

	//abstract method declaration for add user technology data
	int addusertechnology(UserTechnology usertech);
	
	//find technology by given ID
	Technology findtechnology(Long parseInt);
	
	//find department by given ID
	DepartmentModel getDepartment(long parseLong);
	
	//find role by given ID
	RoleModel getRole(long parseLong);

	UserModel changePasswordAuthentication(long parseLong, String oldPassword);

	Boolean checkUsernameDuplication(String userName);

	List<Technology> getTechnology();

	Boolean checkEmpIDDuplication(long empId);

	

}
