package com.EMS.repository;

import com.EMS.model.UserLeaveSummary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface UserLeaveSummaryRepository extends JpaRepository<UserLeaveSummary, Long>{

    @Query(value = "select COUNT(*) from user_leave_summary where user_leave_summary_id=?1 ",nativeQuery = true)
    int isExist(long leaveSummaryId);

    @Query(value = "select * from user_leave_summary where user_user_id=?1",nativeQuery = true)
    List<UserLeaveSummary> getUserLeaveSummaryList(long userId);


    @Query(value = "select * from user_leave_summary where user_leave_summary_id=?1",nativeQuery = true)
    UserLeaveSummary getLeaveDetailsById(long userLeaveSummaryId);

    @Query(value = "select COUNT(*) from user_leave_summary where user_user_id=?1 ",nativeQuery = true)
    int isUserExist(long userId);

    @Query(value = "select COUNT(*) from user_leave_summary where user_user_id=?1 and leave_date<=?3 and leave_date>=?2 and leave_type='FD'",nativeQuery = true)
    int getFullDayLeaveDays(long userId, Date startDate, Date endDate);

    @Query(value = "select COUNT(*) from user_leave_summary where user_user_id=?1 and leave_date<=?3 and leave_date>=?2 and leave_type='HD'",nativeQuery = true)
    int getHalfDayLeaveDays(long userId, Date startDate, Date endDate);

}
