package com.EMS.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.EMS.model.Timetrack;

public interface TimetrackService {
	
	public List<Timetrack> getAllRecord();
	public void createNewRecord(Timetrack timetrack);
	public Timetrack findById(long id);
	public Timetrack update(Timetrack timetrack);
	public void deleteRecordById(long id);
	
	
	public List<Timetrack> getByDate(Date currentDate);

}
