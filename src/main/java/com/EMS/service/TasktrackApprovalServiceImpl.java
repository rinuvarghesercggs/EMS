package com.EMS.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.EMS.model.AllocationModel;
import com.EMS.model.ProjectModel;
import com.EMS.model.Task;
import com.EMS.model.TaskTrackApproval;
import com.EMS.model.Tasktrack;
import com.EMS.repository.ProjectReportsRepository;
import com.EMS.repository.TaskRepository;
import com.EMS.repository.TasktrackRepository;
import com.EMS.repository.TimeTrackApprovalJPARepository;
import com.EMS.repository.TimeTrackApprovalRepository;
import com.EMS.repository.UserRepository;

@Service
public class TasktrackApprovalServiceImpl implements TasktrackApprovalService {

	@Autowired
	TasktrackRepository tasktrackRepository;
	
	@Autowired
	TaskRepository taskRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserService userService;

	@Autowired
	TimeTrackApprovalRepository timeTrackApprovalRepository;
	
	@Autowired
	TimeTrackApprovalJPARepository timeTrackApprovalJPARepository;
//	For Task track Model


	@Override
	public Boolean checkIsUserExists(Long id) {
		Boolean exist = tasktrackRepository.existsByUser(id);
		return exist;		
	}
	
	@Override
	public List<JSONObject> getTimeTrackUserTaskDetails(Long id, Date startDate, Date endDate, List<Object[]> userList,
			List<JSONObject> loggedJsonArray,List<JSONObject> billableJsonArray,List<JSONObject> timeTrackJSONData, Boolean isExist,Long projectId) {
		if (isExist) {
			JSONObject userListObject = new JSONObject();
            
    		userList =getUserListByProject(id, startDate, endDate,projectId);

    		loggedJsonArray = new ArrayList<>();

			String name = null;
			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);
			int diffInDays = (int) ((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24)) + 1;
			int intMonth = 0,intday = 0;
			for (int i = 0; i < diffInDays; i++) {
				intMonth = (cal.get(Calendar.MONTH) + 1);
				intday = cal.get(Calendar.DAY_OF_MONTH);
				String vl = cal.get(Calendar.YEAR) + "-" + ((intMonth < 10) ? "0" + intMonth : "" + intMonth) + "-"
						+ ((intday < 10) ? "0" + intday : "" + intday);

				Double hours = 0.0;
				if (userList != null && userList.size() > 0) {
					JSONObject jsonObject = new JSONObject();
					for (Object[] item : userList) {

						String st = String.valueOf(item[3]);

						if (st.equals(vl)) {
							hours = hours + (Double) item[2];

						}
						name = (String) item[0] + " " + item[1];
					}
					jsonObject.put(vl, hours);
					cal.add(Calendar.DATE, 1);
					loggedJsonArray.add(jsonObject);

				}

				else {
					JSONObject jsonObject = new JSONObject();
					String uName = userService.getUserName(id);
					name = String.valueOf(uName).replace(",", " ");
					jsonObject.put(vl, 0);
					cal.add(Calendar.DATE, 1);
					loggedJsonArray.add(jsonObject);
				}

			}
			userListObject.put("userName", name);
			userListObject.put("userId", id);
			userListObject.put("month", intMonth);
			userListObject.put("logged", loggedJsonArray);
			
							
				int monthIndex = (cal.get(Calendar.MONTH) + 1);
				int yearIndex = cal.get(Calendar.YEAR);
				List<TaskTrackApproval> approvalUserList =getUserListForApproval(id,projectId,monthIndex,yearIndex);
				billableJsonArray = new ArrayList<>();

				name = null;
				cal.setTime(startDate);
				diffInDays = (int) ((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24)) + 1;
				intMonth = 0;
				intday = 0;
				Double hours = 0.0;
					if (approvalUserList != null && approvalUserList.size() > 0) {
						JSONObject jsonObject = new JSONObject();
						
						for (TaskTrackApproval item : approvalUserList) {
							cal.setTime(startDate);
							
							for (int i = 0; i < diffInDays; i++) {
								
								intMonth = (cal.get(Calendar.MONTH) + 1);
								intday = cal.get(Calendar.DAY_OF_MONTH);
								String vl = cal.get(Calendar.YEAR) + "-" + ((intMonth < 10) ? "0" + intMonth : "" + intMonth) + "-"
										+ ((intday < 10) ? "0" + intday : "" + intday);
								
							if(i==0)
								hours=(Double)item.getDay1();
							else if(i==1)
								hours=(Double)item.getDay2();
							else if(i==2)
								hours=(Double)item.getDay3();
							else if(i==3)
								hours=(Double)item.getDay4();
							else if(i==4)
								hours=(Double)item.getDay5();
							else if(i==5)
								hours=(Double)item.getDay6();
							else if(i==6)
								hours=(Double)item.getDay7();
							else if(i==7)
								hours=(Double)item.getDay8();
							else if(i==8)
								hours=(Double)item.getDay9();
							else if(i==9)
								hours=(Double)item.getDay10();
							else if(i==10)
								hours=(Double)item.getDay11();
							else if(i==11)
								hours=(Double)item.getDay12();
							else if(i==12)
								hours=(Double)item.getDay13();
							else if(i==13)
								hours=(Double)item.getDay14();
							else if(i==14)
								hours=(Double)item.getDay15();
							else if(i==15)
								hours=(Double)item.getDay16();
							else if(i==16)
								hours=(Double)item.getDay17();
							else if(i==17)
								hours=(Double)item.getDay18();
							else if(i==18)
								hours=(Double)item.getDay19();
							else if(i==19)
								hours=(Double)item.getDay20();
							else if(i==20)
								hours=(Double)item.getDay21();
							else if(i==21)
								hours=(Double)item.getDay22();
							else if(i==22)
								hours=(Double)item.getDay23();
							else if(i==23)
								hours=(Double)item.getDay24();
							else if(i==24)
								hours=(Double)item.getDay25();
							else if(i==25)
								hours=(Double)item.getDay26();
							else if(i==26)
								hours=(Double)item.getDay27();
							else if(i==27)
								hours=(Double)item.getDay28();
							else if(i==28)
								hours=(Double)item.getDay29();
							else if(i==29)
								hours=(Double)item.getDay30();
							else if(i==30)
								hours=(Double)item.getDay31();
							
							name = (String) item.getFirstName() + " " + item.getLastName();
							
							if(item.getProjectType().equals("Billable")) {
								jsonObject = new JSONObject();
								jsonObject.put(vl, hours);
								billableJsonArray.add(jsonObject);
							}
							cal.add(Calendar.DATE, 1);
							
							}
						}					
					}
					else {
						cal.setTime(startDate);
						for (int i = 0; i < diffInDays; i++) {
							
							intMonth = (cal.get(Calendar.MONTH) + 1);
							intday = cal.get(Calendar.DAY_OF_MONTH);
							String vl = cal.get(Calendar.YEAR) + "-" + ((intMonth < 10) ? "0" + intMonth : "" + intMonth) + "-"
									+ ((intday < 10) ? "0" + intday : "" + intday);
						
							JSONObject jsonObject = new JSONObject();
							jsonObject.put(vl, 0);
							billableJsonArray.add(jsonObject);
							
						cal.add(Calendar.DATE, 1);
						
						}
					}
				userListObject.put("billable", billableJsonArray);

			
		
			timeTrackJSONData.add(userListObject);

		} else {
			loggedJsonArray = new ArrayList<>();
			billableJsonArray = new ArrayList<>();
			JSONObject userListObject = new JSONObject();

			String uName = userService.getUserName(id);
			String name = String.valueOf(uName).replace(",", " ");

			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);
			int diffInDays = (int) ((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24)) + 1;
			int intMonth = 0,intday = 0;
			for (int i = 0; i < diffInDays; i++) {
				JSONObject jsonObject = new JSONObject();

				intMonth = (cal.get(Calendar.MONTH) + 1);
				intday = cal.get(Calendar.DAY_OF_MONTH);
				String vl = cal.get(Calendar.YEAR) + "-" + ((intMonth < 10) ? "0" + intMonth : "" + intMonth) + "-"
						+ ((intday < 10) ? "0" + intday : "" + intday);

				jsonObject.put(vl, 0);
				cal.add(Calendar.DATE, 1);
				loggedJsonArray.add(jsonObject);
				billableJsonArray.add(jsonObject);
			}
			userListObject.put("userName", name);
			userListObject.put("userId", id);
			userListObject.put("month", intMonth);
			userListObject.put("logged", loggedJsonArray);
			userListObject.put("billable", billableJsonArray);
			
			timeTrackJSONData.add(userListObject);
		}
		return timeTrackJSONData;
	}
	
	private List<Object[]> getUserListByProject(Long id, Date startDate, Date endDate, Long projectId) {
		List<Object[]> userTaskList = taskRepository.getUserListByProject(id,startDate,endDate,projectId);
		return userTaskList;
	}

	@Override
	public JSONObject getApprovedUserTaskDetails(Long id, Date startDate, Date endDate, List<TaskTrackApproval> userList,
			List<JSONObject> jsonArray, List<JSONObject> jsonDataRes1, Boolean isExist,Long projectId) {
		JSONObject userListObject = new JSONObject();
		
		List<JSONObject> billableArray = new ArrayList<>();
		List<JSONObject> nonbillableArray = new ArrayList<>();
		List<JSONObject> beachArray = new ArrayList<>();
		
		if (isExist) {
			
			Calendar cal = Calendar.getInstance();
			int monthIndex = (cal.get(Calendar.MONTH) + 1);
			int yearIndex = cal.get(Calendar.YEAR);
			userList =getUserListForApproval(id,projectId,monthIndex,yearIndex);
			
			jsonArray = new ArrayList<>();

			String name = null;
			Long billableId = null,nonBillableId=null,beachId=null,updatedBy=null;
			cal.setTime(startDate);
			int diffInDays = (int) ((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24)) + 1;
			int intMonth = 0,intday = 0;

			Double hours = 0.0;
				if (userList != null && userList.size() > 0) {
					JSONObject jsonObject = new JSONObject();
					
					for (TaskTrackApproval item : userList) {
						cal.setTime(startDate);
						
						for (int i = 0; i < diffInDays; i++) {
							
							intMonth = (cal.get(Calendar.MONTH) + 1);
							intday = cal.get(Calendar.DAY_OF_MONTH);
							String vl = cal.get(Calendar.YEAR) + "-" + ((intMonth < 10) ? "0" + intMonth : "" + intMonth) + "-"
									+ ((intday < 10) ? "0" + intday : "" + intday);
							
						if(i==0)
							hours=(Double)item.getDay1();
						else if(i==1)
							hours=(Double)item.getDay2();
						else if(i==2)
							hours=(Double)item.getDay3();
						else if(i==3)
							hours=(Double)item.getDay4();
						else if(i==4)
							hours=(Double)item.getDay5();
						else if(i==5)
							hours=(Double)item.getDay6();
						else if(i==6)
							hours=(Double)item.getDay7();
						else if(i==7)
							hours=(Double)item.getDay8();
						else if(i==8)
							hours=(Double)item.getDay9();
						else if(i==9)
							hours=(Double)item.getDay10();
						else if(i==10)
							hours=(Double)item.getDay11();
						else if(i==11)
							hours=(Double)item.getDay12();
						else if(i==12)
							hours=(Double)item.getDay13();
						else if(i==13)
							hours=(Double)item.getDay14();
						else if(i==14)
							hours=(Double)item.getDay15();
						else if(i==15)
							hours=(Double)item.getDay16();
						else if(i==16)
							hours=(Double)item.getDay17();
						else if(i==17)
							hours=(Double)item.getDay18();
						else if(i==18)
							hours=(Double)item.getDay19();
						else if(i==19)
							hours=(Double)item.getDay20();
						else if(i==20)
							hours=(Double)item.getDay21();
						else if(i==21)
							hours=(Double)item.getDay22();
						else if(i==22)
							hours=(Double)item.getDay23();
						else if(i==23)
							hours=(Double)item.getDay24();
						else if(i==24)
							hours=(Double)item.getDay25();
						else if(i==25)
							hours=(Double)item.getDay26();
						else if(i==26)
							hours=(Double)item.getDay27();
						else if(i==27)
							hours=(Double)item.getDay28();
						else if(i==28)
							hours=(Double)item.getDay29();
						else if(i==29)
							hours=(Double)item.getDay30();
						else if(i==30)
							hours=(Double)item.getDay31();
						
						name = (String) item.getFirstName() + " " + item.getLastName();
						updatedBy = item.getUpdatedBy();
						
						if(item.getProjectType().equals("Billable")) {
							jsonObject = new JSONObject();
							jsonObject.put(vl, hours);
							billableArray.add(jsonObject);
							billableId = item.getId();
						}
						else if(item.getProjectType().equals("Non-Billable")) {
							jsonObject = new JSONObject();
							jsonObject.put(vl, hours);
							nonbillableArray.add(jsonObject);
							nonBillableId = item.getId();
						}
						else if(item.getProjectType().equals("Beach")) {
							jsonObject = new JSONObject();
							jsonObject.put(vl, hours);
							beachArray.add(jsonObject);
							beachId = item.getId();
						}
						cal.add(Calendar.DATE, 1);
						
						}
					}					
				}
				else {
					cal.setTime(startDate);
					for (int i = 0; i < diffInDays; i++) {
						
						intMonth = (cal.get(Calendar.MONTH) + 1);
						intday = cal.get(Calendar.DAY_OF_MONTH);
						String vl = cal.get(Calendar.YEAR) + "-" + ((intMonth < 10) ? "0" + intMonth : "" + intMonth) + "-"
								+ ((intday < 10) ? "0" + intday : "" + intday);
					
						JSONObject jsonObject = new JSONObject();
						jsonObject.put(vl, 0);
						billableArray.add(jsonObject);
						nonbillableArray.add(jsonObject);
						beachArray.add(jsonObject);
						
					cal.add(Calendar.DATE, 1);
					
					}
				}

			userListObject.put("userName", name);
			userListObject.put("userId", id);
			userListObject.put("month", intMonth);
			userListObject.put("billable", billableArray);;
			userListObject.put("nonBillable", nonbillableArray);
			userListObject.put("beach", beachArray);
			userListObject.put("billableId", billableId);
			userListObject.put("nonBillableId", nonBillableId);
			userListObject.put("beachId", beachId);
			userListObject.put("updatedBy", updatedBy);
			jsonDataRes1.add(userListObject);

		}
		else {
			
			String uName = userService.getUserName(id);
			String name = String.valueOf(uName).replace(",", " ");

			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);
			int diffInDays = (int) ((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24)) + 1;
			int intMonth = 0,intday = 0;
			for (int i = 0; i < diffInDays; i++) {
				JSONObject jsonObject = new JSONObject();

				intMonth = (cal.get(Calendar.MONTH) + 1);
				intday = cal.get(Calendar.DAY_OF_MONTH);
				String vl = cal.get(Calendar.YEAR) + "-" + ((intMonth < 10) ? "0" + intMonth : "" + intMonth) + "-"
						+ ((intday < 10) ? "0" + intday : "" + intday);

				jsonObject.put(vl, 0);
				cal.add(Calendar.DATE, 1);
				billableArray.add(jsonObject);
				nonbillableArray.add(jsonObject);
				beachArray.add(jsonObject);
			}
			userListObject.put("userName", name);
			userListObject.put("userId", id);
			userListObject.put("month", intMonth);
			userListObject.put("billable", billableArray);;
			userListObject.put("nonBillable", nonbillableArray);
			userListObject.put("beach", beachArray);
			userListObject.put("billableId", null);
			userListObject.put("nonBillableId", null);
			userListObject.put("beachId", null);
			userListObject.put("updatedBy", null);
			
		}
		return userListObject;
	}
	public List<TaskTrackApproval> getUserListForApproval(Long id,Long projectId,Integer monthIndex,Integer yearIndex) {

		List<TaskTrackApproval> userList = timeTrackApprovalRepository.getUserListForApproval(id,projectId,monthIndex,yearIndex);
		
		return userList;
	}
	@Override
	public TaskTrackApproval findById(Long billableId) {
		TaskTrackApproval taskTrackApproval = timeTrackApprovalJPARepository.getOne(billableId);
		return taskTrackApproval;
	}
	
	@Override
	public TaskTrackApproval updateData(TaskTrackApproval taskTrackApproval) {
		return timeTrackApprovalJPARepository.save(taskTrackApproval);
		
	}
	
	@Override
	 public void save(TaskTrackApproval taskTrackApproval) {
		timeTrackApprovalJPARepository.save(taskTrackApproval);		
	}
}
