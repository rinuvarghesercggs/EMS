package com.EMS.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.EMS.dto.Taskdetails;
import com.EMS.model.ProjectModel;
import com.EMS.model.TaskModel;
import com.EMS.model.UserModel;
import com.EMS.service.ProjectService;
import com.EMS.service.TaskService;
import com.EMS.service.UserService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javassist.expr.NewArray;

@RestController
@RequestMapping(value = { "/timetrack" })
public class TasktrackController {

	@Autowired
	ProjectService projectService;
	@Autowired
	TaskService taskService;
	@Autowired
	UserService userService;

	@PostMapping(value = "/getTaskdetails")
	public JSONObject getByDate(@RequestBody Taskdetails requestdata) {
		List<TaskModel> tracklist = null;
		tracklist = taskService.getByDate(requestdata.getTaskDate(), requestdata.getuId());
		JSONObject jsonData = new JSONObject();
		JSONObject jsonDataRes = new JSONObject();
		List<JSONObject> jsonArray = new ArrayList<>();
		try {
			if (!tracklist.isEmpty() && tracklist.size() > 0) {
				for (TaskModel item : tracklist) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("id", item.getId());
					jsonObject.put("project", item.getproject().getprojectName());
					jsonObject.put("taskType", item.getTaskName());
					jsonObject.put("taskSummary", item.getDescription());
					jsonObject.put("hours", item.getHours());
					jsonArray.add(jsonObject);
				}
				jsonData.put("taskDetails", jsonArray);
				jsonDataRes.put("status", "Success");
			} else {
				jsonDataRes.put("status", "Date Not Found");
			}
		} catch (Exception e) {
			jsonDataRes.put("status", "Failure");
			jsonData.put("taskDetails", jsonArray);
		}
		jsonDataRes.put("data", jsonData);

		return jsonDataRes;
	}

	@GetMapping(value = "/getprojectTaskDatas")
	public JSONObject getprojectnameList() {
		List<Object[]> projectTitleList = projectService.getNameId();
		List<Object[]> taskTypesList = taskService.getTaskList();
		JSONObject returnData = new JSONObject();
		JSONObject projectTaskDatas = new JSONObject();
		List<JSONObject> projectIdTitleList = new ArrayList<>();
		List<JSONObject> taskIdTitleList = new ArrayList<>();

		try {
			if (!projectTitleList.isEmpty() && !taskTypesList.isEmpty() && projectTitleList.size() > 0
					&& taskTypesList.size() > 0) {

				for (Object[] itemNew : projectTitleList) {
					JSONObject jsonObjectNew = new JSONObject();
					jsonObjectNew.put("id", itemNew[0]);
					jsonObjectNew.put("value", itemNew[1]);
					projectIdTitleList.add(jsonObjectNew);
				}
				for (Object[] itemNew : taskTypesList) {
					JSONObject jsonObjectNew = new JSONObject();
					jsonObjectNew.put("id", itemNew[0]);
					jsonObjectNew.put("value", itemNew[1]);
					taskIdTitleList.add(jsonObjectNew);
				}
				projectTaskDatas.put("taskTypes", taskIdTitleList);
				projectTaskDatas.put("projectTitle", projectIdTitleList);
				returnData.put("status", "success");
				returnData.put("data", projectTaskDatas);
			}
		} catch (Exception e) {
			returnData.put("status", "Failure");
			returnData.put("data", projectTaskDatas);
		}
		return returnData;
	}

	@PostMapping(value = "/addTask", headers = "Accept=application/json")
	public JSONObject updateData(@RequestBody JSONObject taskData) throws JSONException, ParseException {
		JSONObject jsonDataRes = new JSONObject();
		String taskListString = taskData.get("addTask").toString();
		String userId = taskData.get("uId").toString();
		Long uId = Long.parseLong(userId);
		org.json.JSONArray newArray = new org.json.JSONArray(taskListString);
		try {
			if (!userId.isEmpty() && uId != null) {
				UserModel user = userService.getUserDetailsById(uId);
				int count = newArray.length();
				Boolean saveFail = false;
				for (int i = 0; i < count; i++) {
					org.json.JSONObject jsonObject = newArray.getJSONObject(i);
					TaskModel newTask = new TaskModel();
					if (!jsonObject.getString("project").isEmpty()) {
						Long projectId = projectService.getProjectId(jsonObject.getString("project"));
						if (projectId != null) {
							ProjectModel proj = projectService.findById(projectId);
							if (proj != null) {
								newTask.setproject(proj);
							}
						} else {
							saveFail = true;
						}
					} else {
						saveFail = true;
					}
					if (!jsonObject.getString("date").isEmpty()) {
						String dateNew = jsonObject.getString("date");
						Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(dateNew);
						if (date1 != null) {
							newTask.setDate(date1);
						} else {
							saveFail = true;
						}
					} else {
						saveFail = true;
					}
					if (!jsonObject.getString("taskType").isEmpty()) {
						newTask.setTaskName(jsonObject.getString("taskType"));
					} else {
						saveFail = true;
					}
					if (!jsonObject.getString("taskSummary").isEmpty()) {
						newTask.setDescription(jsonObject.getString("taskSummary"));
					} else {
						saveFail = true;
					}
					newTask.setHours(jsonObject.getInt("hours"));
					newTask.setuser(user);
					if (!saveFail) {
						taskService.saveTaskDetails(newTask);
					}
				}
				if (!saveFail) {
					jsonDataRes.put("status", "Success");
				} else {
					jsonDataRes.put("status", "Failure");
				}
			} else {
				jsonDataRes.put("status", "Failure");
			}
		} catch (Exception e) {
			jsonDataRes.put("status", "Failure");
			System.out.println("Exception " + e);

		}

		return jsonDataRes;
	}

//	@GetMapping(value = "find/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Timetrack> getRecordById(@PathVariable("id") long id) {
//		System.out.println("Fetching Record with id " + id);
//		Timetrack timetrack = timetrackService.findById(id);
//		timetrack.setDate(new Date());
//		Timetrack timetracknew = timetrackService.update(timetrack);
//		System.out.println("Timetrack : " + timetrack);
//		if (timetrack == null) {
//			return new ResponseEntity<Timetrack>(HttpStatus.NOT_FOUND);
//		}
//		return new ResponseEntity<Timetrack>(timetracknew, HttpStatus.OK);
//	}
//
//	@GetMapping(value = "/get")
//	public List<Timetrack> getAllTimetrackRecords() {
//		List<Timetrack> tracklist = timetrackService.getAllRecord();
//		return tracklist;
//
//	}
//	

//	 @PostMapping(value="/create",headers="Accept=application/json")
//	 public ResponseEntity<Void> createNewRecord(@RequestBody Timetrack timetrack, UriComponentsBuilder ucBuilder){
//	     System.out.println("Creating entry "+timetrack.getId());
//	     timetrackService.createNewRecord(timetrack);
//	     HttpHeaders headers = new HttpHeaders();
//	     headers.setLocation(ucBuilder.path("/timetrack/get").buildAndExpand(timetrack.getId()).toUri());
//	     return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
//	 }

//	 @PutMapping(value="/update", headers="Accept=application/json")
//		public ResponseEntity<String> updateRecord(@RequestBody Timetrack timetrack)
//		{
//			System.out.println("Updating");
//		Timetrack timetrackOld = timetrackService.findById(timetrack.getId());
//		if (timetrackOld ==null) {
//			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
//		}
//		timetrackService.update(timetrack);
//		return new ResponseEntity<String>(HttpStatus.OK);
//		}
//	 
//	 
//	 @DeleteMapping(value="delete/{id}", headers ="Accept=application/json")
//		public ResponseEntity<Timetrack> deleteUser(@PathVariable("id") long id){
//			Timetrack timetrack = timetrackService.findById(id);
//			if (timetrack == null) {
//				return new ResponseEntity<Timetrack>(HttpStatus.NOT_FOUND);
//			}
//			timetrackService.deleteRecordById(id);
//			return new ResponseEntity<Timetrack>(HttpStatus.NO_CONTENT);
//		}

}
