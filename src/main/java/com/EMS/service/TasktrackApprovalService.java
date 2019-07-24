package com.EMS.service;

import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.EMS.model.AllocationModel;
import com.EMS.model.ProjectModel;
import com.EMS.model.Task;
import com.EMS.model.TaskTrackApproval;
import com.EMS.model.Tasktrack;
import com.fasterxml.jackson.databind.node.ArrayNode;

public interface TasktrackApprovalService {

	Boolean checkIsUserExists(Long id);
	
	List<JSONObject> getTimeTrackUserTaskDetails(Long id, Date startDate, Date endDate, List<Object[]> userList,
			List<JSONObject> loggedJsonArray,List<JSONObject> billableJsonArray,List<JSONObject> timeTrackJSONData, Boolean isExist,Long projectId);
	List<JSONObject> getTimeTrackUserTaskDetailsByProject(Long id, Date startDate, Date endDate, List<Object[]> userList,
												 List<JSONObject> loggedJsonArray,List<JSONObject> billableJsonArray,List<JSONObject> nonBillableJsonArray,List<JSONObject> timeTrackJSONData, Boolean isExist,Long projectId);
	List<JSONObject> getTimeTrackUserProjectTaskDetails(Long projectId,String projectName, Date startDate, Date endDate, List<Object[]> projectList,
												 List<JSONObject> loggedJsonArray,List<JSONObject> billableJsonArray,List<JSONObject> nonBillableJsonArray,List<JSONObject> timeTrackJSONData, Boolean isExist,Long userId);
	
	JSONObject getApprovedUserTaskDetails(Long id, Date startDate, Date endDate, List<TaskTrackApproval> userList,
			List<JSONObject> jsonArray, List<JSONObject> jsonDataRes1, Boolean isExist,Long projectId);
	
	TaskTrackApproval findById(Long billableId);
	
	public TaskTrackApproval updateData(TaskTrackApproval taskTrackApproval);
	
	public void save(TaskTrackApproval taskTrackApproval);
	
}

