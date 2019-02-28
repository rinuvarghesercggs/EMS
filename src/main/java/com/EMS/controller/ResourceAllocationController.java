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

	

	// To get user name list

	@GetMapping(value = "/getUsers")
	public List getUsernameList() {
		List<UserModel> userList = resourceAllocation.getUserList();
		return userList;

	}

	
	// To get department name list

	@GetMapping(value = "/getDepartment")
	public List getDepartmentnameList() {
		List<DepartmentModel> departmentList = resourceAllocation.getDepartmentList();
		return departmentList;

	}
	

	// To get the allocation list based on project name

//	@GetMapping(value = "/getresourceList/{projectId}")
//	public List<Alloc> getAllocationLists(@PathVariable("projectId") Long projectId) {
//		List<Alloc> alloc = resourceAllocation.getAllocationList(projectId);
////		 List<Alloc> alloc = resourceAllocation.getList();
//		
//		return alloc;
//
//	}
	
	
	@GetMapping(value = "/getresourceList/{projectId}")
	public JSONObject getAllocationLists(@PathVariable("projectId") Long projectId) {
		List<Alloc> alloc = resourceAllocation.getAllocationList(projectId);
//		 List<Alloc> alloc = resourceAllocation.getList();
		String response = null;
		JSONObject jsonData = new JSONObject();
		JSONObject jsonDataRes = new JSONObject();
		List<JSONObject> jsonArray = new ArrayList<>();
		

//		LOG.info("Inside  getThirdPartyIncidentReportMainTable In ReportController");
        try {
        	
        	for(Alloc item : alloc) {
               	JSONObject jsonObject = new JSONObject();
               	jsonObject.put("id", item.getId());
               	jsonObject.put("projectTitle", item.getProjectModel().getProject_name());
               	jsonObject.put("name", item.getUserModel().getFirstName());
               	jsonObject.put("allocatedVal", item.getAllocatedPerce());
               	jsonObject.put("allocatedFree", item.getFreeAllocation());
               	jsonArray.add(jsonObject);
    		}
        	
        	jsonData.put("resourceList", jsonArray);
        	jsonDataRes.put("status", "success");
        	
        } catch (Exception e) {
            jsonDataRes.put("status", "Failure");
//            LOG.error("Exception getThirdPartyIncidentReportGraph " + e.getLocalizedMessage());
        }
        jsonDataRes.put("data", jsonData);
        return jsonDataRes;
		
		
		

	}
	

	// To update resource allocation data
	
	@PutMapping(value = "/editAllocation")
	public JSONObject updateData(@RequestBody JSONObject requestdata) {
		
		JSONObject jsonDataRes = new JSONObject();
		try {
			Long id = (Long) requestdata.get("id");
			Double allocatedVal = (Double) requestdata.get("allocatedVal");
			Double allocatedFree = (Double) requestdata.get("allocatedFree");
			
			System.out.println("id"+id);
			Alloc alloc = resourceAllocation.findDataBy(id);
			alloc.setAllocatedPerce(allocatedVal);
			alloc.setFreeAllocation(allocatedFree);
			resourceAllocation.updateData(alloc);
			
		}catch (Exception e) {
	      jsonDataRes.put("status", "Failure");
		}
		
		
//		Long id = newAlloc.getId();
//		try {
//			Alloc oldAlloc = resourceAllocation.findDataBy(id);
//			if (oldAlloc == null) {
////				return new ResponseEntity<JSONObject>(HttpStatus.NOT_FOUND);
//			}
//		} catch (Exception e) {
//            jsonDataRes.put("status", "Failure");
//		}
//		Alloc alloc = resourceAllocation.updateData(newAlloc);
        jsonDataRes.put("status", "success");

		return jsonDataRes;
	}

}
