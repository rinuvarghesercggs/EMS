package com.EMS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.EMS.model.Technology;

public interface TechnologyRepository extends JpaRepository<Technology, Long>{

	@Query(value = "select EMS.user_technology.experience,EMS.technology.technology_id from EMS.technology JOIN EMS.user_technology ON EMS.technology.technology_id = EMS.user_technology.technology_technology_id where EMS.user_technology.user_user_id = ?1 ",nativeQuery = true)
	List<Object[]> getUserTechnologyList(Long userId);
	
	
}
