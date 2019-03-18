package com.EMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.EMS.model.ClientModel;

public interface ClientRepository extends JpaRepository<ClientModel, Long>{

}
