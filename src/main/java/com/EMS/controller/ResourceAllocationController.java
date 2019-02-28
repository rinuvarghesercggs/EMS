package com.EMS.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.EMS.model.Alloc;
import com.EMS.model.DepartmentModel;
import com.EMS.model.UserModel;
import com.EMS.service.ProjectService;
import com.EMS.service.ResourceAllocationService;

@RestController
@RequestMapping(value = "/resource")
public class ResourceAllocationController {

	@Autowired
	ResourceAllocationService resourceAllocation;

	@Autowired
	ProjectService projectService;

	// To get user and department list

	@GetMapping(value = "/getUsersAndDepartments")
	public JSONObject getUsernameList() {
		JSONObject jsonData = new JSONObject();
		JSONObject jsonDataRes = new JSONObject();
		try {
			List<UserModel> userList = resourceAllocation.getUserList();
			List<DepartmentModel> departmentList = resourceAllocation.getDepartmentList();

			if (!(userList).isEmpty() && userList.size() > 0 && !(departmentList).isEmpty()
					&& departmentList.size() > 0) {
				jsonData.put("userList", userList);
				jsonData.put("departmentList", departmentList);
				jsonDataRes.put("status", "Success");
			}
		} catch (Exception e) {
			jsonDataRes.put("status", "Failure");
		}
		jsonDataRes.put("data", jsonData);
		return jsonDataRes;

	}

	// To get the allocation list based on project name

	@GetMapping(value = "/getresourceList/{projectId}")
	public JSONObject getAllocationLists(@PathVariable("projectId") Long projectId) {
		List<Alloc> alloc = resourceAllocation.getAllocationList(projectId);
		String response = null;
		JSONObject jsonData = new JSONObject();
		JSONObject jsonDataRes = new JSONObject();
		List<JSONObject> jsonArray = new ArrayList<>();

		try {
			if (!(alloc.isEmpty() && alloc.size() > 0)) {
				for (Alloc item : alloc) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("id", item.getId());
					jsonObject.put("projectTitle", item.getProjectModel().getProject_name());
					jsonObject.put("name", item.getUserModel().getFirstName());
					jsonObject.put("allocatedVal", item.getAllocatedPerce());
					jsonObject.put("allocatedFree", item.getFreeAllocation());
					jsonArray.add(jsonObject);
				}

				jsonData.put("resourceList", jsonArray);
				jsonDataRes.put("status", "Success");
			}
		} catch (Exception e) {
			jsonDataRes.put("status", "Failure");
		}
		jsonDataRes.put("data", jsonData);
		return jsonDataRes;

	}

	// To update resource allocation data

	@PutMapping(value = "/editAllocation")
	public JSONObject updateData(@RequestBody JSONObject requestdata) {

		JSONObject jsonDataRes = new JSONObject();

		try {
			String id = requestdata.get("id").toString();
			String allocatedVal = requestdata.get("allocatedVal").toString();
			String allocatedFree = requestdata.get("allocatedFree").toString();

			Alloc alloc = resourceAllocation.findDataBy(Long.parseLong(id));
			if (alloc != null) {
				alloc.setAllocatedPerce(Double.parseDouble(allocatedVal));
				alloc.setFreeAllocation(Double.parseDouble(allocatedFree));

				resourceAllocation.updateData(alloc);
				jsonDataRes.put("status", "success");
			}

		} catch (Exception e) {
			jsonDataRes.put("status", "Failure");
		}

		return jsonDataRes;
	}
	
	
	

//	// To get user name list
//	
//	@GetMapping(value = "/getUsers")
//	public JSONObject getUsernameList() {
//		JSONObject jsonData = new JSONObject();
//		JSONObject jsonDataRes = new JSONObject();
//		try {
//			List<UserModel> userList = resourceAllocation.getUserList();
//			if( !(userList).isEmpty() && userList.size() > 0) {
//				jsonData.put("userList", userList);
//				jsonDataRes.put("status", "Success");
//			}
//		}catch (Exception e) {
//			jsonDataRes.put("status", "Failure");
//		}
//		jsonDataRes.put("data", jsonData);
//		return jsonDataRes;
//
//	}
//	
//	
//
//	// To get department name list
//	
//	@GetMapping(value = "/getDepartment")
//	public JSONObject getDepartmentnameList() {
//		JSONObject jsonData = new JSONObject();
//		JSONObject jsonDataRes = new JSONObject();
//
//		try {
//			List<DepartmentModel> departmentList = resourceAllocation.getDepartmentList();
//			if( !(departmentList).isEmpty() && departmentList.size() > 0 ) {
//				jsonDataRes.put("status", "Success");
//				jsonData.put("departmentList", departmentList);
//			}
//		}catch (Exception e) {
//			jsonDataRes.put("status", "Failure");
//		}
//			jsonDataRes.put("data", jsonData);
//			
//		return jsonDataRes;
//
//	}

}
