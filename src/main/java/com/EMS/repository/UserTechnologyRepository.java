package com.EMS.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.EMS.model.UserTechnology;

public interface UserTechnologyRepository extends JpaRepository<UserTechnology, Long>{

//	@Modifying
//	@Transactional
//	@Query(value ="delete from EMS.user_technology where EMS.user_technology.user_user_id = 124 ",nativeQuery = true)
	@Query(value = "delete from UserTechnology u where u.user.userId = 125")
	UserTechnology deleteByUserId(long userId);

}
