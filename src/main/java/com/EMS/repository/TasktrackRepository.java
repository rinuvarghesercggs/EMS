package com.EMS.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.EMS.model.ProjectModel;
import com.EMS.model.Task;
import com.EMS.model.Tasktrack;

public interface TasktrackRepository extends JpaRepository<Tasktrack, Long> {

	@Query("SELECT t FROM Tasktrack t WHERE t.date =?1 and t.user.id=?2")
	List<Tasktrack> getByDate(Date currentDate, Long id);

	@Modifying
	@Transactional
	@Query("UPDATE Tasktrack t set t.description=?1,t.date=?3 where t.id=?2")
	public void updateTaskById(String description, long id, Date date) throws Exception;

	@Modifying
	@Transactional
	@Query("delete from Tasktrack t where t.id=?1")
	public void deleteTaskById(long id) throws Exception;

	public default void createTask(Tasktrack task) throws Exception {
		try {
			// getSession().save(task);
		} catch (Exception exc) {
			throw new Exception();
		}
	}

	@Query("SELECT p from ProjectModel p")
	public List<ProjectModel> getProjectNames() throws Exception;

	@Query("SELECT c from Task c")
	public List<Task> getTaskCategories() throws Exception;

	@Query("SELECT tt from Tasktrack tt")
	public List<Tasktrack> getTaskList() throws Exception;
	/*
	 * public void getTaskCategory() {
	 * 
	 * }
	 */
}
