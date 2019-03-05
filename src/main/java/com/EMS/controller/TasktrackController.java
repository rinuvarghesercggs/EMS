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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.EMS.dto.Taskdetails;
import com.EMS.model.ProjectModel;
import com.EMS.model.Task;
import com.EMS.model.Tasktrack;
import com.EMS.model.UserModel;
import com.EMS.service.ProjectService;
import com.EMS.service.TasktrackService;
import com.EMS.service.UserService;

@RestController
@RequestMapping(value = { "/tasktrack" })
public class TasktrackController {

	@Autowired
	ProjectService projectService;
	@Autowired
	TasktrackService tasktrackService;
	@Autowired
	UserService userService;

	@PostMapping(value = "/getTaskDetails")
	public JSONObject getByDate(@RequestBody Taskdetails requestdata) {
		List<Tasktrack> tracklist = null;
		tracklist = tasktrackService.getByDate(requestdata.getTaskDate(), requestdata.getuId());
		JSONObject jsonData = new JSONObject();
		JSONObject jsonDataRes = new JSONObject();
		List<JSONObject> jsonArray = new ArrayList<>();
		try {
			if (!tracklist.isEmpty() && tracklist.size() > 0) {
				for (Tasktrack item : tracklist) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("id", item.getId());
					if(item.getProject() !=null)
					jsonObject.put("project", item.getProject().getprojectName());
					if(item.getTask() != null)
					jsonObject.put("taskType", item.getTask().getTaskName());
					jsonObject.put("taskSummary", item.getDescription());
					jsonObject.put("hours", item.getHours());
					jsonArray.add(jsonObject);
				}
				jsonData.put("taskDetails", jsonArray);
				jsonDataRes.put("status", "success");
			} else {
				jsonDataRes.put("status", "Date Not Found");
			}
		} catch (Exception e) {
			jsonDataRes.put("status", "failure");
			jsonData.put("taskDetails", jsonArray);
		}
		jsonDataRes.put("data", jsonData);

		return jsonDataRes;
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
					newTask.setHours(jsonObject.getInt("hours"));
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
}
