package com.EMS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.EMS.model.Technology;

public interface TechnologyRepository extends JpaRepository<Technology, Long>{

	@Query(value = "select user_technology.experience,technology.technology_id from technology JOIN user_technology ON technology.technology_id = user_technology.technology_technology_id where user_technology.user_user_id = ?1 ",nativeQuery = true)
	List<Object[]> getUserTechnologyList(Long userId);
	
	
}
