package com.EMS.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
	
//	@PostMapping("/admin/project/saveproject")
//	public void save_newproject(@RequestBody ProjectModel projectmodel) {
//
////		Method invocation for creating new project record
//		ArrayList<Long> resouceindex=projectservice.save_project_record(projectmodel);	
//		
//	}
	
	
	
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
		
//		json object for passing response		
		JSONObject responsedata=new JSONObject();
		
//		Json array for storing filtered data from two tables
		ArrayList<JSONObject> userarray=new ArrayList<JSONObject>();
		JSONArray contract_array=new JSONArray();
		
//		json object for storing records array
		JSONObject array=new JSONObject();
		
		

		try {
//   			Method invocation for getting contract type 
				ArrayList<ContractModel> contract=projectservice.getcontract_type();
				
				if(contract.isEmpty())
					array.put("contract_type", contract_array);
				else {
					
//						Looping for storing data on json array				
						for(ContractModel cont : contract) {
							
//						json object for storing single record
							JSONObject contractobject=new JSONObject();
							
//							adding records to json object
							contractobject.put("id", cont.getId());
							contractobject.put("contrct_type", cont.getContrct_type());
							System.out.println("id : "+cont.getId()+" type : "+cont.getContrct_type());
//							adding records object to json array
							contract_array.add(contractobject);			
						}
						
//						storing records array to json object
						array.put("contract_type", contract_array);
				}
				
				
//				Method invocation for getting users  with role as owner				
				List<String> users_owner=projectservice.getproject_owner();
				
				if(users_owner.isEmpty())
					array.put("user_owner", userarray);
				else {
					System.out.println("1.2");
//					Looping for storing data on json array				
					for(String user : users_owner) {
						System.out.println("1.2");	
//					json object for storing single record
						JSONObject object=new JSONObject();
						
//						adding records to json object
						object.put("firstname", user);
					
//						adding records object to json array
						userarray.add(object);			
					}
					
//					storing records array to json object
					array.put("user_owner", userarray);

					
				}
			
//				storing data on response object
				responsedata.put("status", "success");
				responsedata.put("data", array);
			return responsedata;
		}catch(Exception e) {
			System.out.println("Exception "+e);
			return responsedata;
		}
	
	}

}
