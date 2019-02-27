package com.EMS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.EMS.model.Timetrack;
import com.EMS.service.TimetrackService;

@RestController
@RequestMapping(value={"/timetrack"})
public class TimetrackController {
	
	@Autowired
	TimetrackService timetrackService;
	
	
	@GetMapping(value = "find/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Timetrack> getRecordById(@PathVariable("id") long id) {
        System.out.println("Fetching Record with id " + id);
        Timetrack timetrack = timetrackService.findById(id);
        if (timetrack == null) {
            return new ResponseEntity<Timetrack>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Timetrack>(timetrack, HttpStatus.OK);
    }
	
	
	 @GetMapping(value="/get", headers="Accept=application/json")
	 public List<Timetrack> getAllTimetrackRecords() {	 
	  List<Timetrack> tracklist= timetrackService.getAllRecord();
	  return tracklist;
	
	 }
	 
	 
	 @PostMapping(value="/create",headers="Accept=application/json")
	 public ResponseEntity<Void> createNewRecord(@RequestBody Timetrack timetrack, UriComponentsBuilder ucBuilder){
	     System.out.println("Creating entry "+timetrack.getId());
	     timetrackService.createNewRecord(timetrack);
	     HttpHeaders headers = new HttpHeaders();
	     headers.setLocation(ucBuilder.path("/timetrack/get").buildAndExpand(timetrack.getId()).toUri());
	     return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	 }
	 
	 
	 
	 @PutMapping(value="/update", headers="Accept=application/json")
		public ResponseEntity<String> updateRecord(@RequestBody Timetrack timetrack)
		{
			System.out.println("Updating");
		Timetrack timetrackOld = timetrackService.findById(timetrack.getId());
		if (timetrackOld ==null) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		timetrackService.update(timetrack);
		return new ResponseEntity<String>(HttpStatus.OK);
		}
	 
	 
	 @DeleteMapping(value="delete/{id}", headers ="Accept=application/json")
		public ResponseEntity<Timetrack> deleteUser(@PathVariable("id") long id){
			Timetrack timetrack = timetrackService.findById(id);
			if (timetrack == null) {
				return new ResponseEntity<Timetrack>(HttpStatus.NOT_FOUND);
			}
			timetrackService.deleteRecordById(id);
			return new ResponseEntity<Timetrack>(HttpStatus.NO_CONTENT);
		}

}
