package com.EMS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.EMS.model.Alloc;
import com.EMS.model.ProjectModel;

public interface ProjectRepositary extends JpaRepository<ProjectModel, Long> {

	@Query("SELECT s.id ,s.project_name FROM ProjectModel s")
	List<String> getProjectName();

	@Query("SELECT s.id FROM ProjectModel s")
	Long getProjectId(String projectName);

	

}
