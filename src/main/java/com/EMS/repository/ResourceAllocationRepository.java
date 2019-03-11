package com.EMS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.EMS.model.Alloc;


public interface ResourceAllocationRepository extends JpaRepository<Alloc, Long> {


	@Query("SELECT s FROM Alloc s WHERE s.project.projectId = ?1")
	List<Alloc> getProjectLists(Long projectId);
//	public default List<Alloc> getProjectLists(Long projectId){
//	getJdbcTemplate().query("select user_name as userName", new RowMapper());
//	}
	
	public default void a() {
		
	}

	@Query("SELECT count(*) > 0 FROM Alloc s WHERE s.user.userId = ?1")
	Boolean isExists(long userId);

	@Query("SELECT s FROM Alloc s WHERE s.user.userId = ?1")
	List<Alloc> findByUserId(long userId);

	

}
