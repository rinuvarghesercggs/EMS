package com.EMS.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.EMS.model.UserTaskCategory;

public interface UserTaskCategoryRepository extends JpaRepository<UserTaskCategory, Long>{
}
