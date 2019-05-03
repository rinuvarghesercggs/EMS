package com.EMS.service;

import java.util.List;

import org.json.simple.JSONObject;

import com.EMS.model.UserModel;
import com.EMS.model.UserTechnology;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

public interface UserService {
	
	UserModel getUserDetailsById(Long uId);

	List<UserModel> getUserByDeptId(Long deptId);

	UserModel getUser(Long deptId, Long userId);

	List<UserModel> getprojectOwner();

	List<UserModel> getAllUsers();

//	List<Object[]> getUserIdLists(Long pageSize, Long startingIndex);
	List<Object[]> getUserIdLists();

	String getUserName(Long id);

	Long getCount();

	 ArrayNode getUserList();

	JsonNode getUserdetails(Long userId);

	UserModel updateUser(UserModel user);

	int deleteTechnology(Long userId);

	Boolean checkExistanceOfUserId(Long userId);

	





}
