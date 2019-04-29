package com.EMS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.EMS.model.LeaveModel;

public interface LeaveRepository extends JpaRepository<LeaveModel, Long> {

	@Query(value = "select * from EMS.user_leave where EMS.user_leave.user_user_id = ?1",nativeQuery = true)
//	@Query("select l from LeaveModel l where l.user.userId = ?1")
	List<LeaveModel> getUserLeaveList(Long userId);

}
