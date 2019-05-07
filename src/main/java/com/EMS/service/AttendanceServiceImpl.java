package com.EMS.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EMS.model.HolidayModel;
import com.EMS.model.LeaveBalanceModel;
import com.EMS.model.LeaveModel;
import com.EMS.model.UserModel;
import com.EMS.repository.HolidayRepository;
import com.EMS.repository.LeaveBalanceRepository;
import com.EMS.repository.LeaveRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class AttendanceServiceImpl implements AttendanceService {

	@Autowired
	HolidayRepository holidayRepository;

	@Autowired
	LeaveRepository leaveRepository;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	LeaveBalanceRepository leaveBalanceRepository;
	@Override
	public List<Object[]> getHolidayList() {
		List<Object[]> list = holidayRepository.getHolidayLists();
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
		System.out.println("date :"+date1+" list :"+list.size());
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

	
	@Override
	public ObjectNode getLeavebalanceData(Long userId, int quarter, int year) {
		ObjectNode leaveBalanceNode = objectMapper.createObjectNode();

		 	Double clBalance = 0.0;
		    Double slBalance = 0.0;
		    Double elBalance = 0.0;

			    
				for (int i = 1; i <= quarter; i++) {

					LeaveBalanceModel leaveBalanceOld = leaveBalanceRepository.getLeaveBalance(userId, i,
							year);
					if (leaveBalanceOld.getClBalance() > 0) {
						clBalance+= leaveBalanceOld.getClBalance();
												
					}
					if (leaveBalanceOld.getSlBalance() > 0) {
						slBalance+= leaveBalanceOld.getSlBalance();
					}
					if (leaveBalanceOld.getElBalance() > 0) {
						elBalance+= leaveBalanceOld.getElBalance();
					}
					leaveBalanceNode.put("balanceId", leaveBalanceOld.getLeaveBalanceId());
				}


			leaveBalanceNode.put("CL", clBalance);
			leaveBalanceNode.put("SL", slBalance);
			leaveBalanceNode.put("EL", elBalance);
			

		return leaveBalanceNode;
	}

	@Override
	public Boolean checkLeave(LeaveModel leaveModel) {
		int result=leaveRepository.checkuser(leaveModel.getUser().getUserId(),leaveModel.getLeaveFrom(),leaveModel.getLeaveTo()); 
		System.out.println("check count:"+result);
		if(result==0)
			return true;
		else
			return false;
	}

	@Override
	public void setLeaveBalance(LeaveBalanceModel leavebal) {
		leaveBalanceRepository.save(leavebal);
		
	}

	@Override
	public List<Long> getAllUserId() {
		List<Long> list=leaveBalanceRepository.getUserId();
		return list;
	}

	@Override
	public List<LeaveBalanceModel> getUserLeaveBalance(LeaveBalanceModel lBalance) {
		List<LeaveBalanceModel> list=leaveBalanceRepository.getuserLeaveBalance(lBalance.getYear(),lBalance.getQuarter(),lBalance.getUser().getUserId());
		return list;
	}

	@Override
	public List<LeaveModel> getLeavelist(Date startDate1, Date endDate1) {
		List<LeaveModel> list=leaveRepository.getyearlyleavelist(startDate1,endDate1);
		return list;
	}

	@Override
	public LeaveModel Leavedetails(long leaveId) {
		LeaveModel leavedata=leaveRepository.getOne(leaveId);
		return leavedata;
	}

	
}
