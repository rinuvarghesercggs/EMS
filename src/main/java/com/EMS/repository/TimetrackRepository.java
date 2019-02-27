package com.EMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.EMS.model.Timetrack;

public interface TimetrackRepository extends JpaRepository<Timetrack, Long> {

}
