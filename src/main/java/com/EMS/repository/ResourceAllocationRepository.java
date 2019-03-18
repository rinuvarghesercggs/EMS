package com.EMS.repository;

import java.util.Date;
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

	@Query(value = "SELECT s FROM Alloc s WHERE s.user.userId =:userId and s.startDate <:date2 and s.endDate >:date1")
	List<Alloc> findUsers(long userId, Date date1, Date date2);

	
//	@Query(value = "SELECT * FROM EMS.alloc where  EMS.alloc.user_user_id = ?1 and EMS.alloc.end_date < ?3 or EMS.alloc.start_date > ?1", nativeQuery = true)
//	List<Alloc> findUsers(long userId, Date date1, Date date2);

}
