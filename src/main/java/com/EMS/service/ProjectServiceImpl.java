package com.EMS.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EMS.model.ProjectModel;
import com.EMS.model.UserModel;
import com.EMS.repository.ProjectRepositary;
import com.EMS.repository.UserRepositary;

@Service
public class ProjectServiceImpl implements ProjectService{

	@Autowired
	ProjectRepositary project_repositary;
	
	@Autowired
	UserRepositary user_repositary;
	
	@Override
	public void save_project_record(ProjectModel projectmodel) {		
		
		ProjectModel model=project_repositary.save(projectmodel);		//Saving project record by using repositary instance
		
		
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
	public ArrayList<UserModel> getproject_owner() {
		
		ArrayList<UserModel> user_owner=null;		//initilizing the usermodel array
		
		try {
			user_owner=user_repositary.getProjectOwners();	//invoking the query through repositary instance
			return user_owner;
		}catch(Exception e) {
			System.out.println("Exception : "+e);
			return user_owner;
		}
		
		
	}



}
