package com.EMS.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.EMS.model.ProjectModel;
import com.EMS.model.ProjectReportModel;
import com.EMS.model.Task;
import com.EMS.model.Tasktrack;
import com.EMS.model.UserModel;
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
	private ObjectMapper objectMapper;

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
}
