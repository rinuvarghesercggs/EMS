package com.EMS.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EMS.model.HolidayModel;
import com.EMS.repository.HolidayRepository;

@Service
public class AttendanceServiceImpl implements AttendanceService {

	@Autowired
	HolidayRepository holidayRepository;
	
	@Override
	public List<HolidayModel> getHolidayList() {
		List<HolidayModel> list = holidayRepository.findAll();
		return list;
	}

}
