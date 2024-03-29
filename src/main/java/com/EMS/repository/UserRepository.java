package com.EMS.repository;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.EMS.model.UserModel;
import com.fasterxml.jackson.databind.JsonNode;

public interface UserRepository extends JpaRepository<UserModel, Long>{
		
	@Query("SELECT u FROM UserModel u WHERE u.userName=?1 AND u.password=?2 AND u.active = true") //Query for checking username and password are matching or not
//	@Query("SELECT u FROM UserModel u WHERE u.userName=?1 AND u.active = true") 
	UserModel getUserdetails(String userName, String password);

	@Query("SELECT u FROM UserModel u WHERE u.role in (2,5)")//for getting user details with role as owner by providing role
	List<UserModel> getProjectOwners();

	@Query("SELECT u FROM UserModel u WHERE u.role in(2,3,4,5) AND u.active = true order by firstName")
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

	@Query(value = "SELECT first_name,last_name FROM user where user.user_id = ?1",nativeQuery = true)
	String getUserName(Long id);

	@Query(value = "SELECT COUNT(user_id) FROM user",nativeQuery = true)
	Long getUserCount();

	@Query("select count(*)>0 from UserModel u where u.userName = ?1")
	Boolean checkExistanceOfUserName(String userName);

	@Query("select u from UserModel u where u.userId = ?1")
	Object getUserById(Long userId);

	@Query("select count(*)>0 from UserModel u where u.empId = ?1")
	Boolean checkExistanceOfEmpId(long empId);

	@Query(value="SELECT joining_date,first_name,last_name,cpp_level,emp_category,recruiter,referred_by,active,user_id FROM user where year(joining_date)=:year AND month(joining_date) between :startmonth AND :endmonth",nativeQuery=true)
	List<Object[]> findnewHire(int startmonth,int endmonth, int year);

//	@Query("select u from UserModel u where u.userName = ?1")
	Optional<UserModel> findByUserName(String username);

	@Query(value = "SELECT user_id,first_name,last_name,joining_date,termination_date FROM user where role_role_id in('2','3','5') and  department_department_id in('1','2','3','4','8') and (termination_date >= ?1 or termination_date IS NULL) and joining_date<=?2 order by first_name",nativeQuery = true)
	List<Object[]> getUserList(Date startDate, Date endDate);

	@Query("SELECT u FROM UserModel u WHERE u.role in(2,3,4,5) order by firstName")
	List<UserModel> getAllUsers();
	
	@Query("SELECT u FROM UserModel u WHERE u.role in (7)")
	List<UserModel> getOnsiteLeads();
}
