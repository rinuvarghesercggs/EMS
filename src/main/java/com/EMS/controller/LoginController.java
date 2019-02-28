package com.EMS.controller;

import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.EMS.model.DepartmentModel;
import com.EMS.model.UserModel;
import com.EMS.service.LoginService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.util.JSONPObject;


@RestController
public class LoginController {

	@Autowired
	LoginService login_service;
	
	
	// api call for admin login 
	
		@PostMapping(value="/getLoginCredentials")
		@ResponseBody
		public JSONObject adminLogin(@RequestBody JSONObject requestdata) {
			
				JSONObject response=new JSONObject();
		
				String username=requestdata.get("username").toString();					//getting string value from json request
				String password=requestdata.get("password").toString();
			
				try {
					
					String usercheck=login_service.login_authentication(username,password);	//Invoking user authentication method 
					if(usercheck==null) 
						response.put("status", "Failed");									//Setting status on json object	
					else
						response.put("status", "success");									//Setting status on json object
						response.put("data", usercheck);									//Setting data on json object
				}catch(Exception e) {
					System.out.println("Exception : "+e);
					response.put("status", "Failed");										//Setting status on json object	
				}
			return response;
		}
		
	
}
