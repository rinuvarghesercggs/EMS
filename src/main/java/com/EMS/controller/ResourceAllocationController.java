package com.EMS.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

	// To get user and department list

	@GetMapping(value = "/getUsersAndDepartments")
	public JSONObject getUsernameList() {
		JSONObject jsonData = new JSONObject();
		JSONObject jsonDataRes = new JSONObject();
		try {
			List<UserModel> userList = resourceAllocation.getUserList();
//			List<DepartmentModel> departmentList = resourceAllocation.getDepartmentList();
			List<ProjectModel> projectList = projectService.getProjectList();
			

			List<JSONObject> jsonArray = new ArrayList<>();
			List<JSONObject> jsonProjectArray = new ArrayList<>();


			if (!(userList).isEmpty() && userList.size() > 0) {
				for (UserModel user : userList) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("userId", user.getUserId());
					jsonObject.put("name", user.getFirstName());
					jsonObject.put("department", user.getdepartment());
					jsonArray.add(jsonObject);
				}
				jsonData.put("userList", jsonArray);
			}

			if (!(projectList).isEmpty() && projectList.size() > 0) {
				for (ProjectModel project : projectList) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("projectId", project.getProjectId());
					jsonObject.put("projectName", project.getProjectName());
					jsonProjectArray.add(jsonObject);
				}			
				jsonData.put("projectList", jsonProjectArray);
			}

		} catch (Exception e) {
			jsonDataRes.put("status", "failure");
		}
		jsonDataRes.put("data", jsonData);
		jsonDataRes.put("status", "success");

		return jsonDataRes;

	}

	
	// To update resource allocation data

	@PutMapping(value = "/editAllocation")
	public JSONObject updateData(@RequestBody JSONObject requestdata,HttpServletResponse httpstatus) {

		JSONObject jsonDataRes = new JSONObject();

		try {
			String id = requestdata.get("id").toString();
			String allocatedVal = requestdata.get("allocatedPerce").toString();
			String date1 = requestdata.get("startDate").toString();
			String date2 = requestdata.get("endDate").toString();
			DateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
			Date startDate = null, endDate = null;
			if (!date1.isEmpty()) {
				startDate = formatter.parse(date1);
			}
			if (!date2.isEmpty()) {
				endDate = formatter.parse(date2);
			}
			Alloc alloc = resourceAllocation.findDataById(Long.parseLong(id));
			if (alloc != null) {
				alloc.setAllocatedPerce(Double.parseDouble(allocatedVal));
				alloc.setStartDate(startDate);
				alloc.setEndDate(endDate);
				

				resourceAllocation.updateData(alloc);
				jsonDataRes.put("status", "success");
				jsonDataRes.put("code", httpstatus.getStatus());
				jsonDataRes.put("message", "updated successfully");
			}

		} catch (Exception e) {
			jsonDataRes.put("status", "failure");
			jsonDataRes.put("code", httpstatus.getStatus());
			jsonDataRes.put("message", "updated failed. "+e);
		}

		return jsonDataRes;
	}
	

	

	
	
	// To get the allocation list

		@GetMapping(value = "/getResourceListBasedonProject/{projectId}")
		public JSONObject getAllocationListsBasedonProject(@PathVariable("projectId") Long projectId) {
			List<Alloc> alloc = resourceAllocation.getAllocationList(projectId);

			String response = null;
			JSONObject jsonData = new JSONObject();
			JSONObject jsonDataRes = new JSONObject();
			List<JSONObject> jsonArray = new ArrayList<>();
			Date currentDate = new Date();

				try {
					if (!(alloc.isEmpty() && alloc.size() > 0)) {
						for (Alloc item : alloc) {
	                    	if(item.getEndDate().compareTo(currentDate) > 0) {
	                    		JSONObject jsonObject = new JSONObject();
								jsonObject.put("allocationId", item.getAllocId());
								if(item.getproject() != null)
									jsonObject.put("projectTitle", item.getproject().getProjectName());
								if(item.getuser() != null)
									jsonObject.put("name", item.getuser().getFirstName());
								jsonObject.put("allocatedVal", item.getAllocatedPerce());

//								jsonObject.put("allocatedFree", item.getFreeAllocation());
							if (item.getuser() != null && item.getuser().getdepartment() != null)
								jsonObject.put("department name", item.getuser().getdepartment().getdepartmentName());
							jsonArray.add(jsonObject);
	                    	}
	
					}

					jsonData.put("resourceList", jsonArray);
				}

				jsonDataRes.put("status", "success");

			} catch (Exception e) {
				jsonDataRes.put("status", "failure");
			}
			jsonDataRes.put("data", jsonData);
			return jsonDataRes;

		}
	
	
		
		// New allocation list
//		@GetMapping("/getLists/{deptId}")
//		public JSONObject getAllocationList(@PathVariable("deptId") Long deptId) {
//			List<UserModel> userList = userService.getUserByDeptId(deptId);
//			System.out.println("length : "+ userList.size());
//			List<Alloc> newList = new ArrayList<Alloc>();
//			List<JSONObject> jsonArray = new ArrayList<>();
//			JSONObject jsonData = new JSONObject();
//			JSONObject jsonDataRes = new JSONObject();
//
//
//			for(UserModel user : userList) {
//				
//				Boolean isExist = resourceAllocation.checkIsExist(user.getUserId());
//				System.out.println("isExist : "+ isExist);
//				if(isExist) {
//					List<Alloc> allocationList = resourceAllocation.getListByUser(user.getUserId());
//					System.out.println("id : "+ allocationList.size());
//					Date currentDate = new Date();
//                    for(Alloc alloc : allocationList){
//                    	if(alloc.getEndDate().compareTo(currentDate) > 0) {
//        					System.out.println("alloc.getEndDate : "+ alloc.getEndDate());
//        					newList.add(alloc);
//
//                    	}
//                    }
//				
//				System.out.println("newList : "+ newList.size());
//
//				for(Alloc item : newList) {
//					JSONObject jsonObject = new JSONObject();
//					jsonObject.put("allocationId", item.getAllocId());
//					if(item.getproject() != null)
//						jsonObject.put("projectTitle", item.getproject().getProjectName());
//					if(item.getuser() != null)
//						jsonObject.put("name", item.getuser().getFirstName());
//					jsonObject.put("allocatedVal", item.getAllocatedPerce());
//
////					jsonObject.put("allocatedFree", item.getFreeAllocation());
//				if (item.getuser() != null && item.getuser().getdepartment() != null)
//					jsonObject.put("department name", item.getuser().getdepartment().getdepartmentName());
//				
//				
//				jsonArray.add(jsonObject);
//
//				}
//			}
//				else {
//					JSONObject jsonObject = new JSONObject();
//					jsonObject.put("allocationId", null);
////					if(item.getproject() != null)
//						jsonObject.put("projectTitle", null);
////					if(item.getuser() != null)
//						jsonObject.put("name", user.getFirstName());
//					jsonObject.put("allocatedVal", null);
//
////					jsonObject.put("allocatedFree", item.getFreeAllocation());
////				if (item.getuser() != null && item.getuser().getdepartment() != null)
//					jsonObject.put("department name", user.getdepartment().getdepartmentName());
//				
//				
//				jsonArray.add(jsonObject);
//				}
//				
//				}
//			jsonData.put("resourceList", jsonArray);
//			
//			jsonDataRes.put("data", jsonData);
//			jsonDataRes.put("status", "success");
//			return jsonDataRes;
//			
//		}
		
		
		
		
		
		//Get user list based on department
		
		@GetMapping("/getLists/{deptId}")
		public JSONObject getAllocationList(@PathVariable("deptId") Long deptId) {
			
			List<Alloc> newList = new ArrayList<Alloc>();
			List<JSONObject> jsonArray = new ArrayList<>();
			JSONObject jsonData = new JSONObject();
			JSONObject jsonDataRes = new JSONObject();

			try {
				List<UserModel> userList = userService.getUserByDeptId(deptId);
				
				if(userList != null) {
					for(UserModel user : userList) {
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("userId", user.getUserId());
						jsonObject.put("userName", user.getFirstName());
						jsonObject.put("department", user.getdepartment());
						jsonArray.add(jsonObject);
					}
					
				}
			}
			catch (Exception e) {
				jsonDataRes.put("status", "failure");
			}
			
			
			jsonData.put("resourceList", jsonArray);
			
			jsonDataRes.put("data", jsonData);
			jsonDataRes.put("status", "success");
			return jsonDataRes;
			
		}
		
		
		
		//Get allocation list by user
		
		@GetMapping("/getAllocLists/{userId}")
		public JSONObject getUserAllocationList(@PathVariable("userId") Long userId) {
			
			List<JSONObject> jsonArray = new ArrayList<>();
			JSONObject jsonData = new JSONObject();
			JSONObject jsonDataRes = new JSONObject();
			List<Alloc> newList = new ArrayList<Alloc>();


			try {
				Boolean isExist = resourceAllocation.checkIsExist(userId);
				System.out.println("isExist : "+ isExist);
				
				if(isExist) {
					List<Alloc> allocList = resourceAllocation.getListByUser(userId);
					

					Date currentDate = new Date();
	              for(Alloc alloc : allocList){
	              	if(alloc.getEndDate().compareTo(currentDate) > 0) {
	  					newList.add(alloc);

	              	}
	              }
				

					
					if(newList != null && newList.size() > 0) {
						for(Alloc alloc : newList) {
							JSONObject jsonObject = new JSONObject();
							jsonObject.put("allocationId", alloc.getAllocId());
							jsonObject.put("userId", alloc.getuser().getUserId());
							jsonObject.put("userName", alloc.getuser().getFirstName());
							jsonObject.put("department", alloc.getuser().getdepartment());
							jsonObject.put("allocatedVal", alloc.getAllocatedPerce());
							jsonObject.put("allocationStartDate", alloc.getStartDate());
							jsonObject.put("allocationEndDate", alloc.getEndDate());
//							jsonObject.put("project", alloc.getproject());
							jsonArray.add(jsonObject);
						}
						
					}
					else {
						UserModel user = userService.getUserDetailsById(userId);
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("allocationId", null);
						jsonObject.put("userId", userId);
						jsonObject.put("userName", user.getFirstName());
						jsonObject.put("department", user.getdepartment());
						jsonObject.put("allocatedVal", 0.0);
						jsonArray.add(jsonObject);
					}
					
				}
				else {
					UserModel user = userService.getUserDetailsById(userId);
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("allocationId", null);
					jsonObject.put("userId", userId);
					jsonObject.put("userName", user.getFirstName());
					jsonObject.put("department", user.getdepartment());
					jsonObject.put("allocatedVal", 0.0);
					jsonArray.add(jsonObject);
				}
				
			}
			catch (Exception e) {
				jsonDataRes.put("status", "failure");

			}
			
			
			jsonData.put("resourceList", jsonArray);
			jsonDataRes.put("data", jsonData);
			jsonDataRes.put("status", "success");
			
			return jsonDataRes;
			
		}
		
		
		
		// saving allocation details
		
		@PostMapping("/saveAllocation")
		public JSONObject saveAllocationDetails(@RequestBody JSONObject requestdata) {
			
			Alloc alloc = new Alloc();
			JSONObject jsonDataRes = new JSONObject();
			try {
				
				String date1 = requestdata.get("startDate").toString();
				String date2 = requestdata.get("endDate").toString();
				DateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
				Date startDate = null, endDate = null;
				if (!date1.isEmpty()) {
					startDate = formatter.parse(date1);
					alloc.setStartDate(startDate);
				}
				if (!date2.isEmpty()) {
					endDate = formatter.parse(date2);
					alloc.setEndDate(endDate);
				}
				String val = requestdata.get("allocatedPerce").toString();
				alloc.setAllocatedPerce(Double.parseDouble(val));
				String projectId = requestdata.get("projectId").toString();
				ProjectModel project = projectService.findById(Long.parseLong(projectId));
				alloc.setproject(project);
				String userId = requestdata.get("userId").toString();
				UserModel user = userService.getUserDetailsById(Long.parseLong(userId));
				alloc.setuser(user);

				resourceAllocation.save(alloc);
				System.out.println("dfg");
			}
			catch (Exception e) {
				jsonDataRes.put("status", "failure");
			}
			jsonDataRes.put("status", "success");

			return jsonDataRes;
			
		}
		
		
		
		
		
		//Get user list based on department
		
	@PostMapping("/getListsByDate")
	public JSONObject getAllocationListByDate(@RequestBody JSONObject requestData) {

		List<Alloc> newList = new ArrayList<Alloc>();
		JSONObject jsonData = new JSONObject();
		JSONObject jsonDataRes = new JSONObject();
		List<JSONObject> jsonArrayFiltered = new ArrayList<>();
		DateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
		SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

		java.util.Date date1 = null,date2 = null;
		try {	
		String id = requestData.get("deptId").toString();
		Long deptId = Long.parseLong(id);
		String startDate = requestData.get("startDate").toString();
		String endDate = requestData.get("endDate").toString();
		if (!startDate.isEmpty()) {
		     date1 = outputFormat.parse(startDate);
			System.out.println("sql date3 : "+date1);

		}
		if (!endDate.isEmpty()) {
			date2 = formatter.parse(endDate);
		}
	
			List<UserModel> userList = userService.getUserByDeptId(deptId);

			if (userList != null) {
				for (UserModel user : userList) {

					Boolean isExist = resourceAllocation.checkIsExist(user.getUserId());
					System.out.println("isExist : " + isExist);

					if (isExist) {
						List<Alloc> allocationList = resourceAllocation.getListByUser(user.getUserId());
						System.out.println("id : " + allocationList.size());

						for (Alloc item : allocationList) {
							System.out.println("item.getEndDate() : "+item.getEndDate());
							System.out.println("date1 : "+date1);

//							java.util.Date date4 = new Date(new java.util.Date(item.getStartDate()));

							if ((item.getEndDate().compareTo(date1) < 0) && item.getStartDate().compareTo(date2) > 0) {

								newList.add(item);

							}

						}

						System.out.println("size : "+newList.size());
						if(newList.size() > 0) {
							JSONObject jsonObject = new JSONObject();
							List<JSONObject> jsonArray = new ArrayList<>();


							jsonObject.put("userId", user.getUserId());
							jsonObject.put("userName", user.getFirstName());
							jsonObject.put("department", user.getdepartment());
							for(Alloc item : newList ) {
								JSONObject jsonObjectData = new JSONObject();
								jsonObjectData.put("projectId",item.getproject().getProjectId());
								jsonObjectData.put("projectName", item.getproject().getProjectName());
								jsonObjectData.put("allocationPercentage", item.getAllocatedPerce());
								jsonArray.add(jsonObjectData);

							}
							jsonObject.put("project", jsonArray);
							jsonArrayFiltered.add(jsonObject);

						}
						
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
				}

			}
		}
		catch (Exception e) {
			jsonDataRes.put("status", "failure");
		}

		jsonData.put("user", jsonArrayFiltered);

		jsonDataRes.put("data", jsonData);
		jsonDataRes.put("status", "success");
		return jsonDataRes;

	}
		
		
		
}
