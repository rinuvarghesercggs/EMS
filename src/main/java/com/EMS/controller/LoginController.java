package com.EMS.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

@RestController
@RequestMapping(value = "/login")
public class LoginController {

	@Autowired
	LoginService login_service;

	// api call for admin login

	@PostMapping(value = "/getLoginCredentials")
	@ResponseBody
	public JSONObject adminLogin(@RequestBody JSONObject requestdata, HttpServletResponse httpstatus) {

		JSONObject response = new JSONObject();
		JSONObject data = new JSONObject();

		// getting string value from json request
		String username = requestdata.get("username").toString();
		String password = requestdata.get("password").toString();

		try {

			if ((username != null) && (username.length() > 0) && (!username.equals(" ")) && (password != null)
					&& (password.length() > 0)) {

				// Invoking user authentication method
				UserModel usercheck = login_service.login_authentication(username, password);
				System.out.print("user :" + usercheck.getUserId());
				if (usercheck == null) {
					// Setting status on json object
					response.put("status", "Failed");
					response.put("code", httpstatus.getStatus());
					response.put("message", "Invalid user");
					response.put("payload", "");
				} else {
					// Setting status on json object
					response.put("status", "success");
					response.put("code", httpstatus.getStatus());
					response.put("message", "Valid user");
					data.put("username", usercheck.getUserName());
					data.put("userId", usercheck.getUserId());
					data.put("roleId", usercheck.getrole().getroleId());
					data.put("roleName", usercheck.getrole().getroleName());
				}
				// Setting data on json object
				response.put("payload", data);

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
	public JSONObject adduser(@RequestBody JSONObject requestdata, HttpServletResponse servletresponse) {

		JSONObject responsedata = new JSONObject();
		int responseflag = 0;
		// setting values to usermodel
		UserModel user = new UserModel();

		try {

			user.setFirstName(requestdata.get("firstName").toString());
			user.setLastName(requestdata.get("lastName").toString());
			user.setContact(Long.parseLong(requestdata.get("contact").toString()));
			user.setUserName(requestdata.get("userName").toString());
			user.setBloodGroup(requestdata.get("bloodGroup").toString());
			user.setGender(Integer.parseInt(requestdata.get("gender").toString()));
			user.setEmploymentType(requestdata.get("employment").toString());
			user.setActive(Boolean.parseBoolean(requestdata.get("active").toString()));

			Long departId=Long.parseLong(requestdata.get("department").toString());
			DepartmentModel department=null;
			if(departId!=null)
				department = login_service.getDepartment(departId);
			
			
			if (department != null)
				user.setdepartment(department);
			else
				responseflag = 1;
			
			Long roleId=Long.parseLong(requestdata.get("role").toString());
			RoleModel role=null;
			if(roleId!=null)
				role = login_service.getRole(roleId);
			if (role != null) 
				user.setrole(role);
			
			String dob = requestdata.get("dob").toString();
			String joindate = requestdata.get("joiningDate").toString();

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

			user.setEmpId(Integer.parseInt(requestdata.get("empId").toString()));
			user.setEmail(requestdata.get("email").toString());
			user.setPassword(requestdata.get("password").toString());
			user.setQualification(requestdata.get("qualification").toString());
			UserModel userdata = login_service.adduser(user);

			if (userdata == null)
				responseflag = 1;

			// adding details in user technology
			String usertechnology = requestdata.get("userTechnology").toString();
			org.json.JSONArray usertecharray = new org.json.JSONArray(usertechnology);
			int length = usertecharray.length();
			for (int i = 0; i < length; i++) {
				
				org.json.JSONObject technologyobj = usertecharray.getJSONObject(i);
				// checking for technology using ID

				Long techId = Long.parseLong(technologyobj.get("technology").toString());
				Technology technology = null;

				if (techId != null)
					technology = login_service.findtechnology(techId);
				else
					responseflag = 1;

				UserTechnology usertech = new UserTechnology();
				if (technology != null) 
					usertech.setTechnology(technology);
				 else
					responseflag = 1;
				

				usertech.setUser(userdata);
				usertech.setExperience(Double.parseDouble(technologyobj.get("experience").toString()));
				UserTechnology userTechnology = login_service.addusertechnology(usertech);
				if (userTechnology == null)
					responseflag = 1;
			}

			if (responseflag == 0) {
				responsedata.put("status", "success");
				responsedata.put("message", "record insertion completed");
				responsedata.put("code", servletresponse.getStatus());
			} else {
				responsedata.put("status", "Failed");
				responsedata.put("message", "record insertion failed");
				responsedata.put("code", servletresponse.getStatus());
			}

		} catch (Exception e) {
			responsedata.put("status", "Failed");
			responsedata.put("message", "Exception : " + e);
			responsedata.put("code", servletresponse.getStatus());
		}

		return responsedata;
	}

	// api for user view
//	@GetMapping(value="/viewuser")
//	public ArrayList<UserModel> viewAllUser() {
//		JSONObject responsedata=new JSONObject();
//		JSONArray userjsonarray=new JSONArray();
//		
//		ArrayList<UserModel> userarray=login_service.getAllUser();
//		
//		for(UserModel user:userarray) {
//			JSONObject userobject=new JSONObject();
//			
//		}
//		
//		return userarray;
//	}

}
