package com.EMS.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.EMS.model.LeaveModel;

public interface LeaveRepository extends JpaRepository<LeaveModel, Long> {

//	@Query(value = "select * from EMS.user_leave where EMS.user_leave.user_user_id = ?1",nativeQuery = true)
//	List<LeaveModel> getUserLeaveList(Long userId);

	
//	@Query(value = "select Date(EMS.user_leave.leave_from) AS leaveFrom,Date(EMS.user_leave.leave_to) AS leaveTo,EMS.user_leave.leave_reason AS leaveReason,EMS.user_leave.status AS status,(CASE  WHEN(EMS.user_leave.cl) IS NOT NULL THEN concat ('Casual Leave (',EMS.user_leave.cl,')')WHEN (EMS.user_leave.lop) IS NOT NULL THEN concat ('Loss of Pay (',EMS.user_leave.lop,')') WHEN (EMS.user_leave.sl) IS NOT NULL THEN concat ('Sick Leave (',EMS.user_leave.sl,')') WHEN (EMS.user_leave.sl) IS NOT NULL THEN concat ('Componsatory Off (',EMS.user_leave.compensatory_off,')') END) AS leaveType from EMS.user_leave where EMS.user_leave.user_user_id = ?1",nativeQuery = true)
//	List<Object[]> getUserLeaveList(Long userId);

	
	@Query(value = "select Date(EMS.user_leave.leave_from) AS leaveFrom,Date(EMS.user_leave.leave_to) AS leaveTo,EMS.user_leave.leave_reason AS leaveReason,EMS.user_leave.status AS status,(CASE  WHEN(EMS.user_leave.cl) IS NOT NULL THEN 'Casual Leave' WHEN (EMS.user_leave.lop) IS NOT NULL THEN 'Loss of Pay ' WHEN (EMS.user_leave.sl) IS NOT NULL THEN 'Sick Leave ' WHEN (EMS.user_leave.sl) IS NOT NULL THEN 'Componsatory Off'  END) AS leaveType,(CASE  WHEN(EMS.user_leave.cl) IS NOT NULL THEN EMS.user_leave.cl WHEN (EMS.user_leave.lop) IS NOT NULL THEN EMS.user_leave.lop WHEN (EMS.user_leave.sl) IS NOT NULL THEN EMS.user_leave.sl WHEN (EMS.user_leave.sl) IS NOT NULL THEN EMS.user_leave.sl  END) AS leaveCount from EMS.user_leave where EMS.user_leave.user_user_id = ?1 and (EMS.user_leave.leave_from >= ?2 and EMS.user_leave.leave_to <= ?3)",nativeQuery = true)
	List<Object[]> getUserLeaveList(Long userId, LocalDate firstDayOfYear, LocalDate lastDayOfYear);

//	@Query(value ="select coalesce(sum( coalesce(EMS.user_leave.compensatory_off,0)+coalesce(EMS.user_leave.cl,0)+coalesce(EMS.user_leave.lop,0)+coalesce(EMS.user_leave.sl,0)),0) from EMS.user_leave where EMS.user_leave.user_user_id = ?1 and (EMS.user_leave.leave_from >=?2 and EMS.user_leave.leave_to <= ?3)",nativeQuery = true)
//	Object getLeaveCount(Long userId, LocalDate firstDayOfYear, LocalDate lastDayOfYear);

}
