package com.EMS.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EMS.dto.Taskdetails;
import com.EMS.model.TaskModel;
import com.EMS.model.Timetrack;
import com.EMS.repository.TimetrackRepository;

@Service
public class TimetrackServiceImpl implements TimetrackService {
	
	@Autowired
	TimetrackRepository timetrackRepository;
	
	public List<Timetrack> getAllRecord() {
		List<Timetrack> newList= timetrackRepository.findAll();
		return newList;
	}
	
	
	public void createNewRecord(Timetrack timetrack) {
		timetrackRepository.save(timetrack);
	}
	
	public Timetrack findById(long id) {
		return timetrackRepository.getOne(id);
	}

	public Timetrack update(Timetrack timetrack) {
		return timetrackRepository.save(timetrack);
	}
	
	public void deleteRecordById(long id) {
		timetrackRepository.deleteById(id);
	}
	
	
	
//	public List<TaskModel> getByDate(Date currentDate){
//		return  timetrackRepository.getByDate(currentDate);
//				
//	}

}
