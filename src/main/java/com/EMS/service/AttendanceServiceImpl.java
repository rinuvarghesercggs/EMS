package com.EMS.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EMS.model.HolidayModel;
import com.EMS.model.LeaveModel;
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

	@Override
	public List<LeaveModel> getUserLeaveList(Long userId) {
		List<LeaveModel> leaveList = leaveRepository.getUserLeaveList(userId);
		System.out.println("leave list size 1 : "+leaveList.size());
		return leaveList;
	}

	
}
