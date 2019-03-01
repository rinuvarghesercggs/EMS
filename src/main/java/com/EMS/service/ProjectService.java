package com.EMS.service;

import java.util.ArrayList;
import java.util.List;

import com.EMS.model.ContractModel;
import com.EMS.model.DepartmentModel;
import com.EMS.model.ProjectModel;
import com.EMS.model.Resources;
import com.EMS.model.UserModel;

public interface ProjectService {

	
//		method declaration for saving new project record 
		ProjectModel save_project_record(ProjectModel project);

		List<String> getProjectsList();


		Long getProjectId(String projectName);

//		method declaration for getting user details for project creation
		List<String> getproject_owner();

//		ArrayList<ProjectModel> getProjects();
	
//		method declaration for getting contract type details for project creation
		ArrayList<ContractModel> getcontract_type();

//		Method invocation for getting department details
		List<DepartmentModel> getdepartment();



	
}

