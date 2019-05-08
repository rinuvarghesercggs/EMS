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
			System.out.println("holidayList : " + holidayList.size());
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
	
	
	@PostMapping("/getUserLeaveList")
	public ObjectNode getUserLeaveList(@RequestBody JsonNode requestdata,HttpServletResponse httpstatus) {
		
		ArrayNode jsonArray = objectMapper.createArrayNode();
		ObjectNode jsonDataRes = objectMapper.createObjectNode();
		ObjectNode leaveBalanceNode = objectMapper.createObjectNode();

		Long userId = null;
        String type = null,date1 = null, date2 = null;
		
		LocalDate date = LocalDate.now();
		LocalDate firstDayOfYear = date.with(TemporalAdjusters.firstDayOfYear());
		LocalDate lastDayOfYear = date.with(TemporalAdjusters.lastDayOfYear());
		
		int quarter = 0;
		int monthNumber = date.getMonthValue();
		int year = date.getYear();

		if (monthNumber >= 1 && monthNumber <= 3)
			quarter = 1;
		else if (monthNumber >= 4 && monthNumber <= 6)
			quarter = 2;
		else if (monthNumber >= 7 && monthNumber <= 9)
			quarter = 3;
		else if (monthNumber >= 10 && monthNumber <= 12)
			quarter = 4;

	

		try {
			if (requestdata.get("userId") != null && requestdata.get("userId").asText() != "") 
				userId = requestdata.get("userId").asLong();
			if (requestdata.get("leaveType") != null && requestdata.get("leaveType").asText() != "") 
				type = requestdata.get("leaveType").asText();
			if (requestdata.get("leaveFrom") != null && requestdata.get("leaveFrom").asText() != "") 
				date1 = requestdata.get("leaveFrom").asText();
			if (requestdata.get("leaveTo") != null && requestdata.get("leaveTo").asText() != "") 
				date2 = requestdata.get("leaveTo").asText();

				LocalDate startDate = null, endDate = null;
				if (date1 != null && !date1.isEmpty()) {
					startDate = LocalDate.parse(date1);
					System.out.println("start date : "+startDate);
				}
				if (date1 != null && !date2.isEmpty()) {
					endDate = LocalDate.parse(date2);
				}

				List<Object[]> leaveModel = null;

				if (userId != null && type == null && startDate == null && endDate == null) 
					leaveModel = attendanceService.getUserLeaveList(userId, firstDayOfYear,lastDayOfYear);
				else if(userId != null && type != null && startDate == null && endDate == null)
					leaveModel = attendanceService.getUserLeaveListByLeaveType(userId,type,firstDayOfYear,lastDayOfYear);
				else if(userId != null && type != null && startDate != null && endDate != null)
					leaveModel = attendanceService.getUserLeaveListByLeaveType(userId,type,startDate,endDate);
				else if(userId != null && type == null && startDate != null && endDate != null)
					leaveModel = attendanceService.getUserLeaveList(userId,startDate,endDate);


					if(leaveModel != null) {
				System.out.println("leave list size : " + leaveModel.size());

				for (Object[] leave : leaveModel) {
					ObjectNode leaveObject = objectMapper.createObjectNode();

					String leaveType = leave[4].toString();
					String leaveCount = leave[5].toString();
					String leaveDescription = leave[2].toString();
					String leaveFrom = leave[0].toString();
					String leaveTo = leave[1].toString();
					String status = leave[3].toString();

					leaveObject.put("Leave Type", leaveType);
					leaveObject.put("Leave Count", leaveCount);
					leaveObject.put("Leave Description", leaveDescription);
					leaveObject.put("Start Date", leaveFrom);
					leaveObject.put("End Date", leaveTo);
					leaveObject.put("Status", status);
					jsonArray.add(leaveObject);

				}

				// to obtain user leave balance details
				leaveBalanceNode = attendanceService.getLeavebalanceData(userId, quarter, year);

			}
			

			jsonDataRes.set("data", jsonArray);
			jsonDataRes.set("leaveBalance", leaveBalanceNode);
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

//	@PostMapping("/getWeeklyLeaveList")
//	public JsonNode getweeklyLeeveList(@RequestBody JsonNode requestdata, HttpServletResponse response) {
//		ObjectNode responseData = objectMapper.createObjectNode();
//
//		String startDate = requestdata.get("startDate").asText();
//		String endDate = requestdata.get("endDate").asText();
//
//		try {
//
//			LocalDate start = LocalDate.parse(startDate);
//			LocalDate end = LocalDate.parse(endDate);
//			ObjectNode datewise = objectMapper.createObjectNode();
//
//			while (!start.isAfter(end)) {
//
//				System.out.println("dates :" + start);
//				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
//				String stgdate = String.valueOf(start);
//				Date date1 = formatter.parse(stgdate);
//
//				List<LeaveModel> leavelist = attendanceService.getWeeklyLeavelist(date1);
//				ArrayNode leaverecord = objectMapper.createArrayNode();
//
//				for (LeaveModel leave : leavelist) {
//
//					ObjectNode node = objectMapper.createObjectNode();
//					System.out.println("userid :" + leave.getUser().getUserId());
//					String userDetail = userservice.getUserName(leave.getUser().getUserId());
//					String username = userDetail.replace(",", " ");
//					System.out.println("username:" + username);
//
//					node.put("username", username);
//					node.put("startDate", leave.getLeaveFrom().toString());
//					node.put("reason", leave.getLeaveReason().toString());
//					node.put("endDate", leave.getLeaveTo().toString());
//					if (leave.getCL() != null) {
//						node.put("leaveType", "CL");
//						node.put("clCount", leave.getCL());
//					}
//					if (leave.getEL() != null) {
//						node.put("leaveType", "EL");
//						node.put("elCount", leave.getEL());
//					}
//					if (leave.getLOP() != null) {
//						node.put("leaveType", "LOP");
//						node.put("lopCount", leave.getLOP());
//					}
//
//					if (leave.getSL() != null) {
//						node.put("leaveType", "SL");
//						node.put("slCount", leave.getSL());
//					}
//					leaverecord.add(node);
//				}
//
//				datewise.set(stgdate, leaverecord);
//				start = start.plusDays(1);
//			}
//			responseData.put("status", "success");
//			responseData.put("code", response.getStatus());
//			responseData.put("message", "success");
//			responseData.set("payload", datewise);
//
//		} catch (Exception e) {
//
//			responseData.put("status", "Failed");
//			responseData.put("code", response.getStatus());
//			responseData.put("message", "Exception:" + e);
//			e.printStackTrace();
//			e.printStackTrace();
//		}
//
//		return responseData;
//	}

//	@PostMapping("/getYearlyLeaveList")
//	public JsonNode getYearlyLeeveList(@RequestBody JsonNode requestdata, HttpServletResponse response) {
//		ObjectNode responseData = objectMapper.createObjectNode();
//
//		String startDate = requestdata.get("startDate").asText();
//		String endDate = requestdata.get("endDate").asText();
//
//		try {
//
//			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
//			Date startDate1 = formatter.parse(startDate);
//			Date endDate1 = formatter.parse(endDate);
//
//			ArrayNode userleaverecord = objectMapper.createArrayNode();
//			List<UserModel> userlist = userservice.getAllUsers();
//			if (!userlist.isEmpty()) {
//				System.out.println("array size :" + userlist.size());
//				for (UserModel user : userlist) {
//
//					ObjectNode node = objectMapper.createObjectNode();
//					node.put("employeeID", user.getEmpId());
//					node.put("employeeName", user.getFirstName() + " " + user.getLastName());
//
//					System.out.println("userid :" + user.getUserId());
//					List<LeaveModel> leavelist = attendanceService.getYearlyLeavelist(user.getUserId(), startDate1,
//							endDate1);
//					double clCount = 0, elCount = 0, lopCount = 0, slCount = 0;
//
//					for (LeaveModel leave : leavelist) {
//
//						if (leave.getCL() != null)
//							clCount = clCount + leave.getCL();
//
//						if (leave.getEL() != null)
//							elCount = elCount + leave.getEL();
//
//						if (leave.getLOP() != null)
//							lopCount = lopCount + leave.getLOP();
//
//						if (leave.getSL() != null)
//							slCount = slCount + leave.getSL();
//
//					}
//					node.put("casualLeave", clCount);
//					node.put("earnedLeave", elCount);
//					node.put("LOP", lopCount);
//					node.put("sickLeave", slCount);
//
//					userleaverecord.add(node);
//				}
//
//			}
//
//			responseData.put("status", "success");
//			responseData.put("code", response.getStatus());
//			responseData.put("message", "success");
//			responseData.set("payload", userleaverecord);
//
//		} catch (Exception e) {
//			responseData.put("status", "Failed");
//			responseData.put("code", response.getStatus());
//			responseData.put("message", "Exception:" + e);
//			e.printStackTrace();
//		}
//
//		return responseData;
//	}

//	@PostMapping("/leaveMarking")
//	public ObjectNode setLeaveMarking(@RequestBody JsonNode requestdata, HttpServletResponse httpstatus) {
//		ObjectNode jsonDataRes = objectMapper.createObjectNode();
//		try {
//
//			TimeZone zone = TimeZone.getTimeZone("MST");
//			SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
//			outputFormat.setTimeZone(zone);
//
//			Long userId = requestdata.get("userId").asLong();
//			UserModel user = userservice.getUserDetailsById(userId);
//
//			LocalDate now = LocalDate.now();
//
//			ArrayNode leaveNode = (ArrayNode) requestdata.get("leaveList");
//			System.out.println("leaveNode size : " + leaveNode.size());
//			for (JsonNode node : leaveNode) {
//				LeaveModel leaveModel = new LeaveModel();
//				leaveModel.setUser(user);
//				leaveModel.setStatus(requestdata.get("status").asText());
//				leaveModel.setAppliedDate(outputFormat.parse(now.toString()));
//				leaveModel.setLeaveReason(node.get("reason").asText());
//				String startDate = node.get("startdate").asText();
//				String endDate = node.get("enddate").asText();
//				System.out.println("start:" + startDate);
//				if (!startDate.isEmpty()) {
//					leaveModel.setLeaveFrom(outputFormat.parse(startDate));
//				}
//				if (!endDate.isEmpty()) {
//					leaveModel.setLeaveTo(outputFormat.parse(endDate));
//				}
//
//				double leaveCount = node.get("count").asDouble();
//				System.out.println("leave count:" + leaveCount);
//
//				LocalDate startdate = LocalDate.parse(startDate);
//				LocalDate enddate = LocalDate.parse(endDate);
//
//				// balance check
//				LeaveBalanceModel lBalance = new LeaveBalanceModel();
//				int year = startdate.getYear();
//				lBalance.setYear(year);
//				lBalance.setUser(leaveModel.getUser());
//				int month = startdate.getMonthValue();
//				System.out.println("month :" + month);
//				int quarterleave = 0;
//				if (month <= 3) {
//					quarterleave = 1;
//				} else if (month <= 6 && month > 3) {
//					quarterleave = 2;
//				} else if (month <= 9 && month > 6) {
//					quarterleave = 3;
//				} else if (month <= 12 && month > 9) {
//					quarterleave = 4;
//
//				}
//
//				lBalance.setQuarter(quarterleave);
//				System.out.println("quat:" + lBalance.getQuarter() + "yera:" + lBalance.getYear() + " :user"
//						+ lBalance.getUser().getUserId());
//				LeaveBalanceModel leavebal = attendanceService.getUserLeaveBalance(lBalance.getQuarter(),
//						lBalance.getYear(), lBalance.getUser().getUserId());
//
//				System.out.println("sl:" + leavebal.getSlBalance() + " el:" + leavebal.getElBalance() + " cl:"
//						+ leavebal.getClBalance());
//
//				double leavebalance = 0;
//
//				if (node.get("leaveType").asText().equals("cl")) {
//
//					if (leavebal.getClBalance() >= leaveCount) {
//
//						leavebalance = leavebal.getClBalance();
//						leavebalance = leavebalance - leaveCount;
//						System.out.println("new bal:" + leavebalance + " count:" + leaveCount);
//						leavebal.setClBalance(leavebalance);
//						leaveModel.setCL(leaveCount);
//					} else {
//						leaveCount = leaveCount - leavebal.getClBalance();
//						leaveModel.setLOP(leaveCount);
//						leaveModel.setCL(leavebal.getClBalance());
//						System.out.println("new bal:" + leavebalance + " count:" + leaveCount);
//						leavebal.setClBalance(0);
//					}
//				} else if (node.get("leaveType").asText().equals("sl")) {
//					if (leavebal.getSlBalance() >= leaveCount) {
//						leavebalance = leavebal.getSlBalance();
//						leavebalance = leavebalance - leaveCount;
//						System.out.println("new bal:" + leavebalance + " count:" + leaveCount);
//						leavebal.setSlBalance(leavebalance);
//						leaveModel.setSL(leaveCount);
//					} else {
//						leaveCount = leaveCount - leavebal.getSlBalance();
//						leaveModel.setLOP(leaveCount);
//						leaveModel.setSL(leavebal.getSlBalance());
//						System.out.println("new bal:" + leavebalance + " count:" + leaveCount);
//						leavebal.setSlBalance(0);
//					}
//				} else if (node.get("leaveType").asText().equals("lop")) {
//
//					leaveModel.setLOP(leaveCount);
//				} else if (node.get("leaveType").asText().equals("el")) {
//					if (leavebal.getElBalance() >= leaveCount) {
//
//						leavebalance = leavebal.getElBalance();
//						leavebalance = leavebalance - leaveCount;
//						leavebal.setElBalance(leavebalance);
//						leaveModel.setEL(leaveCount);
//					} else {
//						leaveCount = leaveCount - leavebal.getElBalance();
//						leaveModel.setEL(leavebal.getElBalance());
//						leaveModel.setLOP(leaveCount);
//						leavebal.setElBalance(0);
//						System.out.println("new bal:" + leavebalance + " count:" + leaveCount);
//					}
//				}
//
//				attendanceService.setLeaveBalance(leavebal);
//				boolean statusFlag = false;
//				Boolean checkvalue = attendanceService.checkLeave(leaveModel);
//				if (checkvalue) {
//					statusFlag = true;
//					attendanceService.saveLeaveMarking(leaveModel);
//					jsonDataRes.put("status", "success");
//					jsonDataRes.put("message", "successfully saved.");
//				} else {
//					jsonDataRes.put("status", "success");
//					jsonDataRes.put("message", "duplicate entry");
//				}
//				jsonDataRes.put("flag", statusFlag);
//				jsonDataRes.put("code", httpstatus.getStatus());
//			}
//
//		} catch (Exception e) {
//			jsonDataRes.put("status", "failure");
//			jsonDataRes.put("code", httpstatus.getStatus());
//			jsonDataRes.put("message", "failed. " + e);
//		}
//		return jsonDataRes;
//
//	}

	@GetMapping("/getLeaveBalanceList")
	public JsonNode getLeaveBalanceList(HttpServletResponse response) {
		ObjectNode responseData = objectMapper.createObjectNode();

//		String leaveFrom = requestdata.get("startDate").asText();
//		String leaveTo = requestdata.get("endDate").asText();
		try {

			LocalDate start = LocalDate.now();

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
				for (int i = 0; i < userlist.size(); i++) {
					ObjectNode node = objectMapper.createObjectNode();
					String data = String.valueOf(userlist.get(i));
					Long userId = Long.parseLong(data);
					System.out.println(data + "user id:" + userId);
					UserModel userdata = userservice.getUserDetailsById(userId);
					System.out.println("userid :" + userlist.get(i));
					ObjectNode leavebalance = attendanceService.getLeavebalanceData(userId, lBalance.getQuarter(),
							lBalance.getYear());
					node.put("empId", userdata.getEmpId());
					node.put("userId", userdata.getUserId());
					node.put("employeeName", userdata.getFirstName() + " " + userdata.getLastName());
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

	
//	@PostMapping("/getLeaveList")
//	public JsonNode getLeaveList(@RequestBody JsonNode requestdata, HttpServletResponse response) {
//		ObjectNode responseData = objectMapper.createObjectNode();
//
//		String startDate = requestdata.get("startdate").asText();
//		String endDate = requestdata.get("endDate").asText();
//
//		try {
//
//			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
//			Date startDate1 = formatter.parse(startDate);
//			Date endDate1 = formatter.parse(endDate);
//
//			ArrayNode leaverecord = objectMapper.createArrayNode();
//
//			List<LeaveModel> leavelist = attendanceService.getLeavelist(startDate1, endDate1);
//
//			for (LeaveModel leave : leavelist) {
//
//				ObjectNode leavenode = objectMapper.createObjectNode();
//				UserModel user = userservice.getUserDetailsById(leave.getUser().getUserId());
//				leavenode.put("empId", user.getEmpId());
//				leavenode.put("employeeName", user.getFirstName() + " " + user.getLastName());
//				leavenode.put("casualLeave", leave.getCL());
//				leavenode.put("earnedLeave", leave.getEL());
//				leavenode.put("sickLeave", leave.getSL());
//				leavenode.put("leaveFrom", leave.getLeaveFrom().toString());
//				leavenode.put("leaveTo", leave.getLeaveTo().toString());
//				leavenode.put("leaveReason", leave.getLeaveReason());
//				leavenode.put("leaveId", leave.getLeaveId());
//				leavenode.put("userId", user.getUserId());
//
//				leaverecord.add(leavenode);
//			}
//
//			responseData.put("status", "success");
//			responseData.put("code", response.getStatus());
//			responseData.put("message", "success");
//			responseData.set("payload", leaverecord);
//
//		} catch (Exception e) {
//			responseData.put("status", "Failed");
//			responseData.put("code", response.getStatus());
//			responseData.put("message", "Exception:" + e);
//			e.printStackTrace();
//		}
//
//		return responseData;
//	}

//	for editing leave 
//	@PostMapping("/editLeaveMarking")
//	public ObjectNode setEditLeaveMarking(@RequestBody JsonNode requestdata, HttpServletResponse httpstatus) {
//		ObjectNode jsonDataRes = objectMapper.createObjectNode();
//		try {
//
//			TimeZone zone = TimeZone.getTimeZone("MST");
//			SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
//			outputFormat.setTimeZone(zone);
//
//			Long userId = requestdata.get("userId").asLong();
//			UserModel user = userservice.getUserDetailsById(userId);
//
//			LocalDate now = LocalDate.now();
//
//			ArrayNode leaveNode = (ArrayNode) requestdata.get("leaveList");
//			System.out.println("leaveNode size : " + leaveNode.size());
//			for (JsonNode node : leaveNode) {
//				LeaveModel leaveModel = new LeaveModel();
//				leaveModel.setLeaveId(node.get("leaveId").asLong());
//				leaveModel.setUser(user);
//				leaveModel.setStatus(requestdata.get("status").asText());
//				leaveModel.setAppliedDate(outputFormat.parse(now.toString()));
//				leaveModel.setLeaveReason(node.get("reason").asText());
//				String startDate = node.get("startdate").asText();
//				String endDate = node.get("enddate").asText();
//				System.out.println("start:" + startDate);
//				if (!startDate.isEmpty()) {
//					leaveModel.setLeaveFrom(outputFormat.parse(startDate));
//				}
//				if (!endDate.isEmpty()) {
//					leaveModel.setLeaveTo(outputFormat.parse(endDate));
//				}
//
//				double leaveCount = node.get("count").asDouble();
//				System.out.println("leave count:" + leaveCount);
//
//				LocalDate startdate = LocalDate.parse(startDate);
//				LocalDate enddate = LocalDate.parse(endDate);
//
//				// balance check
//				LeaveBalanceModel lBalance = new LeaveBalanceModel();
//				int year = startdate.getYear();
//				lBalance.setYear(year);
//				lBalance.setUser(leaveModel.getUser());
//				int month = startdate.getMonthValue();
//				System.out.println("month :" + month);
//				int quarterleave = 0;
//				if (month <= 3) {
//					quarterleave = 1;
//				} else if (month <= 6 && month > 3) {
//					quarterleave = 2;
//				} else if (month <= 9 && month > 6) {
//					quarterleave = 3;
//				} else if (month <= 12 && month > 9) {
//					quarterleave = 4;
//
//				}
//
//				lBalance.setQuarter(quarterleave);
//				System.out.println("quat:" + lBalance.getQuarter() + "yera:" + lBalance.getYear() + " :user"
//						+ lBalance.getUser().getUserId());
//				LeaveBalanceModel leavebal = attendanceService.getUserLeaveBalance(lBalance.getQuarter(),
//						lBalance.getYear(), lBalance.getUser().getUserId());
//				System.out.println("sl:" + leavebal.getSlBalance() + " el:" + leavebal.getElBalance() + " cl:"
//						+ leavebal.getClBalance());
//
//				double leavebalance = 0;
//				LeaveModel checkleavevalue = attendanceService.Leavedetails(leaveModel.getLeaveId());
//				if (checkleavevalue.getCL() != null)
//					leavebal.setClBalance(leavebal.getClBalance() + checkleavevalue.getCL());
//				else if (checkleavevalue.getEL() != null)
//					leavebal.setElBalance(leavebal.getElBalance() + checkleavevalue.getEL());
//				else if (checkleavevalue.getSL() != null)
//					leavebal.setSlBalance(leavebal.getSlBalance() + checkleavevalue.getSL());
//
//				if (node.get("leaveType").asText().equals("cl")) {
//
//					if (leavebal.getClBalance() >= leaveCount) {
//
//						leavebalance = leavebal.getClBalance();
//						leavebalance = leavebalance - leaveCount;
//						System.out.println("new bal:" + leavebalance + " count:" + leaveCount);
//						leavebal.setClBalance(leavebalance);
//						leaveModel.setCL(leaveCount);
//					} else {
//						leaveCount = leaveCount - leavebal.getClBalance();
//						leaveModel.setLOP(leaveCount);
//						leaveModel.setCL(leavebal.getClBalance());
//						System.out.println("new bal:" + leavebalance + " count:" + leaveCount);
//						leavebal.setClBalance(0);
//					}
//				} else if (node.get("leaveType").asText().equals("sl")) {
//					if (leavebal.getSlBalance() >= leaveCount) {
//						leavebalance = leavebal.getSlBalance();
//						leavebalance = leavebalance - leaveCount;
//						System.out.println("new bal:" + leavebalance + " count:" + leaveCount);
//						leavebal.setSlBalance(leavebalance);
//						leaveModel.setSL(leaveCount);
//					} else {
//						leaveCount = leaveCount - leavebal.getSlBalance();
//						leaveModel.setLOP(leaveCount);
//						leaveModel.setSL(leavebal.getSlBalance());
//						System.out.println("new bal:" + leavebalance + " count:" + leaveCount);
//						leavebal.setSlBalance(0);
//					}
//				} else if (node.get("leaveType").asText().equals("lop")) {
//
//					leaveModel.setLOP(leaveCount);
//				} else if (node.get("leaveType").asText().equals("el")) {
//					if (leavebal.getElBalance() >= leaveCount) {
//
//						leavebalance = leavebal.getElBalance();
//						leavebalance = leavebalance - leaveCount;
//						leavebal.setElBalance(leavebalance);
//						leaveModel.setEL(leaveCount);
//					} else {
//						leaveCount = leaveCount - leavebal.getElBalance();
//						leaveModel.setEL(leavebal.getElBalance());
//						leaveModel.setLOP(leaveCount);
//						leavebal.setElBalance(0);
//						System.out.println("new bal:" + leavebalance + " count:" + leaveCount);
//					}
//				}
//
//				attendanceService.setLeaveBalance(leavebal);
//
//				attendanceService.saveLeaveMarking(leaveModel);
//				jsonDataRes.put("status", "success");
//				jsonDataRes.put("message", "successfully saved.");
//				jsonDataRes.put("code", httpstatus.getStatus());
//
//			}
//
//		} catch (Exception e) {
//			jsonDataRes.put("status", "failure");
//			jsonDataRes.put("code", httpstatus.getStatus());
//			jsonDataRes.put("message", "failed. " + e);
//		}
//		return jsonDataRes;
//
//	}

//	for calculating balance leave
	@PostMapping("/getleaveBalance")
	public ObjectNode setLeaveBalance(@RequestBody JsonNode requestdata, HttpServletResponse httpstatus) {
		ObjectNode jsonDataRes = objectMapper.createObjectNode();
		try {

			Long userId = requestdata.get("userId").asLong();
			System.out.println("userID:" + userId);
			UserModel user = userservice.getUserDetailsById(userId);

			if (user != null) {
				LocalDate now = LocalDate.now();
				int year = now.getYear();
				LeaveBalanceModel lBalance = new LeaveBalanceModel();
				lBalance.setYear(year);
				lBalance.setUser(user);
				int month = now.getMonthValue();
				double currEL = requestdata.get("el").asDouble();
				double currSL = requestdata.get("sl").asDouble();
				double currCL = requestdata.get("cl").asDouble();

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
				System.out.println("user id" + lBalance.getUser().getUserId());
				System.out.println("quarter:" + lBalance.getQuarter() + "yera:" + lBalance.getYear());

				double clBalance = 0, elBalance = 0, slBalance = 0;
				if (lBalance.getQuarter() > 1) {
					ObjectNode balanceList = attendanceService.getLeavebalanceData(lBalance.getUser().getUserId(),
							lBalance.getQuarter() - 1, lBalance.getYear());
					clBalance = balanceList.get("CL").asDouble();
					elBalance = balanceList.get("SL").asDouble();
					slBalance = balanceList.get("EL").asDouble();
				}

				clBalance = clBalance + currCL;
				elBalance = elBalance + currEL;
				slBalance = slBalance + currSL;

				Boolean checkvalue = attendanceService.checkLeaveBalance(lBalance);
				if (checkvalue) {
					LeaveBalanceModel leavedata = attendanceService.getUserLeaveBalance(lBalance.getQuarter() - 1,
							lBalance.getYear(), lBalance.getUser().getUserId());
					System.out.println("leave id;" + leavedata.getLeavebalanceId());
					leavedata.setClBalance(clBalance);
					leavedata.setElBalance(elBalance);
					leavedata.setSlBalance(slBalance);
					leavedata.setQuarter(lBalance.getQuarter());
					leavedata.setYear(lBalance.getYear());
					attendanceService.setLeaveBalance(leavedata);
//						attendanceService.deleteBalance(lBalance);
					jsonDataRes.put("status", "success");
					jsonDataRes.put("message", "successfully saved.");
					jsonDataRes.put("code", httpstatus.getStatus());
				} else {
					jsonDataRes.put("status", "failure");
					jsonDataRes.put("code", httpstatus.getStatus());
					jsonDataRes.put("message", "duplicate entry");
				}

			} else {
				jsonDataRes.put("status", "failure");
				jsonDataRes.put("code", httpstatus.getStatus());
				jsonDataRes.put("message", "Not an active user");
			}

		} catch (Exception e) {
			jsonDataRes.put("status", "failure");
			jsonDataRes.put("code", httpstatus.getStatus());
			jsonDataRes.put("message", "failed. " + e);
		}
		return jsonDataRes;

	}

//	for cancelling leave
//	@PostMapping("/cancelLeaveMarking")
//	public ObjectNode setcancelLeaveMarking(@RequestBody JsonNode requestdata, HttpServletResponse httpstatus) {
//		ObjectNode jsonDataRes = objectMapper.createObjectNode();
//		try {
//
//			Long userId = requestdata.get("userId").asLong();
//			UserModel user = userservice.getUserDetailsById(userId);
//
//			LocalDate now = LocalDate.now();
//			LeaveModel leaveModel = new LeaveModel();
//			leaveModel.setLeaveId(requestdata.get("leaveId").asLong());
//			leaveModel.setUser(user);
//			System.out.println("leaveid:" + leaveModel.getLeaveId() + " userId:" + leaveModel.getUser().getUserId());
//			LeaveModel leaveDetails = attendanceService.getLeaveDetails(leaveModel.getLeaveId());
//			double clCount = 0, elCount = 0, slCount = 0;
//			if (leaveDetails.getCL() != null)
//				clCount = clCount + leaveDetails.getCL();
//			if (leaveDetails.getEL() != null)
//				elCount = elCount + leaveDetails.getEL();
//			if (leaveDetails.getSL() != null)
//				slCount = slCount + leaveDetails.getSL();
//			System.out.println("CL:" + clCount + "EL:" + elCount + "SL:" + slCount);
//			LeaveBalanceModel lBalance = new LeaveBalanceModel();
//
//			int year = now.getYear();
//			lBalance.setYear(year);
//			lBalance.setUser(leaveModel.getUser());
//			int month = now.getMonthValue();
//			System.out.println("month :" + month);
//			int quarterleave = 0;
//			if (month <= 3) {
//				quarterleave = 1;
//			} else if (month <= 6 && month > 3) {
//				quarterleave = 2;
//			} else if (month <= 9 && month > 6) {
//				quarterleave = 3;
//			} else if (month <= 12 && month > 9) {
//				quarterleave = 4;
//
//			}
//
//			lBalance.setQuarter(quarterleave);
//			System.out.println("quat:" + lBalance.getQuarter() + "yera:" + lBalance.getYear() + " :user"
//					+ lBalance.getUser().getUserId());
//			LeaveBalanceModel leavebal = attendanceService.getUserLeaveBalance(lBalance.getQuarter(),
//					lBalance.getYear(), lBalance.getUser().getUserId());
//			System.out.println("balId:" + leavebal.getLeavebalanceId() + " sl:" + leavebal.getSlBalance() + " el:"
//					+ leavebal.getElBalance() + " cl:" + leavebal.getClBalance());
//
//			leavebal.setClBalance(leavebal.getClBalance() + clCount);
//			leavebal.setElBalance(leavebal.getElBalance() + elCount);
//			leavebal.setSlBalance(leavebal.getSlBalance() + slCount);
//
//			attendanceService.setLeaveBalance(leavebal);
//
//			attendanceService.deleteLeaveMarking(leaveModel.getLeaveId());
//			jsonDataRes.put("status", "success");
//			jsonDataRes.put("message", "successfully deleted");
//			jsonDataRes.put("code", httpstatus.getStatus());
//
//		} catch (Exception e) {
//			jsonDataRes.put("status", "failure");
//			jsonDataRes.put("code", httpstatus.getStatus());
//			jsonDataRes.put("message", "failed. " + e);
//		}
//		return jsonDataRes;
//
//	}

}
