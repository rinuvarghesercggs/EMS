package com.EMS.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.EMS.model.ContractModel;
import com.EMS.model.DepartmentModel;
import com.EMS.model.ProjectModel;
import com.EMS.model.Resources;
import com.EMS.service.ProjectService;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class ProjectController {
	
	@Autowired
	private ProjectService projectservice;
	
	
	
//	api for creating new project
	
	@PostMapping("/addProject")
	public JSONObject save_newproject(@RequestBody JSONObject requestdata) {
		
		
		JSONObject responsedata=new JSONObject();
		try {

//				setting values to object from json request 
				ProjectModel project=new ProjectModel();
				String contractId = requestdata.get("contract").toString();
				ContractModel contractModel = projectservice.getContract(Long.parseLong(contractId));
				project.setprojectDetails(requestdata.get("projectDetails").toString());
				project.setprojectName(requestdata.get("projectName").toString());
				project.setprojectOwner(requestdata.get("projectOwner").toString());
				project.setContract(contractModel);
				project.setestimatedHours(Integer.parseInt(requestdata.get("estimatedHours").toString()));
				String startdate=requestdata.get("startDate").toString();
				String enddate=requestdata.get("endDate").toString();
				
				DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
				Date date1 = formatter.parse(startdate);
				Date date2 = formatter.parse(enddate);
				
				project.setstartDate(date1);
				project.setendDate(date2);
				
//				Method invocation for creating new project record
				ProjectModel projectmodel=projectservice.save_project_record(project);
				
//				method invocation for storing resouces of project created
				String resource=requestdata.get("resources").toString();
				
//				json array for storing json array from request data				
				org.json.JSONArray jsonArray = new org.json.JSONArray(resource);

//				get totalCount of all jsonObjects
				int count = jsonArray.length(); 
				for(int i=0 ; i< count; i++){    
					
					org.json.JSONObject jsonObject = jsonArray.getJSONObject(i);  // get jsonObject @ i position 					
					
//					setting values on resource object					
					Resources resou1=new Resources();
					resou1.setProject(projectmodel.getId());
					String depart=jsonObject.getString("department");
					int count1=jsonObject.getInt("resourceCount");
					resou1.setDepartment(depart);
					resou1.setresourceCount(count1);
					
//					method invocation for storing resource details					
					Resources resourcevalue=projectservice.addprojectresouce(resou1);
					
					if(resourcevalue!=null)
						responsedata.put("status", "success");
					else
						responsedata.put("status", "Failed");
				}
				
		}catch(Exception e) {
			System.out.println("Exception : "+e);
			responsedata.put("status", "Failed");
		}
		return responsedata;
	}
	
	
	
	//Api for getting project owner details from user table
	
	@GetMapping(value="/getAdminFilterData")
	@ResponseBody
	public JSONObject getprojects(){
		
//		json object for passing response		
		JSONObject responsedata=new JSONObject();
		
//		Json array for storing filtered data from two tables
		ArrayList<JSONObject> userarray=new ArrayList<JSONObject>();
		JSONArray contract_array=new JSONArray();
		JSONArray department_array=new JSONArray();
		
//		json object for storing records array
		JSONObject array=new JSONObject();
		
		

		try {
//   			Method invocation for getting contract type 
				ArrayList<ContractModel> contract=projectservice.getcontractType();
				
				if(contract.isEmpty())
					array.put("contractType", contract_array);
				else {
					
//						Looping for storing data on json array				
						for(ContractModel cont : contract) {
							
//						json object for storing single record
							JSONObject contractobject=new JSONObject();
							
//							adding records to json object
							contractobject.put("id", cont.getId());
							contractobject.put("contractType", cont.getcontractType());
							System.out.println("id : "+cont.getId()+" type : "+cont.getcontractType());
//							adding records object to json array
							contract_array.add(contractobject);			
						}
						
//						storing records array to json object
						array.put("contractType", contract_array);
				}
				
				
//				Method invocation for getting users  with role as owner				
				List<String> users_owner=projectservice.getprojectOwner();
				
				if(users_owner.isEmpty())
					array.put("user_owner", userarray);
				else {
					
//					Looping for storing data on json array				
					for(String user : users_owner) {
						
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
				
//				Method invocation for getting users  with role as owner				
				List<DepartmentModel> department=projectservice.getdepartment();				
			
				if(department.isEmpty())
					array.put("department_resource", department_array);
				else {
					
//					Looping for storing data on json array				
					for(DepartmentModel dept : department) {
						
//					json object for storing single record
						JSONObject object=new JSONObject();
						
//						adding records to json object
						object.put("id", dept.getId());
						object.put("department", dept.getdepartmentName());
						
//						adding records object to json array
						department_array.add(object);			
					}
					
//					storing records array to json object
					array.put("department_resource", department_array);

					
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
