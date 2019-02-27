package com.EMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.EMS.model.ProjectModel;

public interface ProjectRepositary extends JpaRepository<ProjectModel, Long> {

}
