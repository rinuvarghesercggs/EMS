package com.EMS.service;

import java.util.List;
import com.EMS.model.PageRule;


public interface PageRuleService {


	List<PageRule> getBlockedPageList(long roleId);



}

