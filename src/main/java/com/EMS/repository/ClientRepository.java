package com.EMS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.EMS.model.ClientModel;

public interface ClientRepository extends JpaRepository<ClientModel, Long>{

	@Query(value="SELECT distinct client_country FROM EMS.client",nativeQuery=true)
	List<String> getLocation();

}
