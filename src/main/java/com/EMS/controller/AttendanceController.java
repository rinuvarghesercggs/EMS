package com.EMS.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EMS.model.HolidayModel;
import com.EMS.model.LeaveModel;
import com.EMS.service.AttendanceService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@RequestMapping(value = "/attendance")
public class AttendanceController {
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private AttendanceService attendanceService;
	
	
	@GetMapping("/getHolidayList")
	public ObjectNode getHolidayList(HttpServletResponse httpstatus) {
		
		ArrayNode jsonArray = objectMapper.createArrayNode();
		ObjectNode jsonDataRes = objectMapper.createObjectNode();
		
		try {
			List<HolidayModel> holidayList = attendanceService.getHolidayList();
//			ArrayNode node = objectMapper.convertValue(attendanceService.getHolidayList(), ArrayNode.class);
			for(HolidayModel item : holidayList) {
				ObjectNode holidayNode = objectMapper.createObjectNode();
				holidayNode.put("holidayId", item.getHolidayId());
				holidayNode.put("date", item.getDate().toString());
				holidayNode.put("day", item.getDay());
				holidayNode.put("holidayName", item.getHolidayName());
				holidayNode.put("holidayType", item.getHolidayType());
				jsonArray.add(holidayNode);

			}
			jsonDataRes.set("data", jsonArray);
			jsonDataRes.put("status", "success");
			jsonDataRes.put("message", "success. ");
			jsonDataRes.put("code", httpstatus.getStatus());
			
		}catch (Exception e) {
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
		try {
			if (requestdata.get("userId") != null && requestdata.get("userId").asText() != "") {
				userId = requestdata.get("userId").asLong();
				System.out.println("userId : "+userId);

				if(userId != null) {
					
					List<LeaveModel> leaveModel = attendanceService.getUserLeaveList(userId);
					System.out.println("leave list size : "+leaveModel.size());
					
					for(LeaveModel leave : leaveModel) {
						ObjectNode leaveObject = objectMapper.createObjectNode();
						leaveObject.put("Leave Type",leave.getCL());
						leaveObject.put("Leave Description", leave.getLeaveReason());
						leaveObject.put("Start Date",leave.getLeaveFrom().toString());
						leaveObject.put("endDate", leave.getLeaveTo().toString());
						leaveObject.put("status", leave.getStatus());
						jsonArray.add(leaveObject);

					}
					
				}
			}
			
			
			jsonDataRes.set("data", jsonArray);
			jsonDataRes.put("status", "success");
			jsonDataRes.put("message", "success. ");
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
