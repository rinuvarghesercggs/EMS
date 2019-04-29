package com.EMS.service;

import java.util.List;

import com.EMS.model.HolidayModel;
import com.EMS.model.LeaveModel;

public interface AttendanceService {
	
	public List<HolidayModel> getHolidayList();

	public List<LeaveModel> getUserLeaveList(Long userId);


}
