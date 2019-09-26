package com.EMS.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.EMS.model.TaskTrackApproval;
import com.EMS.model.TaskTrackApprovalLevel2;

public interface TaskTrackApprovalLevel2Repository extends JpaRepository<TaskTrackApprovalLevel2, Long>{

	@Query("SELECT a FROM TaskTrackApprovalLevel2 a where a.user.userId = ?1 and a.month = ?2 and a.year = ?3 and a.project.projectId = ?4 ")
	List<TaskTrackApprovalLevel2> getApprovedData(Long userId, int monthIndex, int yearIndex, Long projectId);

	@Query("SELECT a FROM TaskTrackApprovalLevel2 a where a.user.userId = ?1 and a.month = ?3 and a.year = ?4 and a.project.projectId = ?2 ")
	List<TaskTrackApprovalLevel2> getUserListForApproval(Long id, Long projectId, Integer monthIndex, Integer yearIndex);

	@Query("SELECT s FROM TaskTrackApprovalLevel2 s WHERE s.project.projectId =?1 and s.user.userId = ?2 and s.month = ?3")
	ArrayList<TaskTrackApprovalLevel2> getForwardedDateLevel2(Long projectId, Long userId, int intMonth);

	/*
	 * @Query("select DISTINCT(s.forwarded_date) FROM TaskTrackApprovalLevel2 s where s.user.user_id = ?2 and s.project.project_id = ?1 and s.month=?3"
	 * ) TaskTrackApprovalLevel2 getForwardedDate(Long projectId, Long userId, int
	 * intMonth);
	 */


	
	

}