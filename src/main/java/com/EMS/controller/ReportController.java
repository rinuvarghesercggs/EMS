package com.EMS.controller;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.EMS.dto.Taskdetails;
import com.EMS.model.AllocationModel;
import com.EMS.model.ExportProjectTaskReportModel;
import com.EMS.model.ProjectModel;
import com.EMS.model.ProjectReportModel;
import com.EMS.model.Task;
import com.EMS.model.Tasktrack;
import com.EMS.model.UserModel;
import com.EMS.service.ProjectAllocationService;
import com.EMS.service.ProjectExportService;
import com.EMS.service.ProjectService;
import com.EMS.service.ReportService;
import com.EMS.service.ReportServiceImpl;
import com.EMS.service.TasktrackService;
import com.EMS.service.TasktrackServiceImpl;
import com.EMS.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@RequestMapping(value = { "/report" })
public class ReportController {


	@Autowired
	ReportService reportService;
	@Autowired
	ReportServiceImpl reportServiceImpl;
	
	@Autowired
	TasktrackService taskTrackService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ProjectAllocationService projectAllocationService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	ProjectExportService projectExportService;
	

	@PostMapping("/getProjectReport")
	public JsonNode getProjectReport(@RequestBody Taskdetails requestdata) {
		
		ArrayNode projectReport = reportServiceImpl.getProjectReportDetails(requestdata.getProjectId(),requestdata.getFromDate(),requestdata.getToDate());
		
		ObjectNode dataNode = objectMapper.createObjectNode();
		dataNode.set("projectReport", projectReport);

		ObjectNode node = objectMapper.createObjectNode();
		node.put("status", "success");
		node.set("data", dataNode);
		return node;
	}
	@PostMapping("/getBenchProjectReport")
	public JsonNode getBenchProjectReport(@RequestBody Taskdetails requestdata) {

		ArrayNode benchProjectReport = null;
		if(requestdata.getuId()!=null) {
			benchProjectReport = reportServiceImpl.getBenchProjectReportDetails(requestdata.getuId(),requestdata.getFromDate(),requestdata.getToDate());
		}
		else {
			benchProjectReport = reportServiceImpl.getBenchProjectReportDetails(requestdata.getFromDate(),requestdata.getToDate());
		}
		ObjectNode dataNode = objectMapper.createObjectNode();
		dataNode.set("benchProjectReport", benchProjectReport);

		ObjectNode node = objectMapper.createObjectNode();
		node.put("status", "success");
		node.set("data", dataNode);
		return node;
	}
	

	
	
	
	
	
	
	
	@PostMapping("/getUserReport")
	public JSONObject getUserreport(@RequestBody JsonNode requestdata, HttpServletResponse httpstatus)
			throws ParseException {

		JSONObject jsonDataRes = new JSONObject();
		List<JSONObject> jsonDataRes1 = new ArrayList<>();
		List<JSONObject> jsonArray = new ArrayList<>();
		Long userId = null, projectId = null;
//		Long pageSize = 50L;
//		Long pageIndex = null;
		

		try {
			if (requestdata.get("userId") != null && requestdata.get("userId").asText() != "") {
				userId = requestdata.get("userId").asLong();
			}

			if (requestdata.get("projectId") != null && requestdata.get("projectId").asText() != "") {
				projectId = requestdata.get("projectId").asLong();
			}

//			if (requestdata.get("pageIndex") != null && requestdata.get("pageIndex").asText() != "") {
//				pageIndex = requestdata.get("pageIndex").asLong();
//			}
//			Long startingIndex = (pageSize*pageIndex)+1;
			String date1 = requestdata.get("startDate").asText();
			String date2 = requestdata.get("endDate").asText();

			SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = null, endDate = null;
			if (!date1.isEmpty()) {
				startDate = outputFormat.parse(date1);
			}
			if (!date2.isEmpty()) {
				endDate = outputFormat.parse(date2);
			}

			List<Object[]> userIdList = null;
			Long count = null;
			if (startDate != null && endDate != null && projectId == null && userId == null) {
				count = userService.getCount();
//				jsonDataRes.put("userCount", count);
//				userIdList = userService.getUserIdLists(pageSize,startingIndex);
				userIdList = userService.getUserIdLists();
				getUserDataForReport(userIdList, startDate, endDate, jsonDataRes, jsonDataRes1, jsonArray,projectId);

			}

			else if (startDate != null && endDate != null && projectId != null && userId == null) {
				count = projectAllocationService.getUserCount(projectId);
//				jsonDataRes.put("userCount", count);
//				userIdList = projectAllocationService.getUserIdByProject(projectId,pageSize,startingIndex);
				userIdList = projectAllocationService.getUserIdByProject(projectId);

				getUserDataForReport(userIdList, startDate, endDate, jsonDataRes, jsonDataRes1, jsonArray,projectId);
			}

			else if (startDate != null && endDate != null && projectId == null && userId != null) {
				List<Object[]> userList = null;
//				jsonDataRes.put("userCount", "1");
				Boolean isExist = taskTrackService.checkIsUserExists(userId);
				List<JSONObject> jsonDataRes11 = taskTrackService.getUserTaskDetails(userId, startDate, endDate,
						userList, jsonArray, jsonDataRes1, isExist,projectId);
			}

			else if (startDate != null && endDate != null && projectId != null && userId != null) {
				List<Object[]> userList = null;
//				jsonDataRes.put("userCount", "1");
				Boolean isExist = taskTrackService.checkExistanceOfUser(projectId, userId);
				List<JSONObject> jsonDataRes11 = taskTrackService.getUserTaskDetails(userId, startDate, endDate,
						userList, jsonArray, jsonDataRes1, isExist,projectId);

			}
			jsonDataRes.put("data", jsonDataRes1);
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

	private void getUserDataForReport(List<Object[]> userIdList, Date startDate, Date endDate, JSONObject jsonDataRes,
			List<JSONObject> jsonDataRes1, List<JSONObject> jsonArray,Long projectId) {

		for (Object userItem : userIdList) {

//			Long id = Long.valueOf(((BigInteger) userItem).longValue());;
			Long id = (Long) userItem;

			List<Object[]> userList = null;
			Boolean isExist = taskTrackService.checkIsUserExists(id);
			 List<JSONObject> jsonDataRes11 = taskTrackService.getUserTaskDetails(id, startDate, endDate, userList, jsonArray, jsonDataRes1, isExist,projectId);

		}
	}

	
	
	
	
	
	
	
	@PostMapping("/getUserTaskReport")
	public ObjectNode getUserTaskReport(@RequestBody JsonNode requestdata, HttpServletResponse httpstatus)
			throws ParseException {
		
		Long projectId = null;
		List<Object[]> userIdList = null;
		Long pageSize = 50L;
		Long pageIndex = null;
		ArrayNode jsonArray = objectMapper.createArrayNode();
		ObjectNode jsonDataRes = objectMapper.createObjectNode();

		try {

			if (requestdata.get("projectId") != null && requestdata.get("projectId").asText() != "") {
				projectId = requestdata.get("projectId").asLong();
			}

			String date1 = requestdata.get("startDate").asText();
			String date2 = requestdata.get("endDate").asText();

			SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = null, endDate = null;
			if (!date1.isEmpty()) {
				startDate = outputFormat.parse(date1);
			}
			if (!date2.isEmpty()) {
				endDate = outputFormat.parse(date2);
			}
			
//			if (requestdata.get("pageIndex") != null && requestdata.get("pageIndex").asText() != "") {
//				pageIndex = requestdata.get("pageIndex").asLong();
//			}
//			Long startingIndex = (pageSize*pageIndex)+1;
			
//			Long count = projectAllocationService.getUserCount(projectId);

//			userIdList = projectAllocationService.getUserIdByProject(projectId,pageSize,startingIndex);
			userIdList = projectAllocationService.getUserIdByProject(projectId);

			System.out.println("userIdList size : "+userIdList.size());
			
			List<Object[]> taskList = taskTrackService.getTaskList();
			System.out.println("taskList size : "+taskList.size());
			
			for(Object userItem : userIdList) {
				System.out.println("entered into for loop");
				String billable = null;
				Boolean isBillable = false;
				Long id = (Long) userItem;
				System.out.println("id : "+id);

				isBillable = projectAllocationService.getIsBillable(id,projectId);
				System.out.println("isBillable : "+isBillable);

				if(isBillable)
					billable = "YES";
				else
					billable = "NO";

				System.out.println("billable : "+billable);

				List<Object[]> userList = null;
				Boolean isExist = taskTrackService.checkIsUserExists(id);
				
				if(isExist) {
					userList = taskTrackService.getUserTaskList(id,startDate,endDate);
				}
				
				if(userList != null && userList.size() > 0) {
					for(Object[] listItem : userList) {
						ObjectNode taskItemObject = objectMapper.createObjectNode();

						String name = (String) listItem[2];
						Double totalHours = (Double) listItem[0];
						String taskName = (String) listItem[3];
						
						taskItemObject.put("User",name);
						taskItemObject.put("Billable",billable);
						taskItemObject.put("Hours",totalHours);
						taskItemObject.put("Task Type",taskName);
						jsonArray.add(taskItemObject);


					}
				}
				

			}


//			jsonDataRes.put("userCount", count);
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
	
	
	@PostMapping(value = "/exportProjctTaskRpt")
	public ResponseEntity exportProjctAllocationRpt(@RequestBody JSONObject requestData,HttpServletResponse response) {
		ObjectNode jsonData = objectMapper.createObjectNode();
		ObjectNode jsonDataRes = objectMapper.createObjectNode();
		Date fromDate=null,toDate=null;
		SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		try {
			if (!requestData.get("startDate").toString().isEmpty() && requestData.get("startDate").toString() != null) {
				String startDate = requestData.get("startDate").toString();
				if (!startDate.isEmpty()) {
					fromDate = outputFormat.parse(startDate);

				}
			}
			if (!requestData.get("endDate").toString().isEmpty() && requestData.get("endDate").toString() != null) {
				String endDate = requestData.get("endDate").toString();
				if (!endDate.isEmpty()) {
					toDate = outputFormat.parse(endDate);

				}
			}
			Long projectId = Long.parseLong(requestData.get("projectId").toString());
			List <ExportProjectTaskReportModel>exportData = reportService.getProjectTaskReportDetails(fromDate,toDate,projectId);
			projectExportService.exportProjectTaskReport(exportData,response);

		} catch (Exception e) {
			return new ResponseEntity( HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity( HttpStatus.OK);
	}
	
}
