package com.EMS.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
//	public List<LeaveModel> getWeeklyLeavelist(LeaveModel leavemodel) {
//		System.out.println("date :"+leavemodel.getLeaveFrom()+"leave:"+leavemodel.getLeaveTo());
//		List<LeaveModel> list=leaveRepository.getweeklyleavelist(leavemodel.getLeaveFrom(),leavemodel.getLeaveTo());
//		System.out.println("list :"+list.size());
//		return list;
//	}

	

	@Override
	public List<LeaveModel> getWeeklyLeavelist(Date date1) {
		System.out.println("date :"+date1);
		List<LeaveModel> list=leaveRepository.getweeklyleavelist(date1);
		System.out.println("list :"+list.size());
		return list;
	}

	@Override
	public List<LeaveModel> getYearlyLeavelist(long userId, Date startDate1, Date endDate1) {
		
		List<LeaveModel> list=leaveRepository.getyearlyleavelist(userId,startDate1,endDate1);
		return list;
	}

//	@Override
//	public Object getUserLeaveBalance(Long userId, LocalDate firstDayOfYear, LocalDate lastDayOfYear) {
//		Object count = leaveRepository.getLeaveCount(userId, firstDayOfYear, lastDayOfYear);
//		return count;
//	}

	
	@Override
	public void saveLeaveMarking(LeaveModel leaveModel) {
		leaveRepository.save(leaveModel);
		
	}
}
