package com.EMS.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.EMS.model.ProjectModel;
import com.EMS.model.UserModel;
import com.EMS.service.ProjectService;

@RestController
@RequestMapping("/admin/project")
public class ProjectController {
	
	@Autowired
	private ProjectService projectservice;
	
	
	
	//api for creating new project
	
	@PostMapping("/saveproject")
	public void save_newproject(@RequestBody ProjectModel projectmodel) {
	
		projectservice.save_project_record(projectmodel);	//Method invocation for creating new project record
		
	}
	
	
	
	@GetMapping("/project")
	public ArrayList<ProjectModel> getproject(){
		ArrayList<ProjectModel> project=projectservice.getProjects();
		return project;
	}
	
	//Api for getting project owner details from user table
	
	@GetMapping(value="/project_owner")
	@ResponseBody
	public ArrayList<UserModel> getprojects(){
		ArrayList<UserModel> users_owner=projectservice.getproject_owner();	//Method invocation for getting users  with role as owner
			
		return users_owner;
	}

}
