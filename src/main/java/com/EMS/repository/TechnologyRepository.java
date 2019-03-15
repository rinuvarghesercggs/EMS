package com.EMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.EMS.model.Technology;

public interface TechnologyRepository extends JpaRepository<Technology, Long>{
	
	
}
