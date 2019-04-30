package com.EMS.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EMS.model.HolidayModel;
import com.EMS.repository.HolidayRepository;
import com.EMS.repository.LeaveRepository;

@Service
public class AttendanceServiceImpl implements AttendanceService {

	@Autowired
	HolidayRepository holidayRepository;

	@Autowired
	LeaveRepository leaveRepository;

	@Override
	public List<HolidayModel> getHolidayList() {
		List<HolidayModel> list = holidayRepository.findAll();
		return list;
	}

//	@Override
//	public List<Object[]> getUserLeaveList(Long userId) {
//		List<Object[]> leaveList = leaveRepository.getUserLeaveList(userId);
//		return leaveList;
//	}

	@Override
	public List<Object[]> getUserLeaveList(Long userId, LocalDate firstDayOfYear, LocalDate lastDayOfYear) {
		List<Object[]> leaveList = leaveRepository.getUserLeaveList(userId, firstDayOfYear, lastDayOfYear);
		return leaveList;
	}

//	@Override
//	public Object getUserLeaveBalance(Long userId, LocalDate firstDayOfYear, LocalDate lastDayOfYear) {
//		Object count = leaveRepository.getLeaveCount(userId, firstDayOfYear, lastDayOfYear);
//		return count;
//	}

}
