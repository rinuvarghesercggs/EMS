package com.EMS.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.EMS.model.PasswordResetModel;
import com.EMS.model.UserModel;

public interface PasswordResetRepository extends JpaRepository<PasswordResetModel, Long>{
	List<PasswordResetModel> findByToken(String token);
	
	@Query("SELECT prm FROM PasswordResetModel prm WHERE prm.user.userId = ?1")
	List<PasswordResetModel> findByUserId(long userId);
}
