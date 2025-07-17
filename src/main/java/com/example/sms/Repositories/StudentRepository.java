package com.example.sms.Repositories;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.sms.Models.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    @Query(value = """
    SELECT g.grade_name AS grade_name, COUNT(*) AS student_count
    FROM students s
    JOIN grades g ON s.grade_id = g.grade_id
    GROUP BY g.grade_name
""", nativeQuery = true)
List<Map<String, Object>> countStudentsByGrade();

}

