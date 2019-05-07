package com.EMS.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.EMS.model.LeaveModel;

public interface LeaveRepository extends JpaRepository<LeaveModel, Long> {

	@Query(value = "select Date(EMS.user_leave.leave_from) AS leaveFrom,Date(EMS.user_leave.leave_to) AS leaveTo,EMS.user_leave.leave_reason AS leaveReason,EMS.user_leave.status AS status,(CASE  WHEN(EMS.user_leave.cl) IS NOT NULL THEN 'Casual Leave' WHEN (EMS.user_leave.lop) IS NOT NULL THEN 'Loss of Pay ' WHEN (EMS.user_leave.sl) IS NOT NULL THEN 'Sick Leave ' WHEN (EMS.user_leave.el) IS NOT NULL THEN 'Earned Leave'  END) AS leaveType,(CASE  WHEN(EMS.user_leave.cl) IS NOT NULL THEN EMS.user_leave.cl WHEN (EMS.user_leave.lop) IS NOT NULL THEN EMS.user_leave.lop WHEN (EMS.user_leave.sl) IS NOT NULL THEN EMS.user_leave.sl WHEN (EMS.user_leave.el) IS NOT NULL THEN EMS.user_leave.el  END) AS leaveCount from EMS.user_leave where EMS.user_leave.user_user_id = ?1 and (EMS.user_leave.leave_from >= ?2 and EMS.user_leave.leave_to <= ?3)",nativeQuery = true)
	List<Object[]> getUserLeaveList(Long userId, LocalDate firstDayOfYear, LocalDate lastDayOfYear);
	
	@Query(value="SELECT * FROM EMS.user_leave where date(leave_from) =:date1",nativeQuery=true)
	List<LeaveModel> getweeklyleavelist(Date date1);
	
	@Query(value="Select * from user_leave where user_user_id=:userId AND leave_from >=:startDate1 AND leave_to<=:endDate1",nativeQuery=true)
	List<LeaveModel> getyearlyleavelist(long userId, Date startDate1, Date endDate1);

	@Query(value="SELECT COUNT(*) FROM user_leave WHERE user_user_id=?1 AND (leave_from=?2 OR leave_to=?3)",nativeQuery=true)
	int checkuser(long userId, Date leaveFrom, Date leaveTo);

	@Query(value="Select * from user_leave where leave_from >=:startDate1 AND leave_to<=:endDate1",nativeQuery=true)
	List<LeaveModel> getyearlyleavelist(Date startDate1, Date endDate1);

	@Query(value = "select Date(EMS.user_leave.leave_from) AS leaveFrom,Date(EMS.user_leave.leave_to) AS leaveTo,EMS.user_leave.leave_reason AS leaveReason,EMS.user_leave.status AS status,(CASE  WHEN(EMS.user_leave.cl) IS NOT NULL THEN 'Casual Leave' WHEN (EMS.user_leave.lop) IS NOT NULL THEN 'Loss of Pay ' WHEN (EMS.user_leave.sl) IS NOT NULL THEN 'Sick Leave ' WHEN (EMS.user_leave.el) IS NOT NULL THEN 'Earned Leave'  END) AS leaveType,(CASE  WHEN(EMS.user_leave.cl) IS NOT NULL THEN EMS.user_leave.cl WHEN (EMS.user_leave.lop) IS NOT NULL THEN EMS.user_leave.lop WHEN (EMS.user_leave.sl) IS NOT NULL THEN EMS.user_leave.sl WHEN (EMS.user_leave.el) IS NOT NULL THEN EMS.user_leave.el  END) AS leaveCount from EMS.user_leave where EMS.user_leave.user_user_id = ?1 and (EMS.user_leave.leave_from >= ?2 and EMS.user_leave.leave_to <= ?3) and EMS.user_leave.cl IS NOT NULL",nativeQuery = true)
	List<Object[]> getUsersCasualLeaveLeaveList(Long userId, LocalDate firstDayOfYear, LocalDate lastDayOfYear);

	@Query(value = "select Date(EMS.user_leave.leave_from) AS leaveFrom,Date(EMS.user_leave.leave_to) AS leaveTo,EMS.user_leave.leave_reason AS leaveReason,EMS.user_leave.status AS status,(CASE  WHEN(EMS.user_leave.cl) IS NOT NULL THEN 'Casual Leave' WHEN (EMS.user_leave.lop) IS NOT NULL THEN 'Loss of Pay ' WHEN (EMS.user_leave.sl) IS NOT NULL THEN 'Sick Leave ' WHEN (EMS.user_leave.el) IS NOT NULL THEN 'Earned Leave'  END) AS leaveType,(CASE  WHEN(EMS.user_leave.cl) IS NOT NULL THEN EMS.user_leave.cl WHEN (EMS.user_leave.lop) IS NOT NULL THEN EMS.user_leave.lop WHEN (EMS.user_leave.sl) IS NOT NULL THEN EMS.user_leave.sl WHEN (EMS.user_leave.el) IS NOT NULL THEN EMS.user_leave.el  END) AS leaveCount from EMS.user_leave where EMS.user_leave.user_user_id = ?1 and (EMS.user_leave.leave_from >= ?2 and EMS.user_leave.leave_to <= ?3) and EMS.user_leave.sl IS NOT NULL",nativeQuery = true)
	List<Object[]> getUsersSickLeaveLeaveList(Long userId, LocalDate firstDayOfYear, LocalDate lastDayOfYear);

	@Query(value = "select Date(EMS.user_leave.leave_from) AS leaveFrom,Date(EMS.user_leave.leave_to) AS leaveTo,EMS.user_leave.leave_reason AS leaveReason,EMS.user_leave.status AS status,(CASE  WHEN(EMS.user_leave.cl) IS NOT NULL THEN 'Casual Leave' WHEN (EMS.user_leave.lop) IS NOT NULL THEN 'Loss of Pay ' WHEN (EMS.user_leave.sl) IS NOT NULL THEN 'Sick Leave ' WHEN (EMS.user_leave.el) IS NOT NULL THEN 'Earned Leave'  END) AS leaveType,(CASE  WHEN(EMS.user_leave.cl) IS NOT NULL THEN EMS.user_leave.cl WHEN (EMS.user_leave.lop) IS NOT NULL THEN EMS.user_leave.lop WHEN (EMS.user_leave.sl) IS NOT NULL THEN EMS.user_leave.sl WHEN (EMS.user_leave.el) IS NOT NULL THEN EMS.user_leave.el  END) AS leaveCount from EMS.user_leave where EMS.user_leave.user_user_id = ?1 and (EMS.user_leave.leave_from >= ?2 and EMS.user_leave.leave_to <= ?3) and EMS.user_leave.el IS NOT NULL",nativeQuery = true)
	List<Object[]> getUsersEarnedLeaveLeaveList(Long userId, LocalDate firstDayOfYear, LocalDate lastDayOfYear);

	@Query(value = "select Date(EMS.user_leave.leave_from) AS leaveFrom,Date(EMS.user_leave.leave_to) AS leaveTo,EMS.user_leave.leave_reason AS leaveReason,EMS.user_leave.status AS status,(CASE  WHEN(EMS.user_leave.cl) IS NOT NULL THEN 'Casual Leave' WHEN (EMS.user_leave.lop) IS NOT NULL THEN 'Loss of Pay ' WHEN (EMS.user_leave.sl) IS NOT NULL THEN 'Sick Leave ' WHEN (EMS.user_leave.el) IS NOT NULL THEN 'Earned Leave'  END) AS leaveType,(CASE  WHEN(EMS.user_leave.cl) IS NOT NULL THEN EMS.user_leave.cl WHEN (EMS.user_leave.lop) IS NOT NULL THEN EMS.user_leave.lop WHEN (EMS.user_leave.sl) IS NOT NULL THEN EMS.user_leave.sl WHEN (EMS.user_leave.el) IS NOT NULL THEN EMS.user_leave.el  END) AS leaveCount from EMS.user_leave where EMS.user_leave.user_user_id = ?1 and (EMS.user_leave.leave_from >= ?2 and EMS.user_leave.leave_to <= ?3) and EMS.user_leave.lop IS NOT NULL",nativeQuery = true)
	List<Object[]> getUsersLOPLeaveLeaveList(Long userId, LocalDate firstDayOfYear, LocalDate lastDayOfYear);
	

}
