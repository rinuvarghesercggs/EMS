package com.EMS.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EMS.model.HolidayModel;
import com.EMS.service.AttendanceService;
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
	
	
	@GetMapping("/getUserTaskReport")
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
	

	
}
