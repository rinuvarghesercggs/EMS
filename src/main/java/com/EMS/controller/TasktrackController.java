package com.EMS.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
import com.EMS.model.AllocationModel;
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
		/*
		 * { "status": "success", "data": { "taskDetails": { "2019-03-11": [ {
		 * "Project": "SAMPLE", "taskType": "SAMPLE", "hours": 3 } ] } } }
		 */
		List<Tasktrack> list = tasktrackService.getByDate(requestdata.getFromDate(), requestdata.getToDate(),
				requestdata.getuId());
		ObjectNode responseNode = objectMapper.createObjectNode();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ObjectNode taskDetails = objectMapper.createObjectNode();
		for (Tasktrack obj : list) {
			if (taskDetails.get(sdf.format(obj.getDate())) != null) {
				ObjectNode objectNode = objectMapper.createObjectNode();
				objectNode.put("taskId", obj.getId());
				objectNode.put("Project",
						(obj.getProject().getProjectName() != null) ? obj.getProject().getProjectName() : null);
				objectNode.put("taskType", (obj.getTask().getTaskName() != null) ? obj.getTask().getTaskName() : null);
				objectNode.put("taskSummary",
						(obj.getDescription() != null) ? obj.getDescription() : obj.getDescription());
				objectNode.put("hours", (obj.getHours() != null) ? obj.getHours() : null);
				ArrayNode arrayNode = (ArrayNode) taskDetails.get(sdf.format(obj.getDate()));
				arrayNode.add(objectNode);
				taskDetails.set(sdf.format(obj.getDate()), arrayNode);
			} else {
				ArrayNode arrayNode = objectMapper.createArrayNode();
				if (obj.getId() != 0) {
					ObjectNode objectNode = objectMapper.createObjectNode();
					objectNode.put("taskId", obj.getId());
					objectNode.put("Project",
							(obj.getProject().getProjectName() != null) ? obj.getProject().getProjectName() : null);
					objectNode.put("taskType",
							(obj.getTask().getTaskName() != null) ? obj.getTask().getTaskName() : null);
					objectNode.put("taskSummary",
							(obj.getDescription() != null) ? obj.getDescription() : obj.getDescription());
					objectNode.put("hours", (obj.getHours() != null) ? obj.getHours() : null);
					arrayNode.add(objectNode);
				}

				taskDetails.set(sdf.format(obj.getDate()), arrayNode);
			}
		}

		ObjectNode dataNode = objectMapper.createObjectNode();
		dataNode.set("taskDetails", taskDetails);
		responseNode.put("status", "success");
		responseNode.set("data", dataNode);

		return responseNode;

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

	@GetMapping("/getTaskList")
	public ArrayNode getTasks() {
		ArrayNode node = objectMapper.convertValue(tasktrackService.getTasks(), ArrayNode.class);

		return node;
	}

	@PutMapping("/updateTaskById")
	public JsonNode updateTaskById(@RequestBody ObjectNode objectNode) {
		ObjectNode node = objectMapper.createObjectNode();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			sdf.setTimeZone(TimeZone.getDefault());
			ProjectModel projectModel = tasktrackServiceImpl.getProjectModelById(objectNode.get("projectId").asLong());
			Task taskCategory = tasktrackService.getTaskById(objectNode.get("taskTypeId").asLong());
			Tasktrack tasktrack = new Tasktrack();
			tasktrack.setTask(taskCategory);
			tasktrack.setProject(projectModel);
			tasktrack.setDescription(objectNode.get("taskSummary").asText());
			tasktrack.setId(objectNode.get("taskId").asLong());
			tasktrack.setHours(objectNode.get("hours").asDouble());
			tasktrack.setDate(sdf.parse(objectNode.get("date").asText()));
			if (tasktrackServiceImpl.updateTaskById(tasktrack)) {
				node.put("status", "success");
			} else {
				node.put("status", "failure");
			}
		} catch (Exception e) {
			e.printStackTrace();
			node.put("status", "failure");
		}

		return node;
	}

	@DeleteMapping("/deleteTaskById")
	public JsonNode deleteTaskById(@RequestParam("taskId") int id) {
		ObjectNode node = objectMapper.createObjectNode();

		if (tasktrackServiceImpl.deleteTaskById(id)) {
			node.put("status", "success");
		} else {
			node.put("status", "failure");
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
	public JsonNode getProjectNames(@RequestParam("uId") int uId) {
		ArrayNode projectTitle = objectMapper.createArrayNode();
		for (AllocationModel alloc : tasktrackServiceImpl.getProjectNames(uId)) {

			ObjectNode node = objectMapper.createObjectNode();
			node.put("id", alloc.getproject().getProjectId());
			node.put("value", alloc.getproject().getProjectName());
			projectTitle.add(node);
		}

		ObjectNode dataNode = objectMapper.createObjectNode();
		dataNode.set("projectTitle", projectTitle);

		ObjectNode node = objectMapper.createObjectNode();
		node.put("status", "success");
		node.set("data", dataNode);
		return node;

	}

	@GetMapping("/getTaskCategories")
	public JsonNode getTaskCategories(@RequestParam("uId") int uId) {
		ArrayNode taskTypes = objectMapper.createArrayNode();
		for (Task category : tasktrackServiceImpl.getTaskCategory(uId)) {
			ObjectNode node = objectMapper.createObjectNode();
			node.put("id", category.getId());
			node.put("value", category.getTaskName());
			taskTypes.add(node);
		}
		ObjectNode dataNode = objectMapper.createObjectNode();
		dataNode.set("taskTypes", taskTypes);

		ObjectNode node = objectMapper.createObjectNode();
		node.put("status", "success");
		node.set("data", dataNode);
		return node;
	}

	@PostMapping(value = "/addTask", headers = "Accept=application/json")
	public JsonNode updateData(@RequestBody JsonNode taskData) throws JSONException, ParseException {
		ObjectNode dataResponse = objectMapper.createObjectNode();

		try {
			Long uId = taskData.get("uId").asLong();
//			Boolean saveFailure = false;
			int responseflag=0;
			
			if (!uId.equals(null)) {

				ArrayNode arrayNode = (ArrayNode) taskData.get("addTask");
				UserModel user = userService.getUserDetailsById(uId);
				arrayNode.forEach((jsonNode) -> {
					
					Tasktrack tasktrack = new Tasktrack();
					Long taskId = jsonNode.get("taskTypeId").asLong();
					if (taskId != null) {
						Task task = tasktrackService.getTaskById(taskId);
						tasktrack.setTask(task);
					}
//					else 
//						saveFailure=true;
					tasktrack.setHours(jsonNode.get("hours").asDouble());

					Long projectId = jsonNode.get("projectId").asLong();
					if (projectId != 0L) {
						ProjectModel proj = projectService.findById(projectId);
						if (proj != null)
							tasktrack.setProject(proj);

					} 
//					else
//						saveFail = true;

					if (!jsonNode.get("taskSummary").equals(null))
						tasktrack.setDescription(jsonNode.get("taskSummary").asText());
//					else
//						saveFail = true;

					if (!user.equals(null))
						tasktrack.setUser(user);
//					else
//						saveFail = true;

					if (!jsonNode.get("date").equals(null)) {
						String dateNew = jsonNode.get("date").asText();
						TimeZone zone = TimeZone.getTimeZone("MST");
						SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
						outputFormat.setTimeZone(zone);
						Date date1;
						try {
							date1 = outputFormat.parse(dateNew);
							if (date1 != null)
								tasktrack.setDate(date1);
//							else
//								saveFail = true;

						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					} 
//					else
//						saveFail = true;
					
//					if (!saveFail) {
						tasktrackService.saveTaskDetails(tasktrack);
						dataResponse.put("status", "success");
//					}
					
				});

			} else {
				dataResponse.put("status", "user id is missing");
			}

		} catch (Exception e) {
			dataResponse.put("status", "failure");
			System.out.println("Exception " + e);
		}

		return dataResponse;
	}
}
