package com.EMS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EMS.model.ProjectModel;
import com.EMS.repository.ProjectRepositary;

@Service
public class ProjectServiceImpl implements ProjectService{

	@Autowired
	ProjectRepositary project_repositary;
	
	@Override
	public void save_project_record(ProjectModel projectmodel) {
		
		ProjectModel model=project_repositary.save(projectmodel);
		
		
	}

	@Override
	public List<String> getProjectsList() {
		List<String> nameList = project_repositary.getProjectName();
		return nameList;
	}

}
