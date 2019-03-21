package com.EMS.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.EMS.model.ProjectModel;

public interface ProjectRepository extends JpaRepository<ProjectModel, Long> {

	@Query("SELECT s.projectName FROM ProjectModel s")
	List<String> getProjectName();

	@Query("SELECT s.id FROM ProjectModel s where s.projectName=?1")
	Long getProjectId(String projectName);
	
	@Query("SELECT s.id,s.projectName FROM ProjectModel s")
	List<Object[]>getByIdName();

	@Query("SELECT count(p) FROM ProjectModel p WHERE p.projectName=?1")
	int findproject(String getprojectName);

	

}
