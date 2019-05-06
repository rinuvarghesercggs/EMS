package com.EMS.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EMS.model.HolidayModel;
import com.EMS.model.LeaveBalanceModel;
import com.EMS.model.LeaveModel;
import com.EMS.model.UserModel;
import com.EMS.service.AttendanceService;
import com.EMS.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import ch.qos.logback.core.pattern.parser.Node;

@RestController
@RequestMapping(value = "/attendance")
public class AttendanceController {
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private AttendanceService attendanceService;
	
	@Autowired
	private UserService userservice;
	
	@GetMapping("/getHolidayList")
	public ObjectNode getHolidayList(HttpServletResponse httpstatus) {

		ArrayNode jsonArray = objectMapper.createArrayNode();
		ObjectNode jsonDataRes = objectMapper.createObjectNode();

		try {
			List<Object[]> holidayList = attendanceService.getHolidayList();
			System.out.println("holidayList : "+holidayList.size());
//			ArrayNode node = objectMapper.convertValue(attendanceService.getHolidayList(), ArrayNode.class);
			for (Object[] item : holidayList) {
				ObjectNode holidayNode = objectMapper.createObjectNode();
				String holidayId = item[1].toString();
				String date = item[0].toString();
				String day = item[2].toString();
				String holidayName = item[3].toString();
				String holidayType = item[4].toString();
				holidayNode.put("holidayId", holidayId);
				holidayNode.put("date", date);
				holidayNode.put("day", day);
				holidayNode.put("holidayName", holidayName);
				holidayNode.put("holidayType", holidayType);
				jsonArray.add(holidayNode);

			}
			jsonDataRes.set("data", jsonArray);
			jsonDataRes.put("status", "success");
			jsonDataRes.put("message", "success. ");
			jsonDataRes.put("code", httpstatus.getStatus());

		} catch (Exception e) {
			jsonDataRes.put("status", "failure");
			jsonDataRes.put("code", httpstatus.getStatus());
			jsonDataRes.put("message", "failed. " + e);
		}

		return jsonDataRes;

	}
	

	
	@PostMapping("/getWeeklyLeaveList")
	public JsonNode getweeklyLeeveList(@RequestBody JsonNode requestdata, HttpServletResponse response) {
		ObjectNode responseData = objectMapper.createObjectNode();

		String startDate = requestdata.get("startdate").asText();
		String endDate = requestdata.get("endDate").asText();

		try {

			LocalDate start = LocalDate.parse(startDate);
			LocalDate end = LocalDate.parse(endDate);
			ObjectNode datewise = objectMapper.createObjectNode();

			while (!start.isAfter(end)) {

				System.out.println("dates :" + start);
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
				String stgdate = String.valueOf(start);
				Date date1 = formatter.parse(stgdate);

				List<LeaveModel> leavelist = attendanceService.getWeeklyLeavelist(date1);
				ArrayNode leaverecord = objectMapper.createArrayNode();

				for (LeaveModel leave : leavelist) {

					ObjectNode node = objectMapper.createObjectNode();
					System.out.println("userid :" + leave.getUser().getUserId());
					String userDetail = userservice.getUserName(leave.getUser().getUserId());
					String username = userDetail.replace(",", " ");
					System.out.println("username:" + username);

					node.put("username", username);
					node.put("startDate", leave.getLeaveFrom().toString());
					node.put("reason", leave.getLeaveReason().toString());
//						node.put("endDate", leave.getLeaveTo().toString());
					if (leave.getCL() != null) {
						node.put("leaveType", "CL");
						node.put("leaveCount", leave.getCL());
					}
					if (leave.getEL() != null) {
						node.put("leaveType", "EL");
//							node.put("leaveCount", leave.getEL());
					}
					if (leave.getLOP() != null) {
						node.put("leaveType", "LOP");
//							node.put("leaveCount", leave.getLOP());
					}

					if (leave.getSL() != null) {
						node.put("leaveType", "SL");
//							node.put("leaveCount", leave.getSL());
					}
					leaverecord.add(node);
				}

				datewise.set(stgdate, leaverecord);
				start = start.plusDays(1);
			}
			responseData.put("status", "success");
			responseData.put("code", response.getStatus());
			responseData.put("message", "success");
			responseData.set("payload", datewise);

		} catch (Exception e) {

			responseData.put("status", "Failed");
			responseData.put("code", response.getStatus());
			responseData.put("message", "Exception:" + e);
			e.printStackTrace();
			e.printStackTrace();
		}

		return responseData;
	}

	@PostMapping("/getYearlyLeaveList")
	public JsonNode getYearlyLeeveList(@RequestBody JsonNode requestdata, HttpServletResponse response) {
		ObjectNode responseData = objectMapper.createObjectNode();

		String startDate = requestdata.get("startdate").asText();
		String endDate = requestdata.get("endDate").asText();

		try {

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
			Date startDate1 = formatter.parse(startDate);
			Date endDate1 = formatter.parse(endDate);

			ArrayNode userleaverecord = objectMapper.createArrayNode();
			List<UserModel> userlist = userservice.getAllUsers();
			if (!userlist.isEmpty()) {
				System.out.println("array size :" + userlist.size());
				for (UserModel user : userlist) {

					ObjectNode node = objectMapper.createObjectNode();
					node.put("employeeID", user.getEmpId());
					node.put("employeeName", user.getFirstName() + " " + user.getLastName());

					System.out.println("userid :" + user.getUserId());
					List<LeaveModel> leavelist = attendanceService.getYearlyLeavelist(user.getUserId(), startDate1,
							endDate1);
					int clCount = 0, elCount = 0, lopCount = 0, slCount = 0;

					for (LeaveModel leave : leavelist) {

						if (leave.getCL() != null)
							clCount++;

						if (leave.getEL() != null)
							elCount++;

						if (leave.getLOP() != null)
							lopCount++;

						if (leave.getSL() != null)
							slCount++;

					}
					node.put("casualLeave", clCount);
					node.put("earnedLeave", elCount);
					node.put("LOP", lopCount);
					node.put("sickLeave", slCount);

					userleaverecord.add(node);
				}

			}

			responseData.put("status", "success");
			responseData.put("code", response.getStatus());
			responseData.put("message", "success");
			responseData.set("payload", userleaverecord);

		} catch (Exception e) {
			responseData.put("status", "Failed");
			responseData.put("code", response.getStatus());
			responseData.put("message", "Exception:" + e);
			e.printStackTrace();
		}

		return responseData;
	}

	@PostMapping("/leaveMarking")
	public ObjectNode setLeaveMarking(@RequestBody JsonNode requestdata, HttpServletResponse httpstatus) {
		ObjectNode jsonDataRes = objectMapper.createObjectNode();
		try {

			TimeZone zone = TimeZone.getTimeZone("MST");
			SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
			outputFormat.setTimeZone(zone);

			Long userId = requestdata.get("userId").asLong();
			UserModel user = userservice.getUserDetailsById(userId);

			LocalDate now = LocalDate.now();

			ArrayNode leaveNode = (ArrayNode) requestdata.get("leaveList");
			System.out.println("leaveNode size : " + leaveNode.size());
			for (JsonNode node : leaveNode) {
				LeaveModel leaveModel = new LeaveModel();
				leaveModel.setUser(user);
				leaveModel.setStatus(requestdata.get("status").asText());
				leaveModel.setAppliedDate(outputFormat.parse(now.toString()));
				leaveModel.setLeaveReason(node.get("reason").asText());
				String startDate = node.get("startdate").asText();
				String endDate = node.get("enddate").asText();
				System.out.println("start:"+startDate);
				if (!startDate.isEmpty()) {
					leaveModel.setLeaveFrom(outputFormat.parse(startDate));
				}
				if (!endDate.isEmpty()) {
					leaveModel.setLeaveTo(outputFormat.parse(endDate));
				}
				
				double leaveCount = node.get("count").asDouble();
				System.out.println("leave count:" + leaveCount);

				LocalDate startdate = LocalDate.parse(startDate);
				LocalDate enddate=LocalDate.parse(endDate);
				
				// balance check
				LeaveBalanceModel lBalance = new LeaveBalanceModel();
				int year = startdate.getYear();
				lBalance.setYear(year);
				lBalance.setUser(leaveModel.getUser());
				int month = startdate.getMonthValue();
				System.out.println("month :" + month);
				int quarterleave = 0;
				if (month <= 3) {
					quarterleave = 1;
				} else if (month <= 6 && month > 3) {
					quarterleave = 2;
				} else if (month <= 9 && month > 6) {
					quarterleave = 3;
				} else if (month <= 12 && month > 9) {
					quarterleave = 4;
					
				}
	
				lBalance.setQuarter(quarterleave);
				System.out.println("quat:" + lBalance.getQuarter() + "yera:" + lBalance.getYear() + " :user"
						+ lBalance.getUser().getUserId());
				List<LeaveBalanceModel> balList = attendanceService.getUserLeaveBalance(lBalance);
				System.out.println("list size:"+balList.size());
				
				LeaveBalanceModel leavebal = balList.get(0);
				System.out.println("sl:"+leavebal.getSlBalance()+" el:"+leavebal.getElBalance()+" cl:"+leavebal.getClBalance());
			
			
				double leavebalance = 0;
				
				
				if (node.get("leaveType").asText().equals("cl")) {

					if (leavebal.getClBalance() >= leaveCount) {
						
						leavebalance = leavebal.getClBalance();
						leavebalance = leavebalance - leaveCount;
						System.out.println("new bal:"+leavebalance+" count:"+leaveCount);
						leavebal.setClBalance(leavebalance);
						leaveModel.setCL(leaveCount);
					} else {
						leaveCount = leaveCount - leavebal.getClBalance();
						leaveModel.setLOP(leaveCount);
						leaveModel.setCL(leavebal.getClBalance());
						System.out.println("new bal:"+leavebalance+" count:"+leaveCount);
						leavebal.setClBalance(0);
					}
				} else if (node.get("leaveType").asText().equals("sl")) {
					if (leavebal.getSlBalance() >= leaveCount) {
						leavebalance = leavebal.getSlBalance();
						leavebalance = leavebalance - leaveCount;
						System.out.println("new bal:"+leavebalance+" count:"+leaveCount);
						leavebal.setSlBalance(leavebalance);
						leaveModel.setSL(leaveCount);
					} else {
						leaveCount = leaveCount - leavebal.getSlBalance();
						leaveModel.setLOP(leaveCount);
						leaveModel.setSL(leavebal.getSlBalance());
						System.out.println("new bal:"+leavebalance+" count:"+leaveCount);
						leavebal.setSlBalance(0);
					}
				} else if (node.get("leaveType").asText().equals("lop")) {
				
					leaveModel.setLOP(leaveCount);
				} else if (node.get("leaveType").asText().equals("el")) {
					if (leavebal.getElBalance() >= leaveCount) {
						
						leavebalance = leavebal.getElBalance();
						leavebalance = leavebalance - leaveCount;
						leavebal.setElBalance(leavebalance);
						leaveModel.setEL(leaveCount);
					} else {
						leaveCount = leaveCount - leavebal.getElBalance();
						leaveModel.setEL(leavebal.getElBalance());
						leaveModel.setLOP(leaveCount);
						leavebal.setElBalance(0);
						System.out.println("new bal:"+leavebalance+" count:"+leaveCount);
					}
				}

				
					attendanceService.setLeaveBalance(leavebal);
					
					Boolean checkvalue = attendanceService.checkLeave(leaveModel);
					if (checkvalue) {
						attendanceService.saveLeaveMarking(leaveModel);
						jsonDataRes.put("status", "success");
						jsonDataRes.put("message", "successfully saved.");
						jsonDataRes.put("code", httpstatus.getStatus());
					} else {
						jsonDataRes.put("status", "failure");
						jsonDataRes.put("code", httpstatus.getStatus());
						jsonDataRes.put("message", "duplicate entry");
					}

				
				
				
			}

		} catch (Exception e) {
			jsonDataRes.put("status", "failure");
			jsonDataRes.put("code", httpstatus.getStatus());
			jsonDataRes.put("message", "failed. " + e);
		}
		return jsonDataRes;

	}

	@PostMapping("/getLeaveBalanceList")
	public JsonNode getLeaveBalanceList(@RequestBody JsonNode requestdata, HttpServletResponse response) {
		ObjectNode responseData = objectMapper.createObjectNode();

		String leaveFrom = requestdata.get("startDate").asText();
		String leaveTo=requestdata.get("endDate").asText();
		try {

			LocalDate start = LocalDate.parse(leaveFrom);
			
			int year = start.getYear();
			int month = start.getMonthValue();
			System.out.println("month :" + month);
			int quarterleave = 0;
			if (month <= 3) {
				quarterleave = 1;
			} else if (month <= 6 && month > 3) {
				quarterleave = 2;
			} else if (month <= 9 && month > 6) {
				quarterleave = 3;
			} else if (month <= 12 && month > 9) {
				quarterleave = 4;
				
			}
			LeaveBalanceModel lBalance = new LeaveBalanceModel();
			lBalance.setQuarter(quarterleave);
			lBalance.setYear(year);
			
			ArrayNode leaverecord = objectMapper.createArrayNode();
			List<Long> userlist = attendanceService.getAllUserId();
			if (!userlist.isEmpty()) {
				System.out.println("array size :" + userlist.size());
				for (int i=0;i<userlist.size();i++) {
					ObjectNode node = objectMapper.createObjectNode();
					String data=String.valueOf(userlist.get(i));
					Long userId=Long.parseLong(data);
					System.out.println(data+"user id:"+userId);
					UserModel userdata=userservice.getUserDetailsById(userId);
					System.out.println("userid :" + userlist.get(i));
					ObjectNode leavebalance = attendanceService.getLeavebalanceData(userId, lBalance.getQuarter(), lBalance.getYear());
					node.put("empId", userdata.getEmpId());
					node.put("userId", userdata.getUserId());
					node.put("employeeName", userdata.getFirstName()+" "+userdata.getLastName());
					node.put("clBalance", leavebalance.get("CL").asDouble());
					node.put("elBalance", leavebalance.get("EL").asDouble());
					node.put("slBalance", leavebalance.get("SL").asDouble());
					leaverecord.add(node);
					System.out.println("for end");
				}

			}

			responseData.put("status", "success");
			responseData.put("code", response.getStatus());
			responseData.put("message", "success");
			responseData.set("payload", leaverecord);

		} catch (Exception e) {
			responseData.put("status", "Failed");
			responseData.put("code", response.getStatus());
			responseData.put("message", "Exception:" + e);
			e.printStackTrace();
		}

		return responseData;
	}


	
	
	@PostMapping("/getLeaveList")
	public JsonNode getLeaveList(@RequestBody JsonNode requestdata, HttpServletResponse response) {
		ObjectNode responseData = objectMapper.createObjectNode();

		String startDate = requestdata.get("startdate").asText();
		String endDate = requestdata.get("endDate").asText();

		try {

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
			Date startDate1 = formatter.parse(startDate);
			Date endDate1 = formatter.parse(endDate);

			ArrayNode leaverecord = objectMapper.createArrayNode();	
			
			List<LeaveModel> leavelist = attendanceService.getLeavelist(startDate1,endDate1);

					for (LeaveModel leave : leavelist) {
						
						ObjectNode leavenode=objectMapper.createObjectNode();
						UserModel user=userservice.getUserDetailsById(leave.getUser().getUserId());
						leavenode.put("empId", user.getEmpId());
						leavenode.put("employeeName", user.getFirstName()+" "+user.getLastName());
						leavenode.put("casualLeave", leave.getCL());
						leavenode.put("earnedLeave", leave.getEL());
						leavenode.put("sickLeave", leave.getSL());
						leavenode.put("leaveFrom", leave.getLeaveFrom().toString());
						leavenode.put("leaveTo", leave.getLeaveTo().toString());
						leavenode.put("leaveReason", leave.getLeaveReason());
						
						leaverecord.add(leavenode);
					}
					
			responseData.put("status", "success");
			responseData.put("code", response.getStatus());
			responseData.put("message", "success");
			responseData.set("payload", leaverecord);

		} catch (Exception e) {
			responseData.put("status", "Failed");
			responseData.put("code", response.getStatus());
			responseData.put("message", "Exception:" + e);
			e.printStackTrace();
		}

		return responseData;
	}

}
