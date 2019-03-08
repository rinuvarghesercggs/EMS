package com.EMS.controller;


import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.EMS.model.UserModel;
import com.EMS.service.LoginService;


@RestController
@RequestMapping(value="/login")
public class LoginController {

	@Autowired
	LoginService login_service;
	
	
// 		api call for admin login 
	
		@PostMapping(value="/getLoginCredentials")
		@ResponseBody
		public JSONObject adminLogin(@RequestBody JSONObject requestdata) {
			
				JSONObject response=new JSONObject();
				JSONObject data=new JSONObject();
				
//				getting string value from json request
				String username=requestdata.get("username").toString();					
				String password=requestdata.get("password").toString();
			
				try {
//					Invoking user authentication method 					
					UserModel usercheck=login_service.login_authentication(username,password);	
					System.out.print("user :"+usercheck.getId());
					if(usercheck==null) 
//						Setting status on json object							
						response.put("status", "Failed");									
					else {
//						Setting status on json object						
						response.put("status", "success");
						
						data.put("username", usercheck.getUserName());
						data.put("id", usercheck.getId());
						data.put("role", usercheck.getrole().getroleId());
						data.put("roleName", usercheck.getrole().getroleName());
					}
//						Setting data on json object
						response.put("data", data);									
				}catch(Exception e) {
					System.out.println("Exception : "+e);
//					Setting status on json object
					response.put("status", "Failed");											
				}
			return response;
		}
		
	
}
