package com.EMS.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.EMS.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
	
	@Query("SELECT t.id,t.taskName FROM Task t")
	List<Object[]> getTaskNameId();

	
	@Query(value = "SELECT EMS.user.first_name,EMS.user.last_name,EMS.tasktrack.hours,Date(EMS.tasktrack.date) FROM EMS.tasktrack JOIN EMS.user ON EMS.user.user_id = EMS.tasktrack.user_user_id where (EMS.tasktrack.date <=?3 and EMS.tasktrack.date >= ?2) and (EMS.tasktrack.user_user_id =?1)",nativeQuery = true)
	List<Object[]> getUserList(Long userId, Date startDate, Date endDate);

	
	
//	@Query("SELECT u.firstName,u.lastName,t.hours,t.date FROM Tasktrack t JOIN UserModel u ON u.userId = t.user.userId where (t.date <= ?3 and t.date >= ?2) and (t.user.userId = ?1)")
//	List<Object[]> getUserList(Long userId, Date startDate, Date endDate);

	@Query("SELECT u.firstName,u.lastName,t.hours,t.date FROM Tasktrack t JOIN UserModel u ON u.userId = t.user.userId where (t.date <= ?3 and t.date >= ?2) and (t.project.projectId = ?1)")
	List<Object[]> getUserListByProjectId(Long projectId, Date startDate, Date endDate);


	@Query("SELECT u.firstName,u.lastName,t.hours,t.date FROM Tasktrack t JOIN UserModel u ON u.userId = t.user.userId where (t.date <= ?2 and t.date >= ?1)")
	List<Object[]> getUserListByDate(Date startDate, Date endDate);

	@Query(value = "SELECT EMS.user.first_name,EMS.user.last_name,EMS.tasktrack.hours,EMS.tasktrack.date FROM EMS.tasktrack JOIN EMS.user ON EMS.user.user_id = EMS.tasktrack.user_user_id where (EMS.tasktrack.date <=?3 and EMS.tasktrack.date >= ?2) and (EMS.tasktrack.user_user_id =?1)LIMIT ?5,?4",nativeQuery = true)
	List<Object[]> getUserListNew(Long userId, Date startDate, Date endDate, Long pageSize, Long startingIndex);


	@Query(value = "select sum(EMS.tasktrack.hours),EMS.tasktrack.task_id,concat(EMS.user.first_name,' ',EMS.user.last_name),EMS.task_master.task_name from EMS.tasktrack JOIN EMS.user JOIN EMS.task_master ON (EMS.tasktrack.user_user_id = EMS.user.user_id and EMS.tasktrack.task_id = EMS.task_master.id) where (EMS.tasktrack.user_user_id = ?1) and (EMS.tasktrack.date >= ?2 and EMS.tasktrack.date <= ?3) and EMS.tasktrack.project_project_id = ?4 group by EMS.tasktrack.task_id",nativeQuery = true)
	List<Object[]> getUserTaskList(Long id, Date startDate, Date endDate, Long projectId);

//	@Query(value = "SELECT EMS.user.first_name,EMS.user.last_name,EMS.tasktrack.hours,Date(EMS.tasktrack.date) FROM EMS.tasktrack JOIN EMS.user ON EMS.user.user_id = EMS.tasktrack.user_user_id where (date(EMS.tasktrack.date) <=?3 and date(EMS.tasktrack.date) >= ?2) and (EMS.tasktrack.user_user_id =?1) and (EMS.tasktrack.project_project_id = ?4) order by EMS.tasktrack.date DESC",nativeQuery = true)
	@Query(value = "SELECT EMS.user.first_name,EMS.user.last_name,EMS.tasktrack.hours,Date(EMS.tasktrack.date) FROM EMS.tasktrack JOIN EMS.user ON EMS.user.user_id = EMS.tasktrack.user_user_id where (date(EMS.tasktrack.date) <=date(?3) and date(EMS.tasktrack.date) >= date(?2)) and (EMS.tasktrack.user_user_id =?1) and (EMS.tasktrack.project_project_id = ?4) order by EMS.tasktrack.date ASC",nativeQuery = true)
	List<Object[]> getUserListByProject(Long id, Date startDate, Date endDate, Long projectId);
	

}

