package com.EMS.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.EMS.model.Alloc;
import com.EMS.model.DepartmentModel;
import com.EMS.model.ProjectModel;
import com.EMS.model.UserModel;
import com.EMS.service.ProjectService;
import com.EMS.service.ResourceAllocationService;
import com.EMS.service.UserService;

@RestController
@RequestMapping(value = "/resource")
public class ResourceAllocationController {

	@Autowired
	ResourceAllocationService resourceAllocation;

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
			List<UserModel> userList = resourceAllocation.getUserList();
			//Method invocation for getting department list
			List<DepartmentModel> departmentList = resourceAllocation.getDepartmentList();
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
					jsonObject.put("name", user.getFirstName()+" "+user.getLastName());
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

		try {
			String id = requestdata.get("id").toString();
			String allocatedVal = requestdata.get("allocatedPerce").toString();
//			String date1 = requestdata.get("startDate").toString();
//			String date2 = requestdata.get("endDate").toString();
//			TimeZone zone = TimeZone.getTimeZone("MST");
//			SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
//			outputFormat.setTimeZone(zone);
//			Date startDate = null, endDate = null;
//			if (!date1.isEmpty()) {
//				startDate = outputFormat.parse(date1);
//			}
//			if (!date2.isEmpty()) {
//				endDate = outputFormat.parse(date2);
//			}
			//Method invocation for getting allocation details
			Alloc alloc = resourceAllocation.findDataById(Long.parseLong(id));
			if (alloc != null) {
				alloc.setAllocatedPerce(Double.parseDouble(allocatedVal));
//				alloc.setStartDate(startDate);
//				alloc.setEndDate(endDate);
                //Updating allcation details
				resourceAllocation.updateData(alloc);
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
		List<Alloc> alloc = resourceAllocation.getAllocationList(projectId);

		String response = null;
		JSONObject jsonData = new JSONObject();
		JSONObject jsonDataRes = new JSONObject();
		List<JSONObject> jsonArray = new ArrayList<>();
		Date currentDate = new Date();

		try {
			if (!(alloc.isEmpty() && alloc.size() > 0)) {
				for (Alloc item : alloc) {
					if (item.getEndDate().compareTo(currentDate) > 0) {
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("allocationId", item.getAllocId());
						if (item.getproject() != null)
							jsonObject.put("projectTitle", item.getproject().getProjectName());
						if (item.getuser() != null)
							jsonObject.put("name", item.getuser().getFirstName());
						jsonObject.put("allocatedVal", item.getAllocatedPerce());

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

		Alloc alloc = new Alloc();
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
				System.out.println("startDate : " + startDate);
				alloc.setStartDate(startDate);
			}
			if (!date2.isEmpty()) {
				endDate = outputFormat.parse(date2);
				System.out.println("endDate : " + endDate);
				alloc.setEndDate(endDate);
			}
			// Setting values to Allocation model object
			String val = requestdata.get("allocatedPerce").toString();
			alloc.setAllocatedPerce(Double.parseDouble(val));
			String projectId = requestdata.get("projectId").toString();
			ProjectModel project = projectService.findById(Long.parseLong(projectId));
			alloc.setproject(project);
			String userId = requestdata.get("userId").toString();
			UserModel user = userService.getUserDetailsById(Long.parseLong(userId));
			alloc.setuser(user);
			resourceAllocation.save(alloc);

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
		
	
		

	// Get allocation list by user

	@PostMapping("/getListsByUserId")
	public JSONObject getAllocationListBy(@RequestBody JSONObject requestData, HttpServletResponse httpstatus) {

		List<Alloc> newList = new ArrayList<Alloc>();
		JSONObject jsonData = new JSONObject();
		JSONObject jsonDataRes = new JSONObject();
		List<JSONObject> jsonArrayFiltered = new ArrayList<>();
		TimeZone zone = TimeZone.getTimeZone("MST");
		SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		outputFormat.setTimeZone(zone);
		java.util.Date date1 = null, date2 = null;
		try {
			String id = requestData.get("userId").toString();
			Long userId = Long.parseLong(id);
			String startDate = requestData.get("startDate").toString();
			String endDate = requestData.get("endDate").toString();
			if (!startDate.isEmpty()) {
				date1 = outputFormat.parse(startDate);
				System.out.println("date : " + date1);
			}
			if (!endDate.isEmpty()) {
				date2 = outputFormat.parse(endDate);
				System.out.println("date2 : " + date2);
			}

			UserModel user = userService.getUserDetailsById(userId);
			Boolean isExist = resourceAllocation.checkIsExist(userId);
			if (isExist) {
				List<Alloc> allocationList = resourceAllocation.getListByUser(userId);
				for (Alloc item : allocationList) {
					if ((item.getEndDate().compareTo(date1) > 0) && (item.getStartDate().compareTo(date2) < 0)) {
						newList.add(item);
					}
				}

				System.out.println("size : " + newList.size());

				if (newList != null && newList.size() > 0) {
					JSONObject jsonObject = new JSONObject();
					List<JSONObject> jsonArray = new ArrayList<>();
					jsonObject.put("userId", userId);
					jsonObject.put("userName", user.getFirstName());
					jsonObject.put("department", user.getdepartment());
					for (Alloc item : newList) {
						JSONObject jsonObjectData = new JSONObject();
						jsonObjectData.put("allocationId", item.getAllocId());
						jsonObjectData.put("projectId", item.getproject().getProjectId());
						jsonObjectData.put("projectName", item.getproject().getProjectName());
						jsonObjectData.put("allocationPercentage", item.getAllocatedPerce());
						jsonObjectData.put("allocationStartDate", item.getStartDate().toString());
						jsonObjectData.put("allocationEndDate", item.getEndDate().toString());
						jsonArray.add(jsonObjectData);

					}
					jsonObject.put("project", jsonArray);
					jsonArrayFiltered.add(jsonObject);
				} else {
					JSONObject jsonObject = new JSONObject();
					List<JSONObject> jsonArray = new ArrayList<>();
					jsonObject.put("userId", user.getUserId());
					jsonObject.put("userName", user.getFirstName());
					jsonObject.put("department", user.getdepartment());
					jsonObject.put("project", jsonArray);
					jsonArrayFiltered.add(jsonObject);
				}

			} else {
				JSONObject jsonObject = new JSONObject();
				List<JSONObject> jsonArray = new ArrayList<>();
				jsonObject.put("userId", user.getUserId());
				jsonObject.put("userName", user.getFirstName());
				jsonObject.put("department", user.getdepartment());
				jsonObject.put("project", jsonArray);
				jsonArrayFiltered.add(jsonObject);
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
	
	
	
		
	// Get user list based on department

	@PostMapping("/getListsByDate")
	public JSONObject getAllocationListByDate(@RequestBody JSONObject requestData, HttpServletResponse httpstatus) {

		JSONObject jsonData = new JSONObject();
		JSONObject jsonDataRes = new JSONObject();
		List<JSONObject> jsonArrayFiltered = new ArrayList<>();
		TimeZone zone = TimeZone.getTimeZone("MST");
		SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		outputFormat.setTimeZone(zone);
		java.util.Date date1 = null, date2 = null;
		try {

			String id = requestData.get("deptId").toString();
			Long deptId = Long.parseLong(id);
			String startDate = requestData.get("startDate").toString();
			String endDate = requestData.get("endDate").toString();

			if (!startDate.isEmpty()) {
				date1 = outputFormat.parse(startDate);
				System.out.println("date1 : " + date1);

			}
			if (!endDate.isEmpty()) {
				date2 = outputFormat.parse(endDate);
				System.out.println("date2 : " + date2);

			}

			List<UserModel> userList = userService.getUserByDeptId(deptId);

			if (userList != null) {
				for (UserModel user : userList) {
					Boolean isExist = resourceAllocation.checkIsExist(user.getUserId());
					List<Alloc> newList = new ArrayList<Alloc>();

					if (isExist) {
						List<Alloc> allocationList = resourceAllocation.getListByUser(user.getUserId());
						for (Alloc item : allocationList) {
							if ((item.getEndDate().compareTo(date1) > 0) && (item.getStartDate().compareTo(date2) < 0)) {
								newList.add(item);
							}
						}
						System.out.println("size : " + newList.size());

						if (newList != null && newList.size() > 0) {
							JSONObject jsonObject = new JSONObject();
							List<JSONObject> jsonArray = new ArrayList<>();
							jsonObject.put("userId", user.getUserId());
							jsonObject.put("userName", user.getFirstName());
							jsonObject.put("department", user.getdepartment());
							for (Alloc item : newList) {
								JSONObject jsonObjectData = new JSONObject();
								jsonObjectData.put("projectId", item.getproject().getProjectId());
								jsonObjectData.put("allocationId", item.getAllocId());
								jsonObjectData.put("projectName", item.getproject().getProjectName());
								jsonObjectData.put("allocationPercentage", item.getAllocatedPerce());
								jsonObjectData.put("allocationStartDate", item.getStartDate().toString());
								jsonObjectData.put("allocationEndDate", item.getEndDate().toString());
								jsonArray.add(jsonObjectData);
							}
							jsonObject.put("project", jsonArray);
							jsonArrayFiltered.add(jsonObject);
						}
						else {
							JSONObject jsonObject = new JSONObject();
							List<JSONObject> jsonArray = new ArrayList<>();
							jsonObject.put("userId", user.getUserId());
							jsonObject.put("userName", user.getFirstName());
							jsonObject.put("department", user.getdepartment());
							jsonObject.put("project", jsonArray);
							jsonArrayFiltered.add(jsonObject);
						}
					} else {
						JSONObject jsonObject = new JSONObject();
						List<JSONObject> jsonArray = new ArrayList<>();
						jsonObject.put("userId", user.getUserId());
						jsonObject.put("userName", user.getFirstName());
						jsonObject.put("department", user.getdepartment());
						jsonObject.put("project", jsonArray);
						jsonArrayFiltered.add(jsonObject);
					}
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
		
	
	
	
	
	
	
	@PostMapping("/getUserData")
	public JSONObject getAllocationList(@RequestBody JSONObject requestData, HttpServletResponse httpstatus) {

		JSONObject jsonData = new JSONObject();
		JSONObject jsonDataRes = new JSONObject();
		List<JSONObject> jsonArrayFiltered = new ArrayList<>();
		TimeZone zone = TimeZone.getTimeZone("MST");
		SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		outputFormat.setTimeZone(zone);
		java.util.Date date1 = null, date2 = null;
		try {

			String startDate = requestData.get("startDate").toString();
			String endDate = requestData.get("endDate").toString();

			if (!startDate.isEmpty()) {
				date1 = outputFormat.parse(startDate);
				System.out.println("date1 : " + date1);

			}
			if (!endDate.isEmpty()) {
				date2 = outputFormat.parse(endDate);
				System.out.println("date2 : " + date2);

			}

			String uId = requestData.get("userId").toString();
			String dId = requestData.get("deptId").toString();

			// Obtain the user list only if the department id is available and user id is not available
			if ((dId != null || dId != "") && (uId == null || uId == "")) {
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
			else if ((uId != null && uId != "") && (dId == null || dId == "")) {

				Long userId = Long.parseLong(uId);
				UserModel user = userService.getUserDetailsById(userId);

				// Invoc getUserAllocationList() to findout the allocation details of the user
				if (user != null) {
					getUserAllocationList(user, date1, date2, jsonArrayFiltered);

				}

			}

			// Obtain the user list if both department id and user id are available

			else if ((uId != null && uId != "") && (dId != null || dId != "")) {
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

//		List<Alloc> newUserList = new ArrayList<Alloc>();

		// Checks whether the user has an entry on allocation table
		Boolean isExist = resourceAllocation.checkIsExist(user.getUserId());
		if (isExist) {

			// find out the allocation lists of a user
//			List<Alloc> allocationList = resourceAllocation.getListByUser(user.getUserId());
//			for (Alloc item : allocationList) {
//
//				// find out the allocation details based on the date passed as an argument and added to a new list
//				if ((item.getEndDate().compareTo(date1) > 0) && (item.getStartDate().compareTo(date2) < 0)) {
//					newUserList.add(item);
//				}
//			}

			// find out the allocation details based on the date passed as an argument
			List<Alloc> newUserList = resourceAllocation.getUsersList(user.getUserId(),date1,date2);
			
            

			System.out.println("size : " + newUserList.size());

			// Add user and project alocation details to the json object
			if (newUserList != null && newUserList.size() > 0) {
				JSONObject jsonObject = new JSONObject();
				int freeAlloc = 100;
				List<JSONObject> jsonArray = new ArrayList<>();
				jsonObject.put("userId", user.getUserId());
				jsonObject.put("userName", user.getFirstName());
				jsonObject.put("department", user.getdepartment());
				for (Alloc item : newUserList) {
					JSONObject jsonObjectData = new JSONObject();
					jsonObjectData.put("allocationId", item.getAllocId());
					jsonObjectData.put("projectId", item.getproject().getProjectId());
					jsonObjectData.put("projectName", item.getproject().getProjectName());
					jsonObjectData.put("allocationPercentage", item.getAllocatedPerce());
					jsonObjectData.put("allocationStartDate", item.getStartDate().toString());
					jsonObjectData.put("allocationEndDate", item.getEndDate().toString());
					freeAlloc-= item.getAllocatedPerce();
					
					
					
					
					jsonArray.add(jsonObjectData);

				}
				jsonObject.put("freeAlloc", freeAlloc);
				jsonObject.put("project", jsonArray);
				jsonArrayFiltered.add(jsonObject);
			} else {
				JSONObject jsonObject = new JSONObject();
				List<JSONObject> jsonArray = new ArrayList<>();
				jsonObject.put("userId", user.getUserId());
				jsonObject.put("userName", user.getFirstName());
				jsonObject.put("department", user.getdepartment());
				jsonObject.put("project", jsonArray);
				jsonObject.put("freeAlloc", 100);
				jsonArrayFiltered.add(jsonObject);
			}

		}

		// If the user has no entry, then add user detais without project allocation details to the json object
		else {
			JSONObject jsonObject = new JSONObject();
			List<JSONObject> jsonArray = new ArrayList<>();
			jsonObject.put("userId", user.getUserId());
			jsonObject.put("userName", user.getFirstName());
			jsonObject.put("department", user.getdepartment());
			jsonObject.put("project", jsonArray);
			jsonObject.put("freeAlloc", 100);
			jsonArrayFiltered.add(jsonObject);
		}
		
	}
			
		
}