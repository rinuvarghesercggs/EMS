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

	// api for creating new project

	@PostMapping("/createProject")
	public JSONObject save_newproject(@RequestBody JSONObject requestdata, HttpServletResponse httpstatus) {

		JSONObject responsedata = new JSONObject();
		int responseflag = 0;
		try {

			// setting values to object from json request
			ProjectModel project = new ProjectModel();
			String contract = requestdata.get("contractType").toString();
			Long contractId = Long.parseLong(contract);
			ContractModel contractModel = new ContractModel();
			if ((contractId != null) && (!contractId.equals(" ")))
				contractModel = projectservice.getContract(contractId);
			project.setProjectDetails(requestdata.get("projectDetails").toString());
			project.setProjectName(requestdata.get("projectName").toString());
			project.setisBillable(Integer.parseInt(requestdata.get("isBillable").toString()));
			project.setProjectCode(requestdata.get("projectCode").toString());
			project.setprojectPhase(Integer.parseInt(requestdata.get("projectPhase").toString()));
			project.setprojectType(Integer.parseInt(requestdata.get("projectType").toString()));
			project.setprojectStatus(Integer.parseInt(requestdata.get("projectStatus").toString()));
			project.setisPOC(Integer.parseInt(requestdata.get("isPOC").toString()));

			Long userid = Long.parseLong(requestdata.get("projectOwner").toString());
			UserModel pro_owner = new UserModel();

			// method for getting userdetails using ID
			if (userid != null)
				pro_owner = userservice.getUserDetailsById(userid);

			if (pro_owner != null)
				project.setProjectOwner(pro_owner);

			if (contractModel != null)
				project.setContract(contractModel);

			project.setEstimatedHours(Integer.parseInt(requestdata.get("estimatedHours").toString()));
			String startdate = requestdata.get("startDate").toString();
			String enddate = requestdata.get("endDate").toString();

			// Formatting the dates before storing
			DateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
			Date date1 = null, date2 = null, releaseDate = null;
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
					&& (project.getprojectPhase()) >= 1) {
				// method invocation for checking duplicate entry for project name

				int result = projectservice.duplicationchecking(project.getProjectName());
				if (result == 0) {
					// Method invocation for creating new project record
					ProjectModel projectmodel = projectservice.save_project_record(project);
					// method invocation for storing resouces of project created
					String resource = requestdata.get("resources").toString();

					// json array for storing json array from request data
					org.json.JSONArray jsonArray = new org.json.JSONArray(resource);

					// get totalCount of all jsonObjects
					int count = jsonArray.length();
					for (int i = 0; i < count; i++) {

						org.json.JSONObject jsonObject = jsonArray.getJSONObject(i); // get jsonObject @ i position

						// setting values on resource object
						Resources resou1 = new Resources();
						if (projectmodel != null)
							resou1.setProject(projectmodel.getProjectId());

						Long depart = jsonObject.getLong("department");
						DepartmentModel department = new DepartmentModel();

						// method for getting department details
						if (depart != null)
							department = projectservice.getDepartmentDetails(depart);

						if (department != null)
							resou1.setDepartment(department);

						int count1 = jsonObject.getInt("resourceCount");
						resou1.setresourceCount(count1);

						// checking resouce model values before storing
						if ((resou1.getresourceCount() != 0) && (!resou1.getDepartment().equals(null))
								&& (resou1.getProject() != 0)) {

							// method invocation for storing resource details
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

			// setting values on response json
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

		// json object for passing response
		JSONObject responsedata = new JSONObject();

		// Json array for storing filtered data from two tables
		ArrayList<JSONObject> userarray = new ArrayList<JSONObject>();
		JSONArray contract_array = new JSONArray();
		JSONArray department_array = new JSONArray();
		JSONArray project_array = new JSONArray();

		// json object for storing records array
		JSONObject array = new JSONObject();

		try {

			// method invocation for getting project details
			List<ProjectModel> projectlist = projectservice.getProjectList();
			if (projectlist.isEmpty())
				array.put("projectName", project_array);
			else {
				for (ProjectModel projectdata : projectlist) {
					JSONObject projectobject = new JSONObject();

					projectobject.put("projectId", projectdata.getProjectId());
					projectobject.put("projectName", projectdata.getProjectName());
					project_array.add(projectobject);
				}
				array.put("projectName", project_array);
			}

			// Method invocation for getting contract type
			ArrayList<ContractModel> contract = projectservice.getcontractType();

			if (contract.isEmpty())
				array.put("contractType", contract_array);
			else {

				// Looping for storing data on json array
				for (ContractModel cont : contract) {

					// json object for storing single record
					JSONObject contractobject = new JSONObject();

					// adding records to json object
					contractobject.put("contractTypeId", cont.getContractTypeId());
					contractobject.put("contractTypeName", cont.getContractTypeName());
					// adding records object to json array
					contract_array.add(contractobject);
				}

				// storing records array to json object
				array.put("contractType", contract_array);
			}

			// Method invocation for getting users with role as owner
			List<UserModel> users_owner = projectservice.getprojectOwner();

			if (users_owner.isEmpty())
				array.put("user_owner", userarray);
			else {

				Iterator<UserModel> itr = users_owner.listIterator();
				// Looping for storing data on json array
				while (itr.hasNext()) {

					// json object for storing single record
					JSONObject object = new JSONObject();

					// adding records to json object
					UserModel user = itr.next();
					object.put("firstName", user.getFirstName());
					object.put("id", user.getUserId());

					// adding records object to json array
					userarray.add(object);
				}

				// storing records array to json object
				array.put("user_owner", userarray);

			}

			// Method invocation for getting users with role as owner
			List<DepartmentModel> department = projectservice.getdepartment();

			if (department.isEmpty())
				array.put("department_resource", department_array);
			else {

				// Looping for storing data on json array
				for (DepartmentModel dept : department) {

					// json object for storing single record
					JSONObject object = new JSONObject();

					// adding records to json object
					object.put("departmentId", dept.getDepartmentId());
					object.put("department", dept.getdepartmentName());

					// adding records object to json array
					department_array.add(object);
				}

				// storing records array to json object
				array.put("department_resource", department_array);

			}

			// storing data on response object
			responsedata.put("status", "success");
			responsedata.put("data", array);
			return responsedata;
		} catch (Exception e) {
			System.out.println("Exception " + e);
			return responsedata;
		}

	}

	// api for project list
	@GetMapping(value = "/viewProjects")
	public JSONObject getProjectsList(HttpServletResponse statusResponse) {
		JSONObject responsedata = new JSONObject();
		JSONArray projectArray = new JSONArray();

		try {
			ArrayList<ProjectModel> projectlist = projectservice.getListofProjects();
			System.out.println("length" + projectlist.size());

			if (projectlist.isEmpty()) {
				responsedata.put("status", "success");
				responsedata.put("message", "No Records Available");
				responsedata.put("code", statusResponse.getStatus());
				responsedata.put("payload", "");
			} else {
				
				System.out.println("else");
				for (ProjectModel obj : projectlist) {
					System.out.println("for");
					JSONObject jsonobj = new JSONObject();
					jsonobj.put("projectId", obj.getProjectId());
					jsonobj.put("projectName", obj.getProjectName());
					jsonobj.put("projectDetails", obj.getProjectDetails());
					jsonobj.put("estimatedHours", obj.getEstimatedHours());
					jsonobj.put("startDate", obj.getStartDate());
					jsonobj.put("endDate", obj.getEndDate());
					jsonobj.put("isBillable", obj.getisBillable());
					jsonobj.put("projectCode", obj.getProjectCode());
					jsonobj.put("projectPhase", obj.getprojectPhase());
					jsonobj.put("projectType", obj.getprojectType());
					jsonobj.put("projectOwner", obj.getProjectOwner());
					jsonobj.put("releasingDate", obj.getReleasingDate());
					jsonobj.put("isPOC", obj.getisPOC());
					jsonobj.put("projectStatus", obj.getprojectStatus());
					System.out.println("for sec1");
					Long contractId = obj.getContract().getContractTypeId();
					System.out.println("contract"+contractId);
					ContractModel contract=null;
					if(contractId!=null)
						contract = projectservice.getContract(contractId);
					JSONObject contractobj = new JSONObject();
					if(contract==null)
						contractobj=null;
					else {
						System.out.println("for sec2");
						contractobj.put("contractTypeId", contract.getContractTypeId());
						contractobj.put("contractTypeName", contract.getContractTypeName());
						System.out.println("for sec3");
					}
					jsonobj.put("contractType", contractobj);

					Long userid = obj.getProjectOwner().getUserId();
					System.out.println("user "+userid);
					UserModel userdata=null;
					if(userid!=null)
						userdata = projectservice.getuser(userid);
					JSONObject userobj = new JSONObject();
					
					if(userdata==null)
						userobj=null;
					else {
						userobj.put("userId", userdata.getUserId());
						userobj.put("userName", userdata.getFirstName() + " " + userdata.getLastName());
						System.out.println("for sec4");
					}
					jsonobj.put("projectOwner", userobj);

					List<Resources> resourcelist = projectservice.getResourceList(obj.getProjectId());
					JSONArray resourceArray = new JSONArray();
					System.out.println("for sec5 "+resourcelist.size());
					
					if(resourcelist.isEmpty())
						jsonobj.put("resource", resourceArray);
					else {
						System.out.println("for sec6");
						for (Resources resource : resourcelist) {
							System.out.println("for 2");
							JSONObject resourceobj = new JSONObject();
							resourceobj.put("resourceId", resource.getResourceId());
							resourceobj.put("resourceCount", resource.getresourceCount());
							resourceobj.put("project", resource.getProject());

							DepartmentModel department = projectservice
									.getDepartmentDetails(resource.getDepartment().getDepartmentId());
							JSONObject departmentobj = new JSONObject();
							
							if(department==null)
								departmentobj=null;
							else {
								departmentobj.put("departmentId", department.getDepartmentId());
								departmentobj.put("departmentName", department.getdepartmentName());
							}
							
							resourceobj.put("department", departmentobj);

							resourceArray.add(resourceobj);
						}
						jsonobj.put("resource", resourceArray);
					}
					
					projectArray.add(jsonobj);
				}
				responsedata.put("status", "success");
				responsedata.put("message", "success");
				responsedata.put("code", statusResponse.getStatus());
				responsedata.put("payload", projectArray);

			}

		} catch (Exception e) {
			System.out.println("Exception : " + e);
			responsedata.put("status", "failed");
			responsedata.put("message", "Exception : " + e);
			responsedata.put("code", statusResponse.getStatus());
		}

		return responsedata;
	}

}
