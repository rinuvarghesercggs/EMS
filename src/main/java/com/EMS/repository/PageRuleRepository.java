package com.EMS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.EMS.model.PageRule;

public interface PageRuleRepository extends JpaRepository<PageRule, Long> {

	@Query(value = "select rule.pageKey from PageRule rule where rule.roleId = ?1")
	List<PageRule> getBlockedList(Long roleId);

}
