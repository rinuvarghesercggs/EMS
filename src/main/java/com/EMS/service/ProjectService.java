package com.EMS.service;

<<<<<<< HEAD
import java.util.ArrayList;
import java.util.List;

import com.EMS.model.ProjectModel;
import com.EMS.model.UserModel;

public interface ProjectService {

	//method declaration for saving new project record 
	void save_project_record(ProjectModel projectmodel);

    List<String> getProjectsList();


	Long getProjectId(String projectName);

    //method declaration for getting user details for project creation
	ArrayList<UserModel> getproject_owner();

	ArrayList<ProjectModel> getProjects();

	
}

=======
import com.EMS.model.ProjectModel;

public interface ProjectService {

	//method for saving
	void save_project_record(ProjectModel projectmodel);

	
}
>>>>>>> branch 'PMS_DEV' of https://github.com/Haritha-Ti/EMS.git
