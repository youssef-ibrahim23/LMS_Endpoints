package com.example.sms.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.sms.Models.Session;

public interface SessionRepository extends JpaRepository<Session, Integer> {

    @Query(value = """
    SELECT s.*
    FROM sessions s
    JOIN grades_subjects gs ON s.grade_subject_id = gs.grade_subject_id
    JOIN students st ON gs.grade_id = st.grade_id
    WHERE st.student_id = :studentId
      AND s.session_date = CURRENT_DATE
    """, nativeQuery = true)
List<Session> findTodaySessionsByStudentId(@Param("studentId") Integer studentId);

}
