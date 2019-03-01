package com.EMS.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Service;

import com.EMS.model.ContractModel;
import com.EMS.model.DepartmentModel;
import com.EMS.model.ProjectModel;
import com.EMS.model.Resources;
import com.EMS.model.UserModel;
import com.EMS.repository.ContractRepositary;
import com.EMS.repository.DepartmentRepository;
import com.EMS.repository.ProjectRepository;
import com.EMS.repository.ResourceRepository;
import com.EMS.repository.UserRepositary;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	ProjectRepository project_repositary;

	@Autowired
	UserRepositary user_repositary;

	@Autowired
	ResourceRepository resource_repository;

	@Autowired
	ContractRepositary contract_repository;

	@Autowired
	DepartmentRepository department_repositary;

	@Override
	public ProjectModel save_project_record(ProjectModel projectmodel) {

		ProjectModel model = project_repositary.save(projectmodel);
		return model;
	}

	@Override
	public ProjectModel findById(Long id) {
		ProjectModel model = project_repositary.getOne(id);
		return model;
	}

	@Override
	public List<String> getProjectsList() {
		List<String> nameList = project_repositary.getProjectName();
		return nameList;
	}

	@Override
	public Long getProjectId(String projectName) {
		Long pid = project_repositary.getProjectId(projectName);
		return pid;
	}

	@Override
	public List<String> getproject_owner() {
		List<String> user_owner = new ArrayList<String>();
		user_owner = user_repositary.getProjectOwners();

		return user_owner;
	}

//	@Override
//	public ArrayList<ProjectModel> getProjects() {
//		ArrayList<ProjectModel> project=(ArrayList<ProjectModel>) project_repositary.findAll();
//		return project;
//	}

	@Override
	public ArrayList<ContractModel> getcontract_type() {

		ArrayList<ContractModel> contract = null;
		try {
//			getting contract type records 
			contract = (ArrayList<ContractModel>) contract_repository.findAll();

			return contract;
		} catch (Exception e) {
			System.out.println("contract 1 Exception : " + e);
			return contract;
		}

	}

	@Override
	public List<DepartmentModel> getdepartment() {
		List<DepartmentModel> department = department_repositary.findAll();
		return department;
	}

}
