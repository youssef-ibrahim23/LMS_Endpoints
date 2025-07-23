package com.example.sms.Repositories;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.sms.Models.Assignment;

public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {
    @Query(value = """
                SELECT * FROM assignments
            WHERE status ILIKE CONCAT('%', :status, '%')
                """, nativeQuery = true)
    List<Assignment> getAssignmentsByStatus(@Param("status") String status);

    @Query(value = """
    SELECT a.*
    FROM assignments a
    JOIN grades_subjects gs ON a.grade_subject_id = gs.grade_subject_id
    JOIN students s ON gs.grade_id = s.grade_id
    WHERE s.student_id = :studentId
      AND a.deadline < NOW()
    """, nativeQuery = true)
List<Assignment> findOverdueAssignmentsByStudentId(@Param("studentId") Integer studentId);


}
