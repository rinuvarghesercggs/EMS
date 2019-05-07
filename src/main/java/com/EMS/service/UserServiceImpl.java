package com.EMS.service;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EMS.model.UserModel;
import com.EMS.model.UserTechnology;
import com.EMS.repository.TechnologyRepository;
import com.EMS.repository.UserRepository;
import com.EMS.repository.UserTechnologyRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private UserTechnologyRepository userTechnologyRepository;
	
	@Autowired
	private TechnologyRepository technologyRepository;
	
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


	@Override
	public String getUserName(Long id) {
		String name = userRepository.getUserName(id);
		return name;
	}

//	@Override
//	public List<Object[]> getUserIdLists(Long pageSize, Long startingIndex) {
//		List<Object[]> userIdList = userRepository.getUserIdLists(pageSize,startingIndex);
//		return userIdList;
//	}
	@Override
	public List<Object[]> getUserIdLists() {
		List<Object[]> userIdList = userRepository.getUserIdLists();
		return userIdList;
	}

	@Override
	public Long getCount() {
		Long count = userRepository.getUserCount();
		return count;
	}

	@Override
	public JsonNode getUserList() {
		JsonNode node = objectMapper.createObjectNode();
		node = objectMapper.convertValue(userRepository.getUser(), JsonNode.class);
		return node;
	}

	@Override
	public JsonNode getUserdetails(Long userId) {
		JsonNode node = objectMapper.createObjectNode();
		node = objectMapper.convertValue(userRepository.getUserById(userId), JsonNode.class);
		return node;
	}

	@Override
	public UserModel updateUser(UserModel user) {
		UserModel userModel = userRepository.save(user);
		return userModel;
	}

	@Override
	public int deleteTechnology(Long userId) {
		System.out.println("userId: "+userId);

		int userTechnology = userTechnologyRepository.deleteByUserId(userId);
		System.out.println("userId 1 : "+userId);

		return userTechnology;
	}

	@Override
	public Boolean checkExistanceOfUserId(Long userId) {
		Boolean isExist = userTechnologyRepository.checkExistanceOfUserId(userId);
		return isExist;
	}

	@Override
	public List<Object[]> getUserTechnologyList(Long userId) {
		
		List<Object[]> list = technologyRepository.getUserTechnologyList(userId);
		return list;
		
	}

	

	



}
