package com.EMS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EMS.model.TaskModel;
import com.EMS.model.UserModel;
import com.EMS.repository.UserRepositary;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepositary userRepositary;
	
	@Override
	public UserModel getUserDetailsByName(String name){
		return  userRepositary.getUserDetailsByName(name);
				
	}

}
