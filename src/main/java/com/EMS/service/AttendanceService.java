package com.EMS.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.EMS.model.HolidayModel;
import com.EMS.model.LeaveBalanceModel;
import com.EMS.model.LeaveModel;
import com.EMS.model.UserModel;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface AttendanceService {

	public List<Object[]> getHolidayList();


	public List<Object[]> getUserLeaveList(Long userId, LocalDate firstDayOfYear, LocalDate lastDayOfYear);

	public List<LeaveModel> getWeeklyLeavelist(Date date1);

	public List<LeaveModel> getYearlyLeavelist(long userId, Date startDate1, Date endDate1);

	public void saveLeaveMarking(LeaveModel leaveModel);

	public ObjectNode getLeavebalanceData(Long userId, int quarter, int year);

	public Boolean checkLeave(LeaveModel leaveModel);

	public void setLeaveBalance(LeaveBalanceModel leavebal);

	public List<Long> getAllUserId();

	public Boolean checkLeaveBalance(LeaveBalanceModel lBalance);

	public void deleteBalance(LeaveBalanceModel lBalance);

	public LeaveBalanceModel getUserLeaveBalance(int i, int year, long userId);

	public List<LeaveModel> getLeavelist(Date startDate1, Date endDate1);

	public LeaveModel Leavedetails(long leaveId);

	public List<Object[]> getUserLeaveListByLeaveType(Long userId, String type, LocalDate firstDayOfYear,
			LocalDate lastDayOfYear);

	public LeaveModel getLeaveDetails(long leaveId);


	public void deleteLeaveMarking(long leaveId);

}
