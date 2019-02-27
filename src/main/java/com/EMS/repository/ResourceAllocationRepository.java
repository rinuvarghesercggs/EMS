package com.EMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.EMS.model.Alloc;


public interface ResourceAllocationRepository extends JpaRepository<Alloc, Long> {

	

}
