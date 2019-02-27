package com.EMS.service;

import java.util.List;

import com.EMS.model.ProjectModel;

public interface ProjectService {

	//method for saving
	void save_project_record(ProjectModel projectmodel);

    List<String> getProjectsList();
}
