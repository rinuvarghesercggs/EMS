package com.EMS.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.EMS.model.AllocationModel;
import com.EMS.model.ProjectModel;
import com.EMS.model.Task;
import com.EMS.model.Tasktrack;

public interface TasktrackRepository extends JpaRepository<Tasktrack, Long> {

	@Query("SELECT t FROM Tasktrack t WHERE t.user.userId=?3 AND CAST(t.date as date) BETWEEN ?1 AND ?2 order by date(t.date) asc")
	List<Tasktrack> getByDate(Date startDate, Date endDate, Long id);

	@Modifying
	@Transactional
	@Query("UPDATE Tasktrack t set t.description=?1,t.date=?3,t.hours=?4,t.project=?5,t.task=?6 where t.id=?2")
	public void updateTaskById(String description, long id, Date date,double hours,ProjectModel projectModel,Task task) throws Exception;

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

	@Query("SELECT a from AllocationModel a where a.user.userId=?1")
	public List<AllocationModel> getProjectNames(long uId) throws Exception;

	@Query("SELECT utc.taskCategory.task from UserTaskCategory utc where utc.user.userId = ?1")
	public List<Task> getTaskCategories(long uId) throws Exception;

	@Query("from ProjectModel where projectId=?1")
	public ProjectModel getProjectById(long id);
	
	@Query("SELECT tt from Tasktrack tt")
	public List<Tasktrack> getTaskList() throws Exception;
	/*
	 * public void getTaskCategory() {
	 * 
	 * }
	 */
}
