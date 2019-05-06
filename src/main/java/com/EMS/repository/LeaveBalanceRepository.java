package com.EMS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.EMS.model.LeaveBalanceModel;

public interface LeaveBalanceRepository extends JpaRepository<LeaveBalanceModel, Long> {

	@Query(value = "SELECT * FROM EMS.leave_balance where EMS.leave_balance.user_user_id = ?1 and EMS.leave_balance.quarter = ?2 and EMS.leave_balance.year = ?3",nativeQuery = true)
	LeaveBalanceModel getLeaveBalance(Long userId, int quater, int year);

	@Query(value="SELECT DISTINCT user_user_id FROM EMS.leave_balance",nativeQuery=true)
	List<Long> getUserId();

	@Query(value="SELECT * FROM EMS.leave_balance where quarter=:quarter AND user_user_id=:userId AND year=:year",nativeQuery=true)
//	@Query(value="SELECT * FROM EMS.leave_balance where quarter=1 AND user_user_id=73 AND year=2019",nativeQuery=true)
	List<LeaveBalanceModel> getuserLeaveBalance(long year, int quarter, long userId);
	

}
