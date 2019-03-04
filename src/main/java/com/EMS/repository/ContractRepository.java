package com.EMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.EMS.model.ContractModel;

public interface ContractRepository extends JpaRepository<ContractModel, Long>{

}
