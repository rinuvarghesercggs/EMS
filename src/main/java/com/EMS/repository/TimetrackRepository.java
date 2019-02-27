package com.EMS.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.EMS.model.Timetrack;

public interface TimetrackRepository extends JpaRepository<Timetrack, Long> {
	
	
	
	List<Timetrack> getByDate(Date currentDate);

}
