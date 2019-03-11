package com.EMS.service;

import java.util.List;

import com.EMS.model.UserModel;

public interface UserService {
	
	UserModel getUserDetailsById(Long uId);

	List<UserModel> getUserByDeptId(Long deptId);

}
