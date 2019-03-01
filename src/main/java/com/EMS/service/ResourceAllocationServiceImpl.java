package com.EMS.service;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EMS.model.Alloc;
import com.EMS.model.DepartmentModel;
import com.EMS.model.UserModel;
import com.EMS.repository.DepartmentRepository;
import com.EMS.repository.ProjectRepository;
import com.EMS.repository.ResourceAllocationRepository;
import com.EMS.repository.UserRepositary;


@Service
@Transactional
public class ResourceAllocationServiceImpl implements ResourceAllocationService{

	@Autowired 
	ResourceAllocationRepository resourceAllocationRepository;
	
	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	DepartmentRepository departmentRepository;
	
	@Autowired
	UserRepositary userRepository;
	
	@Override
	 public void save(Alloc resourceAllocationModel) {
		resourceAllocationRepository.save(resourceAllocationModel);		
	}
	
	@Override
	public List<Alloc> getList() {
		List<Alloc> list = resourceAllocationRepository.findAll();
		return list;
	}

	@Override
	public Alloc findDataBy(Long id) {
		return resourceAllocationRepository.getOne(id);
	}

	@Override
	public void remove(Long id) {
		 resourceAllocationRepository.deleteById(id);
	}

	@Override
	public Alloc updateData(Alloc currentAlloc) {
		return resourceAllocationRepository.save(currentAlloc);
		
	}

	@Override
	public Alloc updatePartially(Alloc allocs, Long id) {
		Alloc alloc = resourceAllocationRepository.getOne(id);
//		alloc.setName(allocs.getName());
		return resourceAllocationRepository.save(alloc);
	}

	
	@Override
	public List<Alloc> getAllocationList(Long projectId) {
		List<Alloc> projList = resourceAllocationRepository.getProjectLists(projectId);
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
	public List<Alloc> getAllocationLists() {
		List<Alloc> allocList = resourceAllocationRepository.findAll();
		return allocList;
	}

	
}
