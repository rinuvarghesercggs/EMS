package com.EMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.EMS.model.Resources;

public interface ResourceRepository extends JpaRepository<Resources, Long>{

}
