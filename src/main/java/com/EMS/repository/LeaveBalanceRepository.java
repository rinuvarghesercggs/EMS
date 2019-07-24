package com.EMS.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.EMS.model.LeaveBalanceModel;

public interface LeaveBalanceRepository extends JpaRepository<LeaveBalanceModel, Long> {

	@Query(value = "SELECT * FROM leave_balance where leave_balance.user_user_id = ?1 and leave_balance.quarter = ?2 and leave_balance.year = ?3",nativeQuery = true)
	LeaveBalanceModel getLeaveBalance(Long userId, int quater, int year);

	@Query(value="SELECT DISTINCT user_user_id FROM leave_balance",nativeQuery=true)
	List<Long> getUserId();

	@Query(value="SELECT * FROM leave_balance where quarter=:quarter AND user_user_id=:userId AND year=:year",nativeQuery=true)
//	@Query(value="SELECT * FROM leave_balance where quarter=1 AND user_user_id=73 AND year=2019",nativeQuery=true)
	LeaveBalanceModel getuserLeaveBalance(long year, int quarter, long userId);

	@Query(value="SELECT COUNT(*) FROM leave_balance WHERE user_user_id=:userId AND quarter=:quarter AND year=:year",nativeQuery=true)
	int checkBalance(long userId, int quarter, int year);
	
	@Modifying
	@Transactional
	@Query(value="delete FROM leave_balance WHERE user_user_id=?3 AND quarter<?1 AND year=?2",nativeQuery=true)
	public void deleteleaveBalance(int quarter, int year, long userId) throws Exception;
	

}
