package com.timetrack.ti.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.timetrack.ti.model.ProjectModel;

public interface ProjectRepositary extends JpaRepository<ProjectModel, Long> {

}
