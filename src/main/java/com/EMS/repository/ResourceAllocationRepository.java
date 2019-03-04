package com.EMS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.EMS.model.Alloc;


public interface ResourceAllocationRepository extends JpaRepository<Alloc, Long> {


	@Query("SELECT s FROM Alloc s WHERE s.project.id = ?1")
	List<Alloc> getProjectLists(Long projectId);

	

}
