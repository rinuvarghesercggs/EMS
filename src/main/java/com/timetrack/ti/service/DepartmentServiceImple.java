package com.timetrack.ti.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timetrack.ti.model.DepartmentModel;
import com.timetrack.ti.repository.DepartmentRepository;
import com.timetrack.ti.service.DepartmentServiceInterface;

@Service
public class DepartmentServiceImple implements DepartmentServiceInterface {

	
	@Autowired
	DepartmentRepository department_repositary;
	
	
	@Override
	public void newDepartment(DepartmentModel department) {
		
		department_repositary.save(department);
		
	}

	@Override
	public ArrayList<DepartmentModel> viewDepartments() {
		
		ArrayList<DepartmentModel> departmentlist=(ArrayList<DepartmentModel>) department_repositary.findAll();
		return departmentlist;
	}

	@Override
	public DepartmentModel findDepartment(Long id) {
		
		Optional<DepartmentModel> department=department_repositary.findById(id);
		DepartmentModel model=department.get();
		
		if(model.getDepartment_name()==null)
			model=null;
		return model;
	}

	@Override
	public void deleteDepartment(Long id) {
		
		department_repositary.deleteById(id);
		
	}

	@Override
	public void getUpdate(DepartmentModel department) {
		department_repositary.save(department);
	}

}
