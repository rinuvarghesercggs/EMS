package com.EMS.service;

import java.util.Date;
import java.util.List;
import com.EMS.model.Alloc;
import com.EMS.model.DepartmentModel;
import com.EMS.model.UserModel;



public interface ResourceAllocationService {

	public void save(Alloc alloc);
	public List<Alloc> getList();
	public Alloc findDataById(Long id);
	public void remove(Long id);
	public Alloc updateData(Alloc alloc);
	public Alloc updatePartially(Alloc alloc, Long id);
	public List<Alloc> getAllocationList(Long projectId);
	public List<DepartmentModel> getDepartmentList();
	public List<UserModel> getUserList();
	public List<Alloc> getAllocationLists();
	public Boolean checkIsExist(long userId);
	public List<Alloc> getListByUser(long userId);
	public List<Alloc> getUsersList(long userId, Date date1, Date date2);
	

}
