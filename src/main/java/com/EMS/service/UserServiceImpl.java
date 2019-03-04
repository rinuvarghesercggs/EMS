package com.EMS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EMS.model.UserModel;
import com.EMS.repository.UserRepositary;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepositary userRepositary;
	
	@Override
	public UserModel getUserDetailsById(Long id){
		return  userRepositary.getOne(id);
				
	}

}
