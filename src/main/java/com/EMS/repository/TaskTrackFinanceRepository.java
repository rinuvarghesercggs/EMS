package com.EMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.EMS.model.TaskTrackApprovalFinance;
import com.EMS.model.TaskTrackApprovalLevel2;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskTrackFinanceRepository extends JpaRepository<TaskTrackApprovalFinance, Long>{

    @Query(value = "SELECT user_user_id as id,first_name,last_name, sum(COALESCE(day1,0)) as day1,sum(COALESCE(day2,0)) as day2,sum(COALESCE(day3,0)) as day3,sum(COALESCE(day4,0)) as day4,"+
            "sum(COALESCE(day5,0)) as day5,sum(COALESCE(day6,0)) as day6,sum(COALESCE(day7,0)) as day7,sum(COALESCE(day8,0)) as day8,sum(COALESCE(day9,0)) as day9," +
            "sum(COALESCE(day10,0)) as day10,sum(COALESCE(day11,0)) as day11,sum(COALESCE(day12,0)) as day12,sum(COALESCE(day13,0)) as day13,sum(COALESCE(day14,0)) as day14," +
            "sum(COALESCE(day15,0)) as day15,sum(COALESCE(day16,0)) as day16,sum(COALESCE(day17,0)) as day17,sum(COALESCE(day18,0)) as day18,sum(COALESCE(day19,0)) as day19," +
            "sum(COALESCE(day20,0)) as day20,sum(COALESCE(day21,0)) as day21,sum(COALESCE(day22,0)) as day22,sum(COALESCE(day23,0)) as day23,sum(COALESCE(day24,0)) as day24," +
            "sum(COALESCE(day25,0)) as day25,sum(COALESCE(day26,0)) as day26,sum(COALESCE(day27,0)) as day27,sum(COALESCE(day28,0)) as day28,sum(COALESCE(day29,0)) as day29," +
            "sum(COALESCE(day30,0)) as day30,sum(COALESCE(day31,0)) as day31 FROM tasktrack_approval_finance ta  inner join user u ON u.user_id = ta.user_user_id where " +
            "month=?1 and year=?2 and project_project_id=?3 and project_type in('Billable','Overtime') group by user_user_id",nativeQuery = true)
    List<Object[]> getFinanceDataByProject(Integer monthIndex, Integer yearIndex, Long projectId);

    @Query(value = "SELECT project_project_id as id,project_name, sum(COALESCE(day1,0)) as day1,sum(COALESCE(day2,0)) as day2,sum(COALESCE(day3,0)) as day3,sum(COALESCE(day4,0)) as day4," +
            "sum(COALESCE(day5,0)) as day5,sum(COALESCE(day6,0)) as day6,sum(COALESCE(day7,0)) as day7,sum(COALESCE(day8,0)) as day8,sum(COALESCE(day9,0)) as day9," +
            "sum(COALESCE(day10,0)) as day10,sum(COALESCE(day11,0)) as day11,sum(COALESCE(day12,0)) as day12,sum(COALESCE(day13,0)) as day13,sum(COALESCE(day14,0)) as day14," +
            "sum(COALESCE(day15,0)) as day15,sum(COALESCE(day16,0)) as day16,sum(COALESCE(day17,0)) as day17,sum(COALESCE(day18,0)) as day18,sum(COALESCE(day19,0)) as day19," +
            "sum(COALESCE(day20,0)) as day20,sum(COALESCE(day21,0)) as day21,sum(COALESCE(day22,0)) as day22,sum(COALESCE(day23,0)) as day23,sum(COALESCE(day24,0)) as day24," +
            "sum(COALESCE(day25,0)) as day25,sum(COALESCE(day26,0)) as day26,sum(COALESCE(day27,0)) as day27,sum(COALESCE(day28,0)) as day28,sum(COALESCE(day29,0)) as day29," +
            "sum(COALESCE(day30,0)) as day30,sum(COALESCE(day31,0)) as day31 FROM tasktrack_approval_finance ta  inner join project p ON p.project_id = ta.project_project_id where " +
            "month=?1 and year=?2 and user_user_id=?3 and ta.project_type in('Billable','Overtime') group by project_project_id",nativeQuery = true)
    List<Object[]> getFinanceDataByUser( Integer monthIndex, Integer yearIndex, Long userId);

    @Query(value = "SELECT project_project_id as projectid,project_name,user_user_id as userid,first_name,last_name, sum(COALESCE(day1,0)) as day1,sum(COALESCE(day2,0)) as day2,sum(COALESCE(day3,0)) as day3,sum(COALESCE(day4,0)) as day4," +
            "sum(COALESCE(day5,0)) as day5,sum(COALESCE(day6,0)) as day6,sum(COALESCE(day7,0)) as day7,sum(COALESCE(day8,0)) as day8,sum(COALESCE(day9,0)) as day9," +
            "sum(COALESCE(day10,0)) as day10,sum(COALESCE(day11,0)) as day11,sum(COALESCE(day12,0)) as day12,sum(COALESCE(day13,0)) as day13,sum(COALESCE(day14,0)) as day14," +
            "sum(COALESCE(day15,0)) as day15,sum(COALESCE(day16,0)) as day16,sum(COALESCE(day17,0)) as day17,sum(COALESCE(day18,0)) as day18,sum(COALESCE(day19,0)) as day19," +
            "sum(COALESCE(day20,0)) as day20,sum(COALESCE(day21,0)) as day21,sum(COALESCE(day22,0)) as day22,sum(COALESCE(day23,0)) as day23,sum(COALESCE(day24,0)) as day24," +
            "sum(COALESCE(day25,0)) as day25,sum(COALESCE(day26,0)) as day26,sum(COALESCE(day27,0)) as day27,sum(COALESCE(day28,0)) as day28,sum(COALESCE(day29,0)) as day29," +
            "sum(COALESCE(day30,0)) as day30,sum(COALESCE(day31,0)) as day31 FROM tasktrack_approval_finance ta  inner join project p ON p.project_id = ta.project_project_id inner join user u ON u.user_id = ta.user_user_id where " +
            "month=?1 and year=?2 and user_user_id=?3 and project_project_id=?4  and ta.project_type in('Billable','Overtime') group by project_project_id",nativeQuery = true)
    List<Object[]> getFinanceDataByUserAndProject( Integer monthIndex, Integer yearIndex, Long userId,Long projectId);

}
