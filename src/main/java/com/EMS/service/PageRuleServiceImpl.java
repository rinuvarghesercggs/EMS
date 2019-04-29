package com.EMS.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EMS.model.PageRule;

import com.EMS.repository.PageRuleRepository;


@Service
public class PageRuleServiceImpl implements PageRuleService {
		
	@Autowired
	PageRuleRepository pageRuleRepository;
	


	@Override
	public List<PageRule> getBlockedPageList(long roleId) {
		List<PageRule> blockedList = pageRuleRepository.getBlockedList(roleId);
		return blockedList;
	}




	

}
