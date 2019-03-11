package com.EMS.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
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
import com.EMS.model.UserModel;
import com.EMS.service.ProjectService;
import com.EMS.service.UserService;

@RestController
@RequestMapping(value = "/project")
public class ProjectController {

	@Autowired
	private ProjectService projectservice;

	@Autowired
	private UserService userservice;

//	api for creating new project

	@PostMapping("/addProject")
	public JSONObject save_newproject(@RequestBody JSONObject requestdata, HttpServletResponse httpstatus) {

		JSONObject responsedata = new JSONObject();
		int responseflag = 0;
		try {

//				setting values to object from json request 
			ProjectModel project = new ProjectModel();
			String contract = requestdata.get("contract").toString();
			Long contractId = Long.parseLong(contract);
			ContractModel contractModel = new ContractModel();
			if ((contractId != null) && (!contractId.equals(" ")))
				contractModel = projectservice.getContract(contractId);
			project.setProjectDetails(requestdata.get("projectDetails").toString());
			project.setProjectName(requestdata.get("projectName").toString());
			project.setBillable(Integer.parseInt(requestdata.get("billable").toString()));
			project.setProjectCode(requestdata.get("projectCode").toString());
			project.setPhase(Integer.parseInt(requestdata.get("phase").toString()));
			project.setType(Integer.parseInt(requestdata.get("type").toString()));

			Long userid = Long.parseLong(requestdata.get("projectOwner").toString());
			UserModel pro_owner = new UserModel();
			if (userid != null)
				pro_owner = userservice.getUserDetailsById(userid);

			if (pro_owner != null)
				project.setProjectOwner(pro_owner);

			if (contractModel != null)
				project.setContract(contractModel);

			project.setEstimatedHours(Integer.parseInt(requestdata.get("estimatedHours").toString()));
			String startdate = requestdata.get("startDate").toString();
			String enddate = requestdata.get("endDate").toString();

			DateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
			Date date1 = null, date2 = null;
			if (!startdate.isEmpty()) {
				date1 = formatter.parse(startdate);
				project.setStartDate(date1);
			}

			if (!enddate.isEmpty()) {
				date2 = formatter.parse(enddate);
				project.setEndDate(date2);

			}

			if ((project.getProjectDetails() != null) && (project.getProjectDetails().length() > 0)
					&& (!project.getProjectDetails().equals(" ")) && (project.getProjectName() != null)
					&& (!project.getProjectName().equals(" ")) && (project.getProjectName().length() > 0)
					&& (project.getProjectCode() != null)
					&& (!project.getProjectCode().equals(" ") && (project.getProjectCode().length() > 0))
					&& (project.getPhase()) >= 1) {

//					method invocation for checking duplicate entry for project name

				int result = projectservice.duplicationchecking(project.getProjectName());
				if (result == 0) {

//						Method invocation for creating new project record
					ProjectModel projectmodel = projectservice.save_project_record(project);
//						method invocation for storing resouces of project created
					String resource = requestdata.get("resources").toString();

//						json array for storing json array from request data				
					org.json.JSONArray jsonArray = new org.json.JSONArray(resource);

//						get totalCount of all jsonObjects
					int count = jsonArray.length();
					for (int i = 0; i < count; i++) {

						org.json.JSONObject jsonObject = jsonArray.getJSONObject(i); // get jsonObject @ i position

//							setting values on resource object					
						Resources resou1 = new Resources();
						if (projectmodel != null)
							resou1.setProject(projectmodel.getProjectId());

						Long depart = jsonObject.getLong("department");
						DepartmentModel department = new DepartmentModel();
						if (depart != null)
							department = projectservice.getDepartmentDetails(depart);

						if (department != null)
							resou1.setDepartment(department);

						int count1 = jsonObject.getInt("resourceCount");
						resou1.setresourceCount(count1);

						if ((resou1.getresourceCount() != 0) && (!resou1.getDepartment().equals(null))
								&& (resou1.getProject() != 0)) {

//							method invocation for storing resource details					
							Resources resourcevalue = projectservice.addprojectresouce(resou1);

							if (resourcevalue == null)
								responseflag = 1;
						} else
							responseflag = 1;

					}

				} else {
					responseflag = 1;
				}

			} else {
				responseflag = 1;
			}

			if (responseflag == 0) {
				responsedata.put("status", "success");
				responsedata.put("code", httpstatus.getStatus());
				responsedata.put("message", "Record Inserted");
				responsedata.put("payload", "");
			} else {
				responsedata.put("status", "Failed");
				responsedata.put("code", httpstatus.getStatus());
				responsedata.put("message", "Insertion failed due to invalid credientials");
				responsedata.put("payload", "");
			}

		} catch (Exception e) {
			System.out.println("Exception : " + e);
			responsedata.put("status", "Failed");
			responsedata.put("code", httpstatus.getStatus());
			responsedata.put("message", "Exception " + e);
			responsedata.put("payload", "");
		}
		return responsedata;
	}

	// Api for getting project owner details from user table

	@GetMapping(value = "/getAdminFilterData")
	@ResponseBody
	public JSONObject getprojects() {

//		json object for passing response		
		JSONObject responsedata = new JSONObject();

//		Json array for storing filtered data from two tables
		ArrayList<JSONObject> userarray = new ArrayList<JSONObject>();
		JSONArray contract_array = new JSONArray();
		JSONArray department_array = new JSONArray();

//		json object for storing records array
		JSONObject array = new JSONObject();

		try {
//   			Method invocation for getting contract type 
			ArrayList<ContractModel> contract = projectservice.getcontractType();

			if (contract.isEmpty())
				array.put("contractType", contract_array);
			else {

//						Looping for storing data on json array				
				for (ContractModel cont : contract) {

//						json object for storing single record
					JSONObject contractobject = new JSONObject();

//							adding records to json object
					contractobject.put("contractTypeId", cont.getContractTypeId());
					contractobject.put("contractTypeName", cont.getContractTypeName());
//							adding records object to json array
					contract_array.add(contractobject);
				}

//						storing records array to json object
				array.put("contractType", contract_array);
			}

//				Method invocation for getting users  with role as owner				
			List<UserModel> users_owner = projectservice.getprojectOwner();

			if (users_owner.isEmpty())
				array.put("user_owner", userarray);
			else {

				Iterator<UserModel> itr = users_owner.listIterator();
//					Looping for storing data on json array				
				while (itr.hasNext()) {

//					json object for storing single record
					JSONObject object = new JSONObject();

//						adding records to json object
					UserModel user = itr.next();
					object.put("firstName", user.getFirstName());
					object.put("id", user.getUserId());

//						adding records object to json array
					userarray.add(object);
				}

//					storing records array to json object
				array.put("user_owner", userarray);

			}

//				Method invocation for getting users  with role as owner				
			List<DepartmentModel> department = projectservice.getdepartment();

			if (department.isEmpty())
				array.put("department_resource", department_array);
			else {

//					Looping for storing data on json array				
				for (DepartmentModel dept : department) {

//					json object for storing single record
					JSONObject object = new JSONObject();

//						adding records to json object
					object.put("departmentId", dept.getDepartmentId());
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
		} catch (Exception e) {
			System.out.println("Exception " + e);
			return responsedata;
		}

	}

}
