package com.EMS.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.EMS.model.UserModel;

public interface UserRepositary extends JpaRepository<UserModel, Long>{
	
	
	@Query("SELECT u FROM UserModel u WHERE u.userName=?1 AND u.password=?2") //Query for checking username and password are matching or not
	UserModel getUserdetails(String userName, String password);

	@Query("SELECT u.id,u.firstName,u.lastName FROM UserModel u WHERE u.roleId=3")//for getting user details with role as owner by providing roleid
	ArrayList<UserModel> getProjectOwners();

	@Query("SELECT u.firstName FROM UserModel u WHERE u.roleId = 1")
	List<UserModel> getUser();
	
}
