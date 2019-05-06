package com.EMS.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.EMS.model.HolidayModel;
import com.EMS.model.LeaveBalanceModel;
import com.EMS.model.LeaveModel;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface AttendanceService {

	public List<Object[]> getHolidayList();

//	public List<Object[]> getUserLeaveList(Long userId);

	public List<Object[]> getUserLeaveList(Long userId, LocalDate firstDayOfYear, LocalDate lastDayOfYear);

//	public List<LeaveModel> getWeeklyLeavelist(LeaveModel leavemodel);

	
	public List<LeaveModel> getWeeklyLeavelist(Date date1);

	public List<LeaveModel> getYearlyLeavelist(long userId, Date startDate1, Date endDate1);

	public void saveLeaveMarking(LeaveModel leaveModel);


	public ObjectNode getLeavebalanceData(Long userId, int quarter, int year);

//	public Object getUserLeaveBalance(Long userId, LocalDate firstDayOfYear, LocalDate lastDayOfYear);

}
