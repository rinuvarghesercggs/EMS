package com.EMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.EMS.model.LeaveBalanceModel;

public interface LeaveBalanceRepository extends JpaRepository<LeaveBalanceModel, Long> {

	@Query(value = "SELECT * FROM EMS.leave_balance where EMS.leave_balance.user_user_id = ?1 and EMS.leave_balance.quarter = ?2 and EMS.leave_balance.year = ?3",nativeQuery = true)
	LeaveBalanceModel getLeaveBalance(Long userId, int quater, int year);

}
