package com.EMS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.EMS.model.HolidayModel;

public interface HolidayRepository extends JpaRepository<HolidayModel, Long>{

	@Query(value = "SELECT Date(holiday.`date`) as `date` ,holiday.holiday_id,holiday.day,holiday.holiday_name,holiday.holiday_type FROM holiday",nativeQuery = true)
	List<Object[]> getHolidayLists();

}
