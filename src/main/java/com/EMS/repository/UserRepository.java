package com.EMS.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.EMS.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long>{
	
	
	@Query("SELECT u FROM UserModel u WHERE u.userName=?1 AND u.password=?2") //Query for checking username and password are matching or not
	UserModel getUserdetails(String userName, String password);

	@Query("SELECT u FROM UserModel u WHERE u.role = 2")//for getting user details with role as owner by providing role
	List<UserModel> getProjectOwners();

	@Query("SELECT u FROM UserModel u WHERE u.role = 3 AND u.active = true")
	List<UserModel> getUser();
	
	@Query("SELECT u FROM UserModel u WHERE u.userName=?1")
	UserModel getUserDetailsByName(String userName);

	@Query("SELECT u FROM UserModel u WHERE u.department.departmentId = ?1 AND u.active = true AND u.role = 3")
	List<UserModel> findByDeptId(Long deptId);

	@Query("SELECT u FROM UserModel u WHERE u.userId = ?1 and u.department.departmentId = ?2 AND u.active = true")
	UserModel getUser(Long userId,Long deptId);

	@Query("SELECT u FROM UserModel u WHERE u.userId = ?1 AND u.active = true")
	UserModel getActiveUser(Long id);
	
	
}
