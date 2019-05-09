package com.EMS.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.EMS.model.LeaveModel;

public interface LeaveRepository extends JpaRepository<LeaveModel, Long> {

	@Query(value = "select Date(EMS.user_leave.leave_from) AS leaveFrom,Date(EMS.user_leave.leave_to) AS leaveTo,EMS.user_leave.leave_reason AS leaveReason,EMS.user_leave.status AS status,EMS.user_leave.leave_type AS leaveType,EMS.user_leave.leave_count AS leaveCount from EMS.user_leave where EMS.user_leave.user_user_id = ?1 and (EMS.user_leave.leave_from >= ?2 and EMS.user_leave.leave_to <= ?3)",nativeQuery = true)
	List<Object[]> getUserLeaveList(Long userId, LocalDate firstDayOfYear, LocalDate lastDayOfYear);
	
//	@Query(value="SELECT * FROM EMS.user_leave where (date(leave_from) >=:startdate1 AND date(leave_to)<=:enddate1) OR (date(leave_from) <=:startdate1 AND (date(leave_to)<=:enddate1 AND date(leave_to)>=:startdate1)) OR (date(leave_to) >=:enddate1 AND (date(leave_from)>=:startdate1 AND date(leave_from)<=:enddate1)) OR (date(leave_from)<:startdate1 AND date(leave_to)>:enddate1)",nativeQuery=true)
//	@Query(value="SELECT user_leave_id,date(leave_from),date(leave_to),leave_count,leave_type,user_user_id,leave_reason,status  FROM EMS.user_leave where (date(leave_from) >=:startdate1 AND date(leave_to)<=:enddate1) OR (date(leave_from) <=:startdate1 AND (date(leave_to)<=:enddate1 AND date(leave_to)>=:startdate1)) OR (date(leave_to) >=:enddate1 AND (date(leave_from)>=:startdate1 AND date(leave_from)<=:enddate1)) OR (date(leave_from)<:startdate1 AND date(leave_to)>:enddate1)",nativeQuery=true)
	@Query(value="SELECT user_leave_id,applied_date,approved_date,leave_count,date(leave_from),leave_reason,date(leave_to),leave_type,status,user_user_id  FROM EMS.user_leave where (date(leave_from) >=:startdate1 AND date(leave_to)<=:enddate1) OR (date(leave_from) <=:startdate1 AND (date(leave_to)<=:enddate1 AND date(leave_to)>=:startdate1)) OR (date(leave_to) >=:enddate1 AND (date(leave_from)>=:startdate1 AND date(leave_from)<=:enddate1)) OR (date(leave_from)<:startdate1 AND date(leave_to)>:enddate1)",nativeQuery=true)
	List<Object[]> getweeklyleavelist(Date startdate1,Date enddate1);
//	List<LeaveModel> getweeklyleavelist(Date startdate1,Date enddate1);
	
	@Query(value="Select * from user_leave where user_user_id=:userId AND leave_from >=:startDate1 AND leave_to<=:endDate1",nativeQuery=true)
	List<LeaveModel> getyearlyleavelist(long userId, Date startDate1, Date endDate1);

	@Query(value="SELECT COUNT(*) FROM user_leave WHERE user_user_id=?1 AND (leave_from<=?2 OR leave_to>=?3)",nativeQuery=true)
	int checkuser(long userId, Date leaveFrom, Date leaveTo);

	@Query(value="Select * from user_leave where leave_from >=:startDate1 AND leave_to<=:endDate1",nativeQuery=true)
	List<LeaveModel> getyearlyleavelist(Date startDate1, Date endDate1);

	@Query(value = "select Date(EMS.user_leave.leave_from) AS leaveFrom,Date(EMS.user_leave.leave_to) AS leaveTo,EMS.user_leave.leave_reason AS leaveReason,EMS.user_leave.status AS status,EMS.user_leave.leave_type AS leaveType,EMS.user_leave.leave_count AS leaveCount  from EMS.user_leave where EMS.user_leave.user_user_id = ?1 and (EMS.user_leave.leave_from >= ?2 and EMS.user_leave.leave_to <= ?3) and EMS.user_leave.leave_type = ?4",nativeQuery = true)
	List<Object[]> getUsersLeaveLeaveListByType(Long userId, LocalDate firstDayOfYear, LocalDate lastDayOfYear, String type);

	@Modifying
	@Transactional
	@Query(value="delete FROM user_leave WHERE user_leave_id=:leaveId",nativeQuery=true)
	public void deleteleaveMarking(long leaveId) throws Exception;

	@Query(value="Select * from user_leave where leave_from >=:startDate1 AND leave_to<=:endDate1 AND leave_type=:leaveType",nativeQuery=true)
	List<LeaveModel> getleavelist(Date startDate1, Date endDate1, String leaveType);
	

}
