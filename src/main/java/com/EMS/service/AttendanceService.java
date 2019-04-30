package com.EMS.service;

import java.time.LocalDate;
import java.util.List;

import com.EMS.model.HolidayModel;

public interface AttendanceService {

	public List<HolidayModel> getHolidayList();

//	public List<Object[]> getUserLeaveList(Long userId);

	public List<Object[]> getUserLeaveList(Long userId, LocalDate firstDayOfYear, LocalDate lastDayOfYear);

//	public Object getUserLeaveBalance(Long userId, LocalDate firstDayOfYear, LocalDate lastDayOfYear);

}
