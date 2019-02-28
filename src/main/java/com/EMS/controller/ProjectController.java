package com.EMS.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.EMS.model.ContractModel;
import com.EMS.model.ProjectModel;
import com.EMS.model.Resources;
import com.EMS.model.UserModel;
import com.EMS.service.ProjectService;

@RestController
public class ProjectController {
	
	@Autowired
	private ProjectService projectservice;
	
	
	
	//api for creating new project
	
	@PostMapping("/admin/project/saveproject")
	public void save_newproject(@RequestBody ProjectModel projectmodel) {

//		Method invocation for creating new project record
		ArrayList<Long> resouceindex=projectservice.save_project_record(projectmodel);	
		
	}
	
	
	
//	@GetMapping("/project")
//	public ArrayList<ProjectModel> getproject(){
//		ArrayList<ProjectModel> project=projectservice.getProjects();
//		return project;
//	}
//	
	//Api for getting project owner details from user table
	
	@GetMapping(value="/getAdminFilterData")
	@ResponseBody
	public JSONObject getprojects(){
		
		JSONObject responsedata=new JSONObject();
		
		try {
//			Method invocation for getting users  with role as owner
			ArrayList<UserModel> users_owner=projectservice.getproject_owner();
			
//			Method invocation for getting contract type 
			ArrayList<ContractModel> contract=projectservice.getcontract_type();
			
			JSONArray userarray=new JSONArray();
			JSONArray contract_array=new JSONArray();
			
			for(UserModel user:users_owner) {
				JSONObject userobject=new JSONObject();
				userobject.put("id", user.getId());
				userobject.put("firstName", user.getFirstName());
				userobject.put("lastName", user.getLastName());
				userarray.add(userobject);
			}
			
			for(ContractModel cont:contract) {
				JSONObject contractobject=new JSONObject();
				contractobject.put("id", cont.getId());
				contractobject.put("contrct_type", cont.getContrct_type());
				contract_array.add(contractobject);			
			}
			
			JSONObject array=new JSONObject();
			array.put("contract_type", contract_array);
			array.put("userarray", userarray);
			
			
			responsedata.put("status", "success");
			responsedata.put("data", array);
		}catch(Exception e) {
			responsedata.put("status", "Failed");
		}

		
		return responsedata;
	}

}
