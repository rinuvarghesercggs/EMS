package com.EMS.service;

import java.util.List;
import com.EMS.model.Alloc;



public interface ResourceAllocationService {

	public void save(Alloc alloc);
	public List<Alloc> getList();
	public Alloc findDataBy(Long id);
	public void remove(Long id);
	public Alloc updateData(Alloc alloc);
	public Alloc updatePartially(Alloc alloc, Long id);
	public List<Alloc> getAllocationList(String projectName);

}
