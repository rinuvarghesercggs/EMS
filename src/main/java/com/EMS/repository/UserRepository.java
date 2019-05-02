package com.EMS.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.EMS.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long>{
		
	@Query("SELECT u FROM UserModel u WHERE u.userName=?1 AND u.password=?2 AND u.active = true") //Query for checking username and password are matching or not
//	@Query("SELECT u FROM UserModel u WHERE u.userName=?1 AND u.active = true") 
	UserModel getUserdetails(String userName, String password);

	@Query("SELECT u FROM UserModel u WHERE u.role = 2")//for getting user details with role as owner by providing role
	List<UserModel> getProjectOwners();

	@Query("SELECT u FROM UserModel u WHERE u.role in(2,3) AND u.active = true order by firstName")
	List<UserModel> getUser();
	
	@Query("SELECT u FROM UserModel u WHERE u.userName=?1")
	UserModel getUserDetailsByName(String userName);

	@Query("SELECT u FROM UserModel u WHERE u.department.departmentId = ?1 AND u.active = true AND u.role = 3 order by firstName")
	List<UserModel> findByDeptId(Long deptId);

	@Query("SELECT u FROM UserModel u WHERE u.userId = ?1 and u.department.departmentId = ?2 AND u.active = true order by firstName")
	UserModel getUser(Long userId,Long deptId);

	@Query("SELECT u FROM UserModel u WHERE u.userId = ?1 AND u.active = true")
	UserModel getActiveUser(Long id);
	
	//authentication based query
	@Query("SELECT u FROM UserModel u WHERE u.userName=:username")
	UserModel getUser(String username);

	@Query("SELECT u FROM UserModel u WHERE u.userId=?1 AND u.password=?2") 
	UserModel getUserByUserId(Long userId, String password);
	
//	@Query(value = "SELECT user_id FROM EMS.user LIMIT ?2,?1",nativeQuery = true)
//	List<Object[]> getUserIdLists(Long pageSize, Long startingIndex);
	
	@Query("select u.userId from UserModel u")
	List<Object[]> getUserIdLists();

	@Query(value = "SELECT first_name,last_name FROM EMS.user where EMS.user.user_id = ?1",nativeQuery = true)
	String getUserName(Long id);

	@Query(value = "SELECT COUNT(user_id) FROM EMS.user",nativeQuery = true)
	Long getUserCount();

	@Query("select count(*)>0 from UserModel u where u.userName = ?1")
	Boolean checkExistanceOfUserName(String userName);

	

}
