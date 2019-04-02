package com.EMS.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.EMS.model.DepartmentModel;
import com.EMS.model.RoleModel;
import com.EMS.model.Technology;
import com.EMS.model.UserModel;
import com.EMS.model.UserTechnology;
import com.EMS.service.LoginService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javax.xml.bind.DatatypeConverter;

@RestController
@RequestMapping(value = "/login")
public class LoginController {

	@Autowired
	LoginService login_service;

	@Autowired
	private ObjectMapper objectMapper;

	// api call for admin login

	@PostMapping(value = "/getLoginCredentials")
	@ResponseBody
	public JsonNode adminLogin(@RequestBody ObjectNode requestdata, HttpServletResponse httpstatus) {

		ObjectNode response = objectMapper.createObjectNode();
		ObjectNode data = objectMapper.createObjectNode();

		// getting string value from json request
		String username = requestdata.get("username").asText();
		String password = requestdata.get("password").asText();

		try {

			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			byte[] digest = md.digest();
			String encPassword = DatatypeConverter.printHexBinary(digest).toUpperCase();

			if ((username != null) && (username.length() > 0) && (!username.equals(" ")) && (encPassword != null)
					&& (encPassword.length() > 0)) {

				// Invoking user authentication method
				UserModel usercheck = login_service.login_authentication(username, encPassword);

				if (usercheck == null) {
					// Setting status on json object
					response.put("status", "Failed");
					response.put("code", httpstatus.getStatus());
					response.put("message", "Invalid user");
					response.put("payload", "");
				} else {

					response.put("status", "success");
					response.put("code", httpstatus.getStatus());
					response.put("message", "Valid user");
					data.put("username", usercheck.getUserName());
					data.put("userId", usercheck.getUserId());
					data.put("roleId", usercheck.getrole().getroleId());
					data.put("roleName", usercheck.getrole().getroleName());
					// Setting data on json object
					response.set("payload", data);
				}

			} else {
				response.put("status", "Failed");
				response.put("code", httpstatus.getStatus());
				response.put("message", "Invalid credientials");
				response.put("payload", "");
			}

		} catch (Exception e) {
			System.out.println("Exception : " + e);
			// Setting status on json object
			response.put("status", "Failed");
			response.put("code", httpstatus.getStatus());
			response.put("message", "Exception " + e);
			response.put("payload", "");
		}
		return response;
	}

//	//api call for registering new user
	@PostMapping(value = "/adduser")
	public JsonNode adduser(@RequestBody ObjectNode requestdata, HttpServletResponse servletresponse) {

		ObjectNode responsedata = objectMapper.createObjectNode();
		int responseflag = 0;
		// setting values to usermodel
		UserModel user = new UserModel();

		try {

			user.setFirstName(requestdata.get("firstName").asText());
			user.setLastName(requestdata.get("lastName").asText());
			user.setContact(requestdata.get("contact").asLong());
			user.setUserName(requestdata.get("userName").asText());
			user.setBloodGroup(requestdata.get("bloodGroup").asText());
			user.setGender(requestdata.get("gender").asInt());
			user.setEmploymentType(requestdata.get("employment").asText());
			user.setActive(requestdata.get("active").asBoolean());

			Long departId = requestdata.get("department").asLong();
			DepartmentModel department = null;
			if (departId != null)
				department = login_service.getDepartment(departId);

			if (department != null)
				user.setdepartment(department);
			else
				responseflag = 1;

			Long roleId = requestdata.get("role").asLong();
			RoleModel role = null;
			if (roleId != null)
				role = login_service.getRole(roleId);
			if (role != null)
				user.setrole(role);

			String dob = requestdata.get("dob").asText();
			String joindate = requestdata.get("joiningDate").asText();

			// Formatting the dates before storing
			DateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
			Date date1 = null, date2 = null;

			if (!dob.isEmpty()) {
				date2 = formatter.parse(dob);
				user.setDob(date2);

			}
			if (!joindate.isEmpty()) {
				date1 = formatter.parse(joindate);
				user.setJoiningDate(date1);

			}

			user.setEmpId(requestdata.get("empId").asLong());
			user.setEmail(requestdata.get("email").asText());
			
			 String password = requestdata.get("password").toString();
			 MessageDigest md = MessageDigest.getInstance("MD5");
			 md.update(password.getBytes());
			 byte[] digest = md.digest();
			 String encPassword = DatatypeConverter.printHexBinary(digest).toUpperCase();
			 user.setPassword(encPassword);  
			
			user.setQualification(requestdata.get("qualification").asText());
			UserModel userdata = login_service.adduser(user);

			if (userdata == null) {
				responseflag = 1;
				responsedata.put("message", "user record insertion failed");
			} else {

				// adding details in user technology
				ArrayNode usertechnology = (ArrayNode) requestdata.get("userTechnology");
				if (usertechnology.equals(null)) {

					responseflag = 1;
					responsedata.put("message", "Technology insertion failed");
				} else {
					for (JsonNode node : usertechnology) {

						// checking for technology using ID

						Long techId = node.get("technology").asLong();
						Technology technology = null;

						if (techId != null)
							technology = login_service.findtechnology(techId);

						UserTechnology usertech = new UserTechnology();
						if (technology != null)
							usertech.setTechnology(technology);
						else {
							responseflag = 1;
							responsedata.put("message",
									"user technology insertion failed due to missing technology value");
						}
						usertech.setUser(userdata);
						usertech.setExperience(node.get("experience").asDouble());
						UserTechnology userTechnology = login_service.addusertechnology(usertech);
						if (userTechnology == null)
							responseflag = 1;
					}

				}

			}

			if (responseflag == 0) {
				responsedata.put("status", "success");
				responsedata.put("message", "record insertion completed");
				responsedata.put("code", servletresponse.getStatus());
			} else {
				responsedata.put("status", "Failed");
				responsedata.put("code", servletresponse.getStatus());
			}

		} catch (Exception e) {
			responsedata.put("status", "Failed");
			responsedata.put("message", "Exception : " + e);
			responsedata.put("code", servletresponse.getStatus());
		}

		return responsedata;
	}
	
	
	@PostMapping(value="/change_password")
	public JSONObject changePassword(@RequestBody JSONObject requestdata, HttpServletResponse httpstatus) throws NoSuchAlgorithmException {
		JSONObject responsedata = new JSONObject();
		try {
			String userId = requestdata.get("userId").toString();			
			String password = requestdata.get("password").toString();		
			String newPassword = requestdata.get("newPassword").toString();		
			
			
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			byte[] digest = md.digest();
			String oldPassword = DatatypeConverter.printHexBinary(digest).toUpperCase();
			UserModel usercheck = login_service.changePasswordAuthentication(Long.parseLong(userId), oldPassword);
					
			if (usercheck!= null) {			
				md.update(newPassword.getBytes());
				digest = md.digest();
				String encPassword = DatatypeConverter.printHexBinary(digest).toUpperCase();
				usercheck.setPassword(encPassword);
				login_service.adduser(usercheck);
				responsedata.put("status", "success");
				responsedata.put("message", "Password Updated");
				responsedata.put("code", httpstatus.getStatus());
			}
			else {
				responsedata.put("status", "Failed");
				responsedata.put("message", "Password Not Matching");
				responsedata.put("code", httpstatus.getStatus());
			}			
		} catch (Exception e) {
			responsedata.put("status", "Failed");
			responsedata.put("message", "Exception : " + e);
			responsedata.put("code", httpstatus.getStatus());
		}

		return responsedata;
	}

}
