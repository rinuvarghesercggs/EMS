package com.EMS.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.EMS.model.AllocationModel;
import com.EMS.model.DepartmentModel;
import com.EMS.model.ProjectModel;
import com.EMS.model.UserModel;
import com.EMS.service.ProjectService;
import com.EMS.service.ProjectAllocationService;
import com.EMS.service.UserService;

@RestController
@RequestMapping(value = "/project")
public class ProjectAllocationController {

	@Autowired
	ProjectAllocationService projectAllocation;

	@Autowired
	ProjectService projectService;
	
	@Autowired
	UserService userService;
	
	

	// To get user, department and project list

	@GetMapping(value = "/getPreResourceData")
	public JSONObject getUsernameList(HttpServletResponse httpstatus) {
		JSONObject jsonData = new JSONObject();
		JSONObject jsonDataRes = new JSONObject();
		try {
			//Method invocation for getting user list
			List<UserModel> userList = projectAllocation.getUserList();
			//Method invocation for getting department list
			List<DepartmentModel> departmentList = projectAllocation.getDepartmentList();
			//Method invocation for getting project list
			List<ProjectModel> projectList = projectService.getProjectList();

			List<JSONObject> jsonArray = new ArrayList<>();
			List<JSONObject> jsonProjectArray = new ArrayList<>();
			List<JSONObject> jsonDepartmentArray = new ArrayList<>();

			// Add user list to json object
			if (!(userList).isEmpty() && userList.size() > 0) {
				for (UserModel user : userList) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("userId", user.getUserId());
					jsonObject.put("firstName", user.getFirstName());
					jsonObject.put("lastName", user.getLastName());
					jsonObject.put("role", user.getrole().getroleId());
					jsonObject.put("department", user.getdepartment());
					jsonArray.add(jsonObject);
				}
				jsonData.put("userList", jsonArray);
			}

			// Add project list to json object
			if (!(projectList).isEmpty() && projectList.size() > 0) {
				for (ProjectModel project : projectList) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("projectId", project.getProjectId());
					jsonObject.put("projectName", project.getProjectName());
					jsonProjectArray.add(jsonObject);
				}
				jsonData.put("projectList", jsonProjectArray);
			}
			
			// Add department list to json object
			if (!(departmentList).isEmpty() && departmentList.size() > 0) {
				for (DepartmentModel department : departmentList) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("departmentId", department.getDepartmentId());
					jsonObject.put("departmentName", department.getdepartmentName());
					jsonDepartmentArray.add(jsonObject);
				}
				jsonData.put("departmentList", jsonDepartmentArray);
			}
			
			
			jsonDataRes.put("data", jsonData);
			jsonDataRes.put("status", "success");
			jsonDataRes.put("code", httpstatus.getStatus());
			jsonDataRes.put("message", "success");
		} catch (Exception e) {
			jsonDataRes.put("status", "failure");
			jsonDataRes.put("code", httpstatus.getStatus());
			jsonDataRes.put("message", "failed. " + e);
		}
		return jsonDataRes;
	}
	

	
	// To update resource allocation data

	@PutMapping(value = "/editAllocation")
	public JSONObject updateData(@RequestBody JSONObject requestdata, HttpServletResponse httpstatus) {

		JSONObject jsonDataRes = new JSONObject();
		
		String id =null,allocatedVal = null,isBillable = null;
		
		try {
			if(!requestdata.get("id").toString().isEmpty() && requestdata.get("id").toString() != null)
				id = requestdata.get("id").toString();
			if(!requestdata.get("allocatedPerce").toString().isEmpty() && requestdata.get("allocatedPerce").toString() != null)
				allocatedVal = requestdata.get("allocatedPerce").toString();
			if(!requestdata.get("isBillable").toString().isEmpty() && requestdata.get("isBillable").toString() != null)
				isBillable = requestdata.get("isBillable").toString();


			//Method invocation for getting allocation details
			AllocationModel allocationModel = projectAllocation.findDataById(Long.parseLong(id));
			if (allocationModel != null) {
				allocationModel.setAllocatedPerce(Double.parseDouble(allocatedVal));
                allocationModel.setIsBillable(Boolean.parseBoolean(isBillable));
    			System.out.println("isBillable : " + Boolean.parseBoolean(isBillable));

                //Updating allcation details
				projectAllocation.updateData(allocationModel);
				jsonDataRes.put("status", "success");
				jsonDataRes.put("code", httpstatus.getStatus());
				jsonDataRes.put("message", "updated successfully");
			}
		} catch (Exception e) {
			jsonDataRes.put("status", "failure");
			jsonDataRes.put("code", httpstatus.getStatus());
			jsonDataRes.put("message", "updation failed. " + e);
		}
		return jsonDataRes;

	}

	

	
	
	// To get the allocation list

	@GetMapping(value = "/getResourceListBasedonProject/{projectId}")
	public JSONObject getAllocationListsBasedonProject(@PathVariable("projectId") Long projectId,
			HttpServletResponse httpstatus) {
		// Method invocation for getting allocation list based on the project
		List<AllocationModel> allocationModel = projectAllocation.getAllocationList(projectId);

		String response = null;
		JSONObject jsonData = new JSONObject();
		JSONObject jsonDataRes = new JSONObject();
		List<JSONObject> jsonArray = new ArrayList<>();
		Date currentDate = new Date();

		try {
			if (!(allocationModel.isEmpty() && allocationModel.size() > 0)) {
				for (AllocationModel item : allocationModel) {
					if (item.getEndDate().compareTo(currentDate) > 0) {
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("allocationId", item.getAllocId());
						if (item.getproject() != null) {
							jsonObject.put("projectTitle", item.getproject().getProjectName());
							jsonObject.put("projectCategory", item.getproject().getProjectCategory());
						}
						if (item.getuser() != null) {
							jsonObject.put("firstName", item.getuser().getFirstName());
							jsonObject.put("lastName", item.getuser().getLastName());
							jsonObject.put("role", item.getuser().getrole().getroleId());
						}
						jsonObject.put("allocatedVal", item.getAllocatedPerce());
						jsonObject.put("isBillable", item.getIsBillable());

						if (item.getuser() != null && item.getuser().getdepartment() != null)
							jsonObject.put("departmentName", item.getuser().getdepartment().getdepartmentName());
						jsonArray.add(jsonObject);
					}
				}
				jsonData.put("resourceList", jsonArray);
			}
			jsonDataRes.put("status", "success");
			jsonDataRes.put("code", httpstatus.getStatus());
			jsonDataRes.put("message", "success ");
			jsonDataRes.put("data", jsonData);

		} catch (Exception e) {
			jsonDataRes.put("status", "failure");
			jsonDataRes.put("code", httpstatus.getStatus());
			jsonDataRes.put("message", "failed. " + e);
		}
		return jsonDataRes;

	}
	

		
	// saving allocation details

	@PostMapping("/saveAllocation")
	public JSONObject saveAllocationDetails(@RequestBody JSONObject requestdata, HttpServletResponse httpstatus) {

		AllocationModel allocationModel = new AllocationModel();
		JSONObject jsonDataRes = new JSONObject();

		try {
           // Obtain the data from request data
			String date1 = requestdata.get("startDate").toString();
			String date2 = requestdata.get("endDate").toString();
			TimeZone zone = TimeZone.getTimeZone("MST");
			SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
			outputFormat.setTimeZone(zone);

			// Formating the date values
			Date startDate = null, endDate = null;
			if (!date1.isEmpty()) {
				startDate = outputFormat.parse(date1);
			}
			if (!date2.isEmpty()) {
				endDate = outputFormat.parse(date2);
			}
			// Setting values to Allocation model object
			String val = requestdata.get("allocatedPerce").toString();
			String projectId = requestdata.get("projectId").toString();
			String userId = requestdata.get("userId").toString();
			String isBillable = requestdata.get("isBillable").toString();
			
			ProjectModel project = projectService.findById(Long.parseLong(projectId));
			UserModel user = userService.getUserDetailsById(Long.parseLong(userId));
			
			allocationModel.setproject(project);
			allocationModel.setuser(user);
			allocationModel.setStartDate(startDate);
			allocationModel.setEndDate(endDate);
			allocationModel.setAllocatedPerce(Double.parseDouble(val));
			allocationModel.setIsBillable(Boolean.parseBoolean(isBillable));

			// Check whether the user is already allocated to the project.If so update the previous entry of the user otherwise new entry is created.
			Long allocId = projectAllocation.getAllocId(Long.parseLong(projectId),Long.parseLong(userId));
			if(allocId != null) {
				AllocationModel oldAlloc = projectAllocation.findDataById(allocId);
				if(oldAlloc != null) {
					oldAlloc.setAllocatedPerce(allocationModel.getAllocatedPerce());
					oldAlloc.setStartDate(allocationModel.getStartDate());
					oldAlloc.setEndDate(allocationModel.getEndDate());
					oldAlloc.setIsBillable(allocationModel.getIsBillable());
					projectAllocation.updateData(oldAlloc);
				}

			}
			else {
				
				projectAllocation.save(allocationModel);

			}
		
			jsonDataRes.put("status", "success");
			jsonDataRes.put("code", httpstatus.getStatus());
			jsonDataRes.put("message", "successfully saved. ");
		} catch (Exception e) {
			jsonDataRes.put("status", "failure");
			jsonDataRes.put("code", httpstatus.getStatus());
			jsonDataRes.put("message", "failed. " + e);
		}
		return jsonDataRes;
	}
		
	
		

	
	
	
	
	
	
	
	@PostMapping("/getUserData")
	public JSONObject getAllocationList(@RequestBody JSONObject requestData, HttpServletResponse httpstatus) {

		JSONObject jsonData = new JSONObject();
		JSONObject jsonDataRes = new JSONObject();
		List<JSONObject> jsonArrayFiltered = new ArrayList<>();
		TimeZone zone = TimeZone.getTimeZone("MST");
		SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		outputFormat.setTimeZone(zone);
		java.util.Date date1 = null, date2 = null;
		String uId = null,dId = null,startDate = null, endDate = null;
		try {

			if (!requestData.get("startDate").toString().isEmpty() && requestData.get("startDate").toString() != null) {
				startDate = requestData.get("startDate").toString();
				if (!startDate.isEmpty()) {
					date1 = outputFormat.parse(startDate);
					System.out.println("date1 : " + date1);

				}
			}
			if (!requestData.get("endDate").toString().isEmpty() && requestData.get("endDate").toString() != null) {
				endDate = requestData.get("endDate").toString();
				if (!endDate.isEmpty()) {
					date2 = outputFormat.parse(endDate);
					System.out.println("date2 : " + date2);

				}
			}
               

			if(!requestData.get("userId").toString().isEmpty() && requestData.get("userId").toString() != null)
				uId = requestData.get("userId").toString();
			if(!requestData.get("deptId").toString().isEmpty() && requestData.get("deptId").toString() != null)
			    dId = requestData.get("deptId").toString();
			 
			System.out.println("uid : "+ uId);
			System.out.println("did : "+ dId);

			
			// Obtain the user list if both department id and user id are not available

			if ((uId == null || uId == "") && (dId == null || dId == "")) {

				List<UserModel> userList = userService.getAllUsers();

				// Invoc getUserAllocationList() to findout the allocation details of the user
				if (userList != null) {
					for (UserModel user : userList) {

						// Invoc getUserAllocationList() to findout the allocation details of the user
						getUserAllocationList(user, date1, date2, jsonArrayFiltered);

					}
				}
			}

			// Obtain the user list only if the department id is available and user id is not available or if the user id is 0.
			
			else if ((dId != null || dId != "") && (uId == null || uId == "" || uId.equals("0"))) {
				Long deptId = Long.parseLong(dId);

				// Obtain the user list based on the department
				List<UserModel> userList = userService.getUserByDeptId(deptId);

				if (userList != null) {
					for (UserModel user : userList) {

						// Invoc getUserAllocationList() to findout the allocation details of the user
						getUserAllocationList(user, date1, date2, jsonArrayFiltered);

					}
				}
			}

			// Obtain the user list only if the user id is available and department id is not available
			
			else if ((uId != null && uId != "" && !uId.equals("0")) && (dId == null || dId == "")) {

				Long userId = Long.parseLong(uId);
				UserModel user = userService.getUserDetailsById(userId);

				// Invoc getUserAllocationList() to findout the allocation details of the user
				if (user != null) {
					getUserAllocationList(user, date1, date2, jsonArrayFiltered);

				}

			}

			// Obtain the user list if both department id and user id are available

			else if ((uId != null && uId != "" && !uId.equals("0")) && (dId != null || dId != "")) {
				Long deptId = Long.parseLong(dId);
				Long userId = Long.parseLong(uId);

				UserModel user = userService.getUser(deptId, userId);

				// Invoc getUserAllocationList() to findout the allocation details of the user
				if (user != null) {
					getUserAllocationList(user, date1, date2, jsonArrayFiltered);
				}
			}
			

			jsonData.put("user", jsonArrayFiltered);
			jsonDataRes.put("data", jsonData);
			jsonDataRes.put("status", "success");
			jsonDataRes.put("code", httpstatus.getStatus());
			jsonDataRes.put("message", "success. ");

		} catch (Exception e) {
			jsonDataRes.put("status", "failure");
			jsonDataRes.put("code", httpstatus.getStatus());
			jsonDataRes.put("message", "failed. " + e);
		}
		return jsonDataRes;
	}



	private void getUserAllocationList(UserModel user, Date date1, Date date2, List<JSONObject> jsonArrayFiltered) {

		// Checks whether the user has an entry on allocation table
		Boolean isExist = projectAllocation.checkIsExist(user.getUserId());
		if (isExist) {

			// find out the allocation details based on the date passed as an argument
			List<AllocationModel> newUserList = projectAllocation.getUsersList(user.getUserId(),date1,date2);
			
            

			System.out.println("size : " + newUserList.size());

			// Add user and project alocation details to the json object
			if (newUserList != null && newUserList.size() > 0) {
				JSONObject jsonObject = new JSONObject();
				int freeAlloc = 100;
				int totAlloc = 0;
				List<JSONObject> jsonArray = new ArrayList<>();
				jsonObject.put("userId", user.getUserId());
				jsonObject.put("firstName", user.getFirstName());
				jsonObject.put("lastName", user.getLastName());
				jsonObject.put("role", user.getrole().getroleId());
				jsonObject.put("department", user.getdepartment());
				for (AllocationModel item : newUserList) {
					JSONObject jsonObjectData = new JSONObject();
					jsonObjectData.put("allocationId", item.getAllocId());
					jsonObjectData.put("projectId", item.getproject().getProjectId());
					jsonObjectData.put("projectName", item.getproject().getProjectName());
					jsonObjectData.put("projectCategory", item.getproject().getProjectCategory());
					jsonObjectData.put("allocationPercentage", item.getAllocatedPerce());
					jsonObjectData.put("allocationStartDate", item.getStartDate().toString());
					jsonObjectData.put("allocationEndDate", item.getEndDate().toString());
					jsonObjectData.put("isBillable", item.getIsBillable());
					totAlloc += item.getAllocatedPerce();
					freeAlloc-= item.getAllocatedPerce();
					jsonArray.add(jsonObjectData);

				}
				System.out.println("total : "+totAlloc);
				jsonObject.put("totalAllocation", totAlloc);
				jsonObject.put("freeAlloc", freeAlloc);
				jsonObject.put("project", jsonArray);
				jsonArrayFiltered.add(jsonObject);
			} else {
				JSONObject jsonObject = new JSONObject();
				List<JSONObject> jsonArray = new ArrayList<>();
				jsonObject.put("userId", user.getUserId());
				jsonObject.put("firstName", user.getFirstName());
				jsonObject.put("lastName", user.getLastName());
				jsonObject.put("role", user.getrole().getroleId());
				jsonObject.put("department", user.getdepartment());
				jsonObject.put("project", jsonArray);
				jsonObject.put("freeAlloc", 100);
				jsonObject.put("totalAllocation", 0);
				jsonArrayFiltered.add(jsonObject);
			}

		}

		// If the user has no entry, then add user detais without project allocation details to the json object
		else {
			JSONObject jsonObject = new JSONObject();
			List<JSONObject> jsonArray = new ArrayList<>();
			jsonObject.put("userId", user.getUserId());
			jsonObject.put("firstName", user.getFirstName());
			jsonObject.put("lastName", user.getLastName());
			jsonObject.put("role", user.getrole().getroleId());
			jsonObject.put("department", user.getdepartment());
			jsonObject.put("project", jsonArray);
			jsonObject.put("freeAlloc", 100);
			jsonObject.put("totalAllocation", 0);
			jsonArrayFiltered.add(jsonObject);
		}
		
	}
			
		
}