package com.EMS.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.EMS.model.Timetrack;
import com.EMS.service.ProjectService;
import com.EMS.service.TaskService;
import com.EMS.service.TimetrackService;

import javassist.expr.NewArray;

@RestController
@RequestMapping(value={"/timetrack"})
public class TimetrackController {
	
	@Autowired
	TimetrackService timetrackService;
	@Autowired
	ProjectService projectService;
	@Autowired
	TaskService taskService;
	
	
	  @PostMapping(value="/getTaskdetails" )
	 public List<Timetrack> getByDate(@RequestBody JSONObject requestdata) {	 
		  String dateString =requestdata.get("taskDate").toString();					
		  String userName =requestdata.get("uname").toString();
		  System.out.println("Date : "+dateString);
		  System.out.println("User Name : "+userName);
		    Date currentDate = null;
		    List<Timetrack> tracklist =null;
			try {
				DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); 
				Date datenew = (Date)formatter.parse(dateString);
				System.out.println("Converted"+datenew);
				 tracklist = timetrackService.getByDate(currentDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}  
			
						
	  System.out.println("Size"+tracklist.size());
	  return tracklist;
	 }
	 
	 
	 @GetMapping(value = "/getprojectTaskDatas")
	 public JSONObject getprojectnameList(){
		 List<String> projectTitleList = projectService.getProjectsList();
		 List<String> taskTypesList = taskService.getTaskList();
		  JSONObject returnData = new JSONObject();
		  JSONObject projectTaskDatas = new JSONObject();
 
	      try {
	    	  if (!projectTitleList.isEmpty() && !taskTypesList.isEmpty()) {
			  projectTaskDatas.put("projectTitle", projectTitleList);
			  projectTaskDatas.put("taskTypes", taskTypesList);
			  returnData.put("status", "success");
			  returnData.put("data", projectTaskDatas);
	    	  }
	        } catch (Exception e) {
	        	returnData.put("status", "Failure");
	        	returnData.put("data", projectTaskDatas);
	        }	
		return returnData;	 
	 }
	 
	 
	 @PostMapping(value="/create",headers="Accept=application/json")
	 public ResponseEntity<Void> createTimrtrackRecord(@RequestBody Timetrack timetrack, UriComponentsBuilder ucBuilder){
	     HttpHeaders headers = new HttpHeaders();
	     headers.setLocation(ucBuilder.path("/timetrack/get").buildAndExpand(timetrack.getId()).toUri());
	     return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	 }
	
	 
	 
	 
	 
	 
	 
	 
	 
	@GetMapping(value = "find/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Timetrack> getRecordById(@PathVariable("id") long id) {
        System.out.println("Fetching Record with id " + id);
        Timetrack timetrack = timetrackService.findById(id);
        timetrack.setDate(new Date());
        Timetrack timetracknew=timetrackService.update(timetrack);
        System.out.println("Timetrack : "+timetrack);
        if (timetrack == null) {
            return new ResponseEntity<Timetrack>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Timetrack>(timetracknew, HttpStatus.OK);
    }
	
	
	 @GetMapping(value="/get")
	 public List<Timetrack> getAllTimetrackRecords() {	 
	  List<Timetrack> tracklist= timetrackService.getAllRecord();
	  return tracklist;
	
	 }
//	
	 
//	 @PostMapping(value="/create",headers="Accept=application/json")
//	 public ResponseEntity<Void> createNewRecord(@RequestBody Timetrack timetrack, UriComponentsBuilder ucBuilder){
//	     System.out.println("Creating entry "+timetrack.getId());
//	     timetrackService.createNewRecord(timetrack);
//	     HttpHeaders headers = new HttpHeaders();
//	     headers.setLocation(ucBuilder.path("/timetrack/get").buildAndExpand(timetrack.getId()).toUri());
//	     return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
//	 }
	 
	 
	 
//	 @PutMapping(value="/update", headers="Accept=application/json")
//		public ResponseEntity<String> updateRecord(@RequestBody Timetrack timetrack)
//		{
//			System.out.println("Updating");
//		Timetrack timetrackOld = timetrackService.findById(timetrack.getId());
//		if (timetrackOld ==null) {
//			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
//		}
//		timetrackService.update(timetrack);
//		return new ResponseEntity<String>(HttpStatus.OK);
//		}
//	 
//	 
//	 @DeleteMapping(value="delete/{id}", headers ="Accept=application/json")
//		public ResponseEntity<Timetrack> deleteUser(@PathVariable("id") long id){
//			Timetrack timetrack = timetrackService.findById(id);
//			if (timetrack == null) {
//				return new ResponseEntity<Timetrack>(HttpStatus.NOT_FOUND);
//			}
//			timetrackService.deleteRecordById(id);
//			return new ResponseEntity<Timetrack>(HttpStatus.NO_CONTENT);
//		}

}
