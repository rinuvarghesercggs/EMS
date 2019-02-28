package com.EMS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	@GetMapping(value = "/getresourceList/{projectId}")
	public List<Alloc> getAllocationLists(@PathVariable("projectId") Long projectId) {
		List<Alloc> alloc = resourceAllocation.getAllocationList(projectId);
//		 List<Alloc> alloc = resourceAllocation.getList();
		
		return alloc;

	}
	
	
//	@GetMapping(value = "/getresourceList/{projectId}")
//	public String getAllocationLists(@PathVariable("projectId") Long projectId) {
//		List<Alloc> alloc = resourceAllocation.getAllocationList(projectId);
////		 List<Alloc> alloc = resourceAllocation.getList();
//		for(Alloc item : alloc) {
//			
//		}
//		
//		
//		return null;
//
//	}
//	

	// To update resource allocation data
	
	@PutMapping(value = "/update")
	public ResponseEntity<Alloc> updateData(@RequestBody Alloc newAlloc) {
		Long id = newAlloc.getId();
		try {
			Alloc oldAlloc = resourceAllocation.findDataBy(id);
			if (oldAlloc == null)
				return new ResponseEntity<Alloc>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			System.out.println(e);
		}
		Alloc alloc = resourceAllocation.updateData(newAlloc);
		return new ResponseEntity<Alloc>(alloc, HttpStatus.OK);
	}

}
