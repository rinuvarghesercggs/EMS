package com.EMS.service;

import java.util.ArrayList;

import com.EMS.model.DepartmentModel;

public interface DepartmentService {

	// For creating new  department
	public void newDepartment(DepartmentModel department);
	
	//For getting details of all departments
	public ArrayList<DepartmentModel> viewDepartments();
	
	//For finding the department details with ID 
	public DepartmentModel findDepartment(Long id);
	
	// For deleting a single record using ID
	public void deleteDepartment(Long id);
	
	//For updating department details
	public void getUpdate(DepartmentModel department);
}
