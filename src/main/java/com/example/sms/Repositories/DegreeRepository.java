package com.example.sms.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.sms.Models.Degree;

public interface DegreeRepository extends JpaRepository<Degree, Integer> {

    @Query(value = """
    SELECT d.*
    FROM degrees d
    JOIN exams e ON d.exam_id = e.exam_id
    WHERE d.student_id = :studentId
    ORDER BY e.exam_date DESC
    LIMIT 10
    """, nativeQuery = true)
List<Degree> findRecentDegreesByStudentId(@Param("studentId") Integer studentId);

List<Degree> findByStudentStudentId(int StudentId);


}
