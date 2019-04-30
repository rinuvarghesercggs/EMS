package com.EMS.controller;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EMS.model.HolidayModel;
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
			for (HolidayModel item : holidayList) {
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
						leaveObject.put("endDate", leaveTo);
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
}
