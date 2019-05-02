package com.EMS.service;

import java.util.List;

import com.EMS.model.UserModel;
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
	





}
