package com.EMS.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EMS.model.UserModel;
import com.EMS.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;

	@Override
	public UserModel getUserDetailsById(Long id) {
		return userRepository.getActiveUser(id);

	}

	@Override
	public List<UserModel> getUserByDeptId(Long deptId) {
		List<UserModel> userList = userRepository.findByDeptId(deptId);
		return userList;
	}

	@Override
	public UserModel getUser(Long deptId, Long userId) {
		UserModel user = userRepository.getUser(userId, deptId);
		return user;
	}

	@Override
	public List<UserModel> getprojectOwner() {
		List<UserModel> user_owner = new ArrayList<UserModel>();
		user_owner = userRepository.getProjectOwners();

		return user_owner;
	}

	@Override
	public List<UserModel> getAllUsers() {
		List<UserModel> users = userRepository.getUser();
		return users;
	}



}
