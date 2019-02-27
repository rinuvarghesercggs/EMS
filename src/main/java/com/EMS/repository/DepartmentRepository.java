package com.EMS.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.EMS.model.DepartmentModel;
import com.EMS.model.UserModel;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentModel, Long> {

		
	
}



































































