package com.timetrack.ti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timetrack.ti.model.ProjectModel;
import com.timetrack.ti.repository.ProjectRepositary;

@Service
public class ProjectServiceImpl implements ProjectServiceInterface{

	@Autowired
	ProjectRepositary project_repositary;
	
	@Override
	public void save_project_record(ProjectModel projectmodel) {
		
		ProjectModel model=project_repositary.save(projectmodel);
		
		
	}

	
}
