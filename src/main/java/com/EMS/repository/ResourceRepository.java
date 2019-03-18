package com.EMS.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.EMS.model.Resources;

public interface ResourceRepository extends JpaRepository<Resources, Long>{

	

	@Query("UPDATE Resources r SET r.project=?1 WHERE r.id=?2")
	ArrayList<Resources> updateprojectid(Long long1,long id);
	
	@Query("SELECT r FROM Resources r WHERE r.project.projectId=?1")
	List<Resources> getResourceList(long projectId);



}
