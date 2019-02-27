package com.EMS.controller;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.EMS.model.DepartmentModel;
import com.EMS.service.DepartmentService;


@RestController
@RequestMapping("/admin/department")
public class DepartmentController {

	@Autowired
	private DepartmentService department_service;

	
//	@RequestMapping(method=RequestMethod.GET,value="/newDepartment")
	
	@GetMapping(value="/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DepartmentModel> getSingleDepartment(@PathVariable("id") long id) {
		System.out.println("id"+id);
		DepartmentModel department=department_service.findDepartment(id);
		System.out.println("departid"+department.getDepartment_name()+"."+department.getId());
		
		return new ResponseEntity<DepartmentModel>(department, HttpStatus.OK);
	}
	

	//api call for saving new department data
	
	@RequestMapping(method=RequestMethod.POST,value="/saveDepartment")
	public void saveDepartment(@RequestBody DepartmentModel departmentModel) {
		department_service.newDepartment(departmentModel);
	}
	
	//api call for viewing department records
	
	@RequestMapping(method=RequestMethod.GET,value="/viewall")
	@ResponseBody
	public ArrayList<DepartmentModel> viewDepartment(){
		ArrayList<DepartmentModel> departmentarray=department_service.viewDepartments();
		
		return departmentarray;
	}
	
	//api call for finding a single department by ID
	
	@RequestMapping(method=RequestMethod.GET,value="/viewdepartment/{id}")
	
	public DepartmentModel getDepartment(@PathVariable("id") Long id) {
		DepartmentModel department=department_service.findDepartment(id);
		return department;
	}
	
	// api call for deleting a with ID
	
	//@RequestMapping(method=RequestMethod.DELETE,value="/deletedepartment/{id}")
	@DeleteMapping(value="/deletedepartment/{id}")
	public ResponseEntity<Void> deleteDepartment(@PathVariable("id") Long id) {
		
		DepartmentModel model=department_service.findDepartment(id);
		if(model==null)
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		department_service.deleteDepartment(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
		
	}
	
	@PutMapping("/updateDepartment")
	public ResponseEntity<Void> updateDepartment(@RequestBody DepartmentModel department) {
		
		DepartmentModel model=department_service.findDepartment(department.getId());
		
		if(model==null)
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		
		department_service.getUpdate(department);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	
	
}
