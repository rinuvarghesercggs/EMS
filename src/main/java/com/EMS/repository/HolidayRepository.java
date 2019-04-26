package com.EMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.EMS.model.HolidayModel;

public interface HolidayRepository extends JpaRepository<HolidayModel, Long>{

}
