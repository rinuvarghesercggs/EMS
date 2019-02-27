package com.EMS.controller;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EMS.model.ProjectModel;
import com.EMS.service.ProjectServiceInterface;

@RestController
@RequestMapping("/admin/project")
public class ProjectController {
	
	@Autowired
	private ProjectServiceInterface projectservice;
	
	//api for creating new project
	
	@GetMapping("/new")
	public ProjectModel get_project_creation_page() {
		ProjectModel model=new ProjectModel();
		
		return model;
	}
	
	@PostMapping("/saveproject")
	public void save_newproject(@RequestBody ProjectModel projectmodel) {
		
	
		
		System.out.println("project"+projectmodel.toString());
		projectservice.save_project_record(projectmodel);
	}

}
