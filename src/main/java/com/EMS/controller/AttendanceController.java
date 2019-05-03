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
				String holidayId = item[0].toString();
				String date = item[1].toString();
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
		Long userId = null;

		LocalDate now = LocalDate.now();
		LocalDate firstDayOfYear = now.with(TemporalAdjusters.firstDayOfYear());
		LocalDate lastDayOfYear = now.with(TemporalAdjusters.lastDayOfYear());

		try {
			if (requestdata.get("userId") != null && requestdata.get("userId").asText() != "") {
				userId = requestdata.get("userId").asLong();
				System.out.println("userId : " + userId);

				if (userId != null) {
					List<Object[]> leaveModel = attendanceService.getUserLeaveList(userId, firstDayOfYear,
							lastDayOfYear);

					
//					List<Object[]> leaveModel = attendanceService.getUserLeaveList(userId);
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
						leaveObject.put("status", status);
						jsonArray.add(leaveObject);

					}

				}
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
	public JsonNode getweeklyLeeveList(@RequestBody JsonNode requestdata,HttpServletResponse response) {
		ObjectNode responseData=objectMapper.createObjectNode();
		
		String startDate=requestdata.get("startdate").asText();
		String endDate=requestdata.get("endDate").asText();
		
		
		try {
			
			LocalDate start = LocalDate.parse(startDate);
			LocalDate end = LocalDate.parse(endDate);
			ObjectNode datewise=objectMapper.createObjectNode();
			
			while (!start.isAfter(end)) {

			    System.out.println("dates :"+start);
			    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
			    String stgdate=String.valueOf(start);
			    Date date1 =formatter.parse(stgdate);	
			  
			    List<LeaveModel> leavelist=attendanceService.getWeeklyLeavelist(date1);
				ArrayNode leaverecord=objectMapper.createArrayNode();	
					
					for(LeaveModel leave:leavelist) {
					
						ObjectNode node=objectMapper.createObjectNode();
						System.out.println("userid :"+leave.getUser().getUserId());
						String userDetail=userservice.getUserName(leave.getUser().getUserId());
						String username=userDetail.replace(","," ");
						System.out.println("username:"+username);
						
						node.put("username", username);
						node.put("startDate", leave.getLeaveFrom().toString());
//						node.put("endDate", leave.getLeaveTo().toString());
						if(leave.getCL()!=null) {
							node.put("leaveType", "CL");
							node.put("leaveCount", leave.getCL());
						}
						if(leave.getEL()!=null) {
							node.put("leaveType", "EL");
//							node.put("leaveCount", leave.getEL());
						}
						if(leave.getLOP()!=null) {
							node.put("leaveType", "LOP");
//							node.put("leaveCount", leave.getLOP());
						}	
						
						if(leave.getSL()!=null) {
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
			
		}catch(Exception e) {
			
			responseData.put("status", "Failed");
			responseData.put("code", response.getStatus());
			responseData.put("message", "Exception:"+e);
			e.printStackTrace();
			e.printStackTrace();
		}
		
		return responseData;
	}
	
	@PostMapping("/getYearlyLeaveList")
	public JsonNode getYearlyLeeveList(@RequestBody JsonNode requestdata,HttpServletResponse response) {
		ObjectNode responseData=objectMapper.createObjectNode();
		
		String startDate=requestdata.get("startdate").asText();
		String endDate=requestdata.get("endDate").asText();
		
		try {

			    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);	    
			    Date startDate1=formatter.parse(startDate);
			    Date endDate1=formatter.parse(endDate);
			    
			    ArrayNode userleaverecord=objectMapper.createArrayNode();
			    List<UserModel> userlist=userservice.getAllUsers();
			    if(!userlist.isEmpty()) {
			    	System.out.println("array size :"+userlist.size());
			    	for(UserModel user:userlist) {
			    		
			    		ObjectNode node=objectMapper.createObjectNode();
			    		node.put("employeeID", user.getEmpId());
			    		node.put("employeeName", user.getFirstName()+" "+user.getLastName());
			    		
						System.out.println("userid :"+user.getUserId());
						List<LeaveModel> leavelist=attendanceService.getYearlyLeavelist(user.getUserId(),startDate1,endDate1);
				    	int clCount=0,elCount=0,lopCount=0,slCount=0;
				    	
				    	for(LeaveModel leave:leavelist) {
										    		
							if(leave.getCL()!=null)
								clCount++;
							
							if(leave.getEL()!=null)
								elCount++;
							
							if(leave.getLOP()!=null) 
								lopCount++;
						
							if(leave.getSL()!=null)
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

			
		}catch(Exception e) {
			responseData.put("status", "Failed");
			responseData.put("code", response.getStatus());
			responseData.put("message", "Exception:"+e);
			e.printStackTrace();
		}
		
		return responseData;
	}
	
	
	
	@PostMapping("/leaveMarking")
	public ObjectNode setLeaveMarking(@RequestBody JsonNode requestdata,HttpServletResponse httpstatus) {
		ObjectNode jsonDataRes = objectMapper.createObjectNode();
		try {


				TimeZone zone = TimeZone.getTimeZone("MST");
				SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
				outputFormat.setTimeZone(zone);

				Long userId = requestdata.get("userId").asLong();
				UserModel user = userservice.getUserDetailsById(userId);

				LocalDate now = LocalDate.now();

				ArrayNode leaveNode=(ArrayNode) requestdata.get("leaveList");
				System.out.println("leaveNode size : "+leaveNode.size());
				for (JsonNode node:leaveNode) {
					LeaveModel leaveModel = new LeaveModel();
					leaveModel.setUser(user);
					leaveModel.setStatus(requestdata.get("status").asText());
					leaveModel.setAppliedDate(outputFormat.parse(now.toString()));
					leaveModel.setLeaveReason(node.get("reason").asText());
					String date = node.get("date").asText();
					if (!date.isEmpty()) {
						leaveModel.setLeaveFrom(outputFormat.parse(date));
						leaveModel.setLeaveTo(outputFormat.parse(date));
					}
					System.out.println("type : "+node.get("leaveType").asText());
					if(node.get("leaveType").asText().equals("cl"))
						leaveModel.setCL(node.get("count").asDouble());
					else if(node.get("leaveType").asText().equals("sl"))
						leaveModel.setSL(node.get("count").asDouble());
					else if(node.get("leaveType").asText().equals("lop"))
						leaveModel.setLOP(node.get("count").asDouble());
					else if(node.get("leaveType").asText().equals("el"))
						leaveModel.setEL(node.get("count").asDouble());

					attendanceService.saveLeaveMarking(leaveModel);

				}
				
				
				jsonDataRes.put("status", "success");
				jsonDataRes.put("message", "successfully saved.");
				jsonDataRes.put("code", httpstatus.getStatus());
		}
		catch (Exception e) {
			jsonDataRes.put("status", "failure");
			jsonDataRes.put("code", httpstatus.getStatus());
			jsonDataRes.put("message", "failed. " + e);
		}
		return jsonDataRes;
		
	}
	
}
