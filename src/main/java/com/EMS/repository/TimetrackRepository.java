package com.EMS.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.EMS.model.Timetrack;

public interface TimetrackRepository extends JpaRepository<Timetrack, Long> {
	
	
	@Query("SELECT k FROM Timetrack k WHERE k.date=?1")
	List<Timetrack> getByDate(Date currentDate,Long uId);

}
