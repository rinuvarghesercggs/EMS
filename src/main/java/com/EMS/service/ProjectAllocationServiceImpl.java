package com.EMS.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EMS.model.AllocationModel;
import com.EMS.model.DepartmentModel;
import com.EMS.model.ProjectModel;
import com.EMS.model.UserModel;
import com.EMS.repository.DepartmentRepository;
import com.EMS.repository.ProjectRepository;
import com.EMS.repository.ProjectAllocationRepository;
import com.EMS.repository.UserRepository;


@Service
@Transactional
public class ProjectAllocationServiceImpl implements ProjectAllocationService{

	@Autowired 
	ProjectAllocationRepository projectAllocationRepository;
	
	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	DepartmentRepository departmentRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	 public void save(AllocationModel resourceAllocationModel) {
		projectAllocationRepository.save(resourceAllocationModel);		
	}
	
	@Override
	public List<AllocationModel> getList() {
		List<AllocationModel> list = projectAllocationRepository.findAll();
		return list;
	}

	@Override
	public AllocationModel findDataById(Long id) {
		return projectAllocationRepository.getOne(id);
	}

	@Override
	public Boolean remove(Long id) {
		// projectAllocationRepository.deleteById(id);
		boolean result = false;
		try {
			projectAllocationRepository.deleteById(id);
			result = true;
		} catch (Exception exc) {
			exc.printStackTrace();
		}

		return result;
	}

	@Override
	public AllocationModel updateData(AllocationModel currentAlloc) {
		return projectAllocationRepository.save(currentAlloc);
		
	}

	@Override
	public AllocationModel updatePartially(AllocationModel allocationModels, Long id) {
		AllocationModel allocationModel = projectAllocationRepository.getOne(id);
		return projectAllocationRepository.save(allocationModel);
	}

	
	@Override
	public List<AllocationModel> getAllocationList(Long projectId) {
		List<AllocationModel> projList = projectAllocationRepository.getProjectLists(projectId);
		return projList;
	}

	@Override
	public List<DepartmentModel> getDepartmentList() {
		List<DepartmentModel> nameList = departmentRepository.findDeptName();
		return nameList;
	}

	@Override
	public List<UserModel> getUserList() {
        List<UserModel> userList = userRepository.getUser();
		return userList;
	}

	@Override
	public List<AllocationModel> getAllocationLists() {
		List<AllocationModel> allocList = projectAllocationRepository.findAll();
		return allocList;
	}

	@Override
	public Boolean checkIsExist(long userId) {
		Boolean exist = projectAllocationRepository.isExists(userId);
	return exist;
	}

	@Override
	public List<AllocationModel> getListByUser(long userId) {
		List<AllocationModel> allocList = projectAllocationRepository.findByUserId(userId);
		return allocList;
	}

	@Override
	public List<AllocationModel> getUsersList(long userId, Date date1, Date date2) {
		List<AllocationModel> allocList = projectAllocationRepository.findUsers(userId,date1,date2);
		return allocList;
	}

	@Override
	public Long getAllocId(long projectId, long userId) {
		Long id = projectAllocationRepository.getAllocationId(projectId,userId);
		return id;
	}


//	@Override
//	public List<Object[]> getUserIdByProject(Long projectId, Long pageSize, Long limit) {
//		List<Object[]> userIdList = projectAllocationRepository.getUserIdByProject(projectId,pageSize,limit);
//		return userIdList;
//	}
	
	@Override
	public List<Object[]> getUserIdByProject(Long projectId) {
		List<Object[]> userIdList = projectAllocationRepository.getUserIdByProject(projectId);
		return userIdList;
	}

	public List<Object[]>getUserIdByProjectAndDate(Long projectId,Date startDate, Date endDate){
		List<Object[]> userIdList = projectAllocationRepository.getUserIdByProjectAndDate(projectId,startDate,endDate);
		return userIdList;
	}
	public List<Object[]>getProjectListByUserAndDate(Long projectId,Date startDate, Date endDate){
		List<Object[]> projectList = projectAllocationRepository.getProjectListByUserAndDate(projectId,startDate,endDate);
		return projectList;
	}

	@Override
	public Long getUserCount(Long projectId) {
		Long count = projectAllocationRepository.getUserCount(projectId);
		return count;
	}


	@Override
	public Boolean getIsBillable(Long id,Long projectId) {
		Boolean isBillable = projectAllocationRepository.getIsBillable(id,projectId);
		return isBillable;
	}
	
	@Override
	public AllocationModel findById(Long id) {
		AllocationModel model = projectAllocationRepository.getOne(id);
		return model;
	}

	@Override
	public List<AllocationModel> getAllocationListonDate(long projectId, LocalDate startDate, LocalDate endDate) {
		List<AllocationModel> projList = projectAllocationRepository.getProjectDatewiseLists(projectId,startDate,endDate);
		return projList;
	}


}
