package com.EMS.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

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
import com.EMS.model.ProjectModel;
import com.EMS.model.Task;
import com.EMS.model.Tasktrack;
import com.EMS.model.UserModel;
import com.EMS.service.ProjectService;
import com.EMS.service.TasktrackService;
import com.EMS.service.TasktrackServiceImpl;
import com.EMS.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@RequestMapping(value = { "/tasktrack" })
public class TasktrackController {

	@Autowired
	ProjectService projectService;
	@Autowired
	TasktrackService tasktrackService;
	@Autowired
	TasktrackServiceImpl tasktrackServiceImpl;
	@Autowired
	UserService userService;

	@Autowired
	private ObjectMapper objectMapper;

	@PostMapping(value = "/getTaskDetails")
	public JsonNode getByDate(@RequestBody Taskdetails requestdata) {
		List<Tasktrack> list = tasktrackService.getByDate(requestdata.getFromDate(), requestdata.getToDate(),requestdata.getuId());
		ObjectNode node = objectMapper.createObjectNode();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ObjectNode taskDetails = objectMapper.createObjectNode();
		for(Tasktrack obj:list) {
			if(taskDetails.get(obj.getDate().toString())!=null) {
				ObjectNode objectNode = objectMapper.createObjectNode();
				objectNode.put("Project",obj.getProject().getProjectName());
				objectNode.put("taskType",obj.getTask().getTaskName());
				objectNode.put("hours",obj.getHours());
				ArrayNode arrayNode = (ArrayNode) taskDetails.get(sdf.format(obj.getDate()));
				arrayNode.add(objectNode);
				taskDetails.set(obj.getDate().toString(),arrayNode);
			}else {
				ArrayNode arrayNode = objectMapper.createArrayNode();
				ObjectNode objectNode = objectMapper.createObjectNode();
				objectNode.put("Project",obj.getProject().getProjectName());
				objectNode.put("taskType",obj.getTask().getTaskName());
				objectNode.put("hours",obj.getHours());
				arrayNode.add(objectNode);
				taskDetails.set(sdf.format(obj.getDate()),arrayNode);
			}
		}

		ObjectNode dataNode = objectMapper.createObjectNode();
		dataNode.set("taskDetails",taskDetails);
		node.put("status", "success");
		node.set("data", dataNode);
		
		return node;
		
	}

	@GetMapping(value = "/getProjectTaskDatas")
	public JSONObject getprojectnameList() {

		List<Object[]> projectTitleList = projectService.getNameId();
		List<Object[]> taskTypesList = tasktrackService.getTaskList();
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
			returnData.put("status", "failure");
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
					Tasktrack newTask = new Tasktrack();
					Long projectId = jsonObject.getLong("projectId");
					if (projectId != null) {
						ProjectModel proj = projectService.findById(projectId);
						if (proj != null) {
							newTask.setProject(proj);
						}
					} else {
						saveFail = true;
					}
					if (!jsonObject.getString("date").isEmpty()) {
						String dateNew = jsonObject.getString("date");
						TimeZone zone = TimeZone.getTimeZone("MST");
						SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
						outputFormat.setTimeZone(zone);
						Date date1 = outputFormat.parse(dateNew);
						if (date1 != null) {
							newTask.setDate(date1);
						} else {
							saveFail = true;
						}
					} else {
						saveFail = true;
					}

					Long taskId = jsonObject.getLong("taskTypeId");
					if (taskId != null) {
						Task task = tasktrackService.getTaskById(taskId);
						newTask.setTask(task);
					} else {
						saveFail = true;
					}
					if (!jsonObject.getString("taskSummary").isEmpty()) {
						newTask.setDescription(jsonObject.getString("taskSummary"));
					} else {
						saveFail = true;
					}
					newTask.setHours(jsonObject.getDouble("hours"));
					newTask.setUser(user);
					if (!saveFail) {
						tasktrackService.saveTaskDetails(newTask);
					}
				}
				if (!saveFail) {
					jsonDataRes.put("status", "success");
				} else {
					jsonDataRes.put("status", "failure");
				}
			} else {
				jsonDataRes.put("status", "failure");
			}
		} catch (Exception e) {
			jsonDataRes.put("status", "failure");
			System.out.println("Exception " + e);
		}

		return jsonDataRes;

	}

	@GetMapping("/getTaskList")
	public ArrayNode getTasks() {
		ArrayNode node = objectMapper.convertValue(tasktrackService.getTasks(), ArrayNode.class);

		return node;
	}

	@PutMapping("/updateTaskById")
	public JsonNode updateTaskById(@RequestBody Tasktrack task, @RequestParam("taskId") int id) {
		ObjectNode node = objectMapper.createObjectNode();

		if (tasktrackServiceImpl.updateTaskById(task)) {
			node.put("status", HttpStatus.OK.value());
			node.put("message", "success");
		} else {
			node.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
			node.put("message", "failed to update");
		}

		return node;
	}

	@DeleteMapping("/deleteTaskById")
	public JsonNode deleteTaskById(@RequestParam("taskId") int id) {
		ObjectNode node = objectMapper.createObjectNode();

		if (tasktrackServiceImpl.deleteTaskById(id)) {
			node.put("status", HttpStatus.OK.value());
			node.put("message", "success");
		} else {
			node.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
			node.put("message", "failed to update");
		}

		return node;
	}

	@PostMapping("/createTask")
	public JsonNode createTask(@RequestBody Tasktrack task) {
		ObjectNode node = objectMapper.createObjectNode();

		if (tasktrackServiceImpl.createTask(task)) {
			node.put("status", HttpStatus.OK.value());
			node.put("message", "success");
		} else {
			node.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
			node.put("message", "failed to update");
		}

		return node;
	}

	@GetMapping("/getProjectNames")
	public JsonNode getProjectNames() {
		return objectMapper.convertValue(tasktrackServiceImpl.getProjectNames(), ArrayNode.class);

	}

	@GetMapping("/getTaskCategories")
	public JsonNode getTaskCategory() {
		return objectMapper.convertValue(tasktrackServiceImpl.getTaskCategory(), ArrayNode.class);
	}
}
