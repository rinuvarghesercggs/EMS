package com.EMS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/admin")
public class AdminLoginController {

	
	
	
	@RequestMapping(method=RequestMethod.GET,value="/login")
	@ResponseBody
	public String adminLogin() {
		return "hai";
	}
	
}
