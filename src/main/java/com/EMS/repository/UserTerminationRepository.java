package com.EMS.repository;

import com.EMS.model.UserTermination;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface UserTerminationRepository extends JpaRepository<UserTermination, Long>{

    @Query(value = "select term_type from user_termination where consultant_user_id=?1",nativeQuery = true)
    String getTermType(long userId);

    @Query(value = "select COUNT(*) from user_termination where consultant_user_id=?1",nativeQuery = true)
    int checkExistanceOfUserId(long userId);

    @Transactional
    @Modifying
    @Query(value = "update user_termination set term_type =?1,termination_date =?2 where consultant_user_id=?3", nativeQuery = true)
    void updateUserTerm(String terminationType, Date date3, long userId);
}
