package com.EMS.service;

import java.util.ArrayList;
import java.util.List;

import com.EMS.model.ContractModel;
import com.EMS.model.ProjectModel;
import com.EMS.model.Resources;
import com.EMS.model.UserModel;

public interface ProjectService {

	//method declaration for saving new project record 
	ArrayList<Long> save_project_record(ProjectModel projectmodel);

    List<String> getProjectsList();


	Long getProjectId(String projectName);

    //method declaration for getting user details for project creation
	ArrayList<UserModel> getproject_owner();

//	ArrayList<ProjectModel> getProjects();
	
//	method declaration for getting contract type details for project creation
	ArrayList<ContractModel> getcontract_type();



	
}

