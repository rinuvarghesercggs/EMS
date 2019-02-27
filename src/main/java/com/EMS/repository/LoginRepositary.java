package com.EMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.EMS.model.UserModel;

public interface LoginRepositary extends JpaRepository<UserModel, Long>{
	
	
	@Query("SELECT * FROM UserModel.u WHERE u.userName=?1 AND u.password=?2") //Query for checking username and password are matching or not
	UserModel getUserdetails(String userName, String password);
	
}
