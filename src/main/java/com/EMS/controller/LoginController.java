package com.EMS.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.EMS.model.DepartmentModel;
import com.EMS.model.UserModel;
import com.EMS.service.LoginService;


@RestController
public class LoginController {

	@Autowired
	LoginService login_service;
	
	
	// api call for admin login 
	
		@PostMapping(value="/login/admin")
		@ResponseBody
		public String adminLogin(UserModel user) {
			UserModel usercheck=login_service.login_authentication(user);
			
			if(usercheck==null)
				return "Invalid User";
			else
				return "Valid User";
		}
		
	//api call for viewing department records
	
		@GetMapping(value="/department")
		@ResponseBody
		public ArrayList<DepartmentModel> viewDepartment(){
			ArrayList<DepartmentModel> departmentarray=login_service.viewDepartments();
			
			return departmentarray;
		}
		
	
}
