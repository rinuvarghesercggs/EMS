package com.EMS.controller;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.EMS.model.UserModel;
import com.EMS.service.LoginService;

@RestController
@RequestMapping(value = "/login")
public class LoginController {

	@Autowired
	LoginService login_service;

// 		api call for admin login 

	@PostMapping(value = "/getLoginCredentials")
	@ResponseBody
	public JSONObject adminLogin(@RequestBody JSONObject requestdata, HttpServletResponse httpstatus) {

		JSONObject response = new JSONObject();
		JSONObject data = new JSONObject();

//				getting string value from json request
		String username = requestdata.get("username").toString();
		String password = requestdata.get("password").toString();

		try {

			if ((username != null) && (username.length() > 0) && (!username.equals(" ")) && (password != null)
					&& (password.length() > 0)) {

//						Invoking user authentication method 					
				UserModel usercheck = login_service.login_authentication(username, password);
				System.out.print("user :" + usercheck.getUserId());
				if (usercheck == null) {
//							Setting status on json object							
					response.put("status", "Failed");
					response.put("code", httpstatus.getStatus());
					response.put("message", "Invalid user");
					response.put("payload", "");
				} else {
//							Setting status on json object						
					response.put("status", "success");
					response.put("code", httpstatus.getStatus());
					response.put("message", "Valid user");
					data.put("username", usercheck.getUserName());
					data.put("userId", usercheck.getUserId());
					data.put("roleId", usercheck.getrole().getroleId());
					data.put("roleName", usercheck.getrole().getroleName());
				}
//						Setting data on json object
				response.put("payload", data);

			} else {
				response.put("status", "Failed");
				response.put("code", httpstatus.getStatus());
				response.put("message", "Invalid credientials");
				response.put("payload", "");
			}
//					

		} catch (Exception e) {
			System.out.println("Exception : " + e);
//					Setting status on json object
			response.put("status", "Failed");
			response.put("code", httpstatus.getStatus());
			response.put("message", "Exception " + e);
			response.put("payload", "");
		}
		return response;
	}

}
