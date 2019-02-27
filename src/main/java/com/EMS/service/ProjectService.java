package com.EMS.service;

import java.util.ArrayList;
import java.util.List;

import com.EMS.model.ProjectModel;
import com.EMS.model.UserModel;

public interface ProjectService {

	//method declaration for saving new project record 
	void save_project_record(ProjectModel projectmodel);

    List<String> getProjectsList();

    //method declaration for getting user details for project creation
	ArrayList<UserModel> getproject_owner();

	
}
