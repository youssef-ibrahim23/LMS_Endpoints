package com.example.sms.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.sms.Models.StudentAttendance;

import com.example.sms.DTO.AttendanceSummaryDTO;

public interface StudentAttendanceRepository extends JpaRepository<StudentAttendance, Integer> {
    @Query("""
    SELECT new com.example.sms.DTO.AttendanceSummaryDTO(
        s.student.user.userId,
        100.0 * COUNT(CASE WHEN s.status = 'Present' THEN 1 END) / COUNT(s),
        100.0 * COUNT(CASE WHEN s.status = 'Absent' THEN 1 END) / COUNT(s),
        100.0 * COUNT(CASE WHEN s.status = 'Late' THEN 1 END) / COUNT(s)
    )
    FROM StudentAttendance s
    WHERE s.student.user.userId = :id
    GROUP BY s.student.user.userId
    """)
AttendanceSummaryDTO attendanceSummary(@Param("id") Integer id);


}
