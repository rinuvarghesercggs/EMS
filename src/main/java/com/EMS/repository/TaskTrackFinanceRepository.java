package com.EMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.EMS.model.TaskTrackApprovalFinance;
import com.EMS.model.TaskTrackApprovalLevel2;

public interface TaskTrackFinanceRepository extends JpaRepository<TaskTrackApprovalFinance, Long>{

}
