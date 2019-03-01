package com.EMS.service;

import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;

import com.EMS.dto.Taskdetails;
import com.EMS.model.TaskModel;
import com.EMS.model.Timetrack;

public interface TimetrackService {
	
	public List<Timetrack> getAllRecord();
	public void createNewRecord(Timetrack timetrack);
	public Timetrack findById(long id);
	public Timetrack update(Timetrack timetrack);
	public void deleteRecordById(long id);
	
	
//	public List<Timetrack>  getByDate(Date currentDate,Long uId);

}
