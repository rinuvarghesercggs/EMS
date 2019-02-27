package com.EMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.EMS.model.ProjectModel;

@Repository
public interface ProjectRepositary extends JpaRepository<ProjectModel, Long> {

}
