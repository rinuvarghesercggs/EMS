package com.EMS.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.EMS.model.AllocationModel;


public interface ProjectAllocationRepository extends JpaRepository<AllocationModel, Long> {


	@Query("SELECT s FROM AllocationModel s WHERE s.project.projectId = ?1")
	List<AllocationModel> getProjectLists(Long projectId);
//	public default List<Alloc> getProjectLists(Long projectId){
//	getJdbcTemplate().query("select user_name as userName", new RowMapper());
//	}
	
	public default void a() {
		
	}

	@Query("SELECT count(*) > 0 FROM AllocationModel s WHERE s.user.userId = ?1")
	Boolean isExists(long userId);

	@Query("SELECT s FROM AllocationModel s WHERE s.user.userId = ?1")
	List<AllocationModel> findByUserId(long userId);

	@Query(value = "SELECT s FROM AllocationModel s WHERE s.user.userId =:userId and s.startDate <:date2 and s.endDate >:date1 order by user.firstName")
	List<AllocationModel> findUsers(long userId, Date date1, Date date2);

	@Query("SELECT s.allocId FROM AllocationModel s WHERE s.user.userId = ?2 and s.project.projectId = ?1")
	Long getAllocationId(long projectId, long userId);
	

//	@Query("select u.user.userId from AllocationModel u where u.project.projectId = ?1")
//	List<Object[]> getUserIdByProject(Long projectId, Long pageSize, Long limit);
	
//	@Query(value ="SELECT EMS.allocation.user_user_id FROM EMS.allocation where EMS.allocation.project_project_id = ?1 LIMIT ?3,?2",nativeQuery = true)
//	List<Object[]> getUserIdByProject(Long projectId, Long pageSize, Long limit);
	
	@Query("select u.user.userId from AllocationModel u where u.project.projectId = ?1")
	List<Object[]> getUserIdByProject(Long projectId);

	@Query(value = "SELECT COUNT(EMS.allocation.user_user_id) FROM EMS.allocation where EMS.allocation.project_project_id = ?1",nativeQuery = true)
	Long getUserCount(Long projectId);
	
	@Query("SELECT a.isBillable FROM AllocationModel a where a.user.userId = ?1 and a.project.projectId = ?2")
	Boolean getIsBillable(Long id, Long projectId);
	

	
//	@Query(value = "SELECT * FROM EMS.alloc where  EMS.alloc.user_user_id = ?1 and EMS.alloc.end_date < ?3 or EMS.alloc.start_date > ?1", nativeQuery = true)
//	List<Alloc> findUsers(long userId, Date date1, Date date2);

	
}
