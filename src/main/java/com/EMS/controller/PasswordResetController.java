package com.EMS.controller;

import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.EMS.model.UserModel;
import com.EMS.service.LoginService;
import com.EMS.service.PasswordResetService;
import com.EMS.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@RequestMapping(value = "/user")
public class PasswordResetController {
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	LoginService login_service;
	
	@Autowired
	private PasswordResetService passwordResetService;
	
	@Autowired
    PasswordEncoder passwordEncoder;
	
	@PostMapping(value = "/resetPassword")
	@ResponseBody
	public JsonNode resetPassword(@RequestBody  ObjectNode requestdata, HttpServletRequest request) {
		ObjectNode response = objectMapper.createObjectNode();
		try {
	    	String userName = requestdata.get("userName").asText();
			UserModel user = userService.getUserByUserName(userName);
		    if (user == null) {
		    	response.put("status", "Failed");
		    	response.put("message", "Invalid User");
		    	return response;
		    }
		    if(user.getEmail() == null || user.getEmail().isEmpty()) {
		    	response.put("status", "Failed");
		    	response.put("message", "No email found.");
		    	return response;
		    }
		    String token = UUID.randomUUID().toString();
		    passwordResetService.createPasswordResetTokenForUser(user, token);
		    String msg = passwordResetService.sendMail(request.getRequestURL().toString(), request.getLocale(), token, user);
		    response.put("status", "success");
	    	response.put("message", msg);
		}
	    catch(Exception e) {
	    	response.put("status", "Failed");
	    	response.put("message", "Invalid User");
	    }
	    return response;
	}
	
	@GetMapping(value = "/validateResetPassword")
	public JsonNode showChangePasswordPage(Locale locale, Model model, 
		@RequestParam("userId") long id, @RequestParam("token") String token) {
		ObjectNode response = objectMapper.createObjectNode();
		try {
			String result = passwordResetService.validatePasswordResetToken(id, token);
			if (result != null) {
				response.put("status", "Failed");
				response.put("message", result);
			}
			else {
				response.put("status", "success");
				response.put("message", "Valid Token");
			}
		}
		catch(Exception e) {
			response.put("status", "Failed");
			response.put("message", "Exception " + e);
		}
		return response;
	}
	
	@PostMapping(value = "/savePassword")
	@ResponseBody
	public JsonNode savePassword(Locale locale, @RequestBody ObjectNode requestdata) {
		ObjectNode response = objectMapper.createObjectNode();
		try {
	    UserModel user = (UserModel) SecurityContextHolder.getContext()
	                                  .getAuthentication().getPrincipal();
	    String encPassword = this.passwordEncoder.encode(requestdata.get("newPassword").asText());
	    user.setPassword(encPassword);
	    login_service.adduser(user);
	    
	    response.put("status", "Success");
		response.put("message", "Password Changed Successfully");
		}
		catch (Exception e) {
			response.put("status", "Failed");
			response.put("message", "Password Updation Failed");
		}
	    return response;
	}
}
