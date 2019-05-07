package com.EMS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.EMS.model.HolidayModel;

public interface HolidayRepository extends JpaRepository<HolidayModel, Long>{

	@Query(value = "SELECT Date(EMS.holiday.`date`) as `date` ,EMS.holiday.holiday_id,EMS.holiday.day,EMS.holiday.holiday_name,EMS.holiday.holiday_type FROM EMS.holiday",nativeQuery = true)
	List<Object[]> getHolidayLists();

}
