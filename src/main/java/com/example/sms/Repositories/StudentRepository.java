package com.example.sms.Repositories;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.sms.Models.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    @Query(value = """
    SELECT g.grade_name AS grade_name, COUNT(*) AS student_count
    FROM students s
    JOIN grades g ON s.grade_id = g.grade_id
    GROUP BY g.grade_name
""", nativeQuery = true)
List<Map<String, Object>> countStudentsByGrade();

    @Query(value="""
            SELECT 
    s.session_id,
    s.session_date,
    s.start_time,
    s.end_time,
    s.type,
    sub.subject_name,
    g.grade_name,
    c.class_name
FROM students st
JOIN grades_subjects gs ON st.grade_id = gs.grade_id
JOIN sessions s ON gs.grade_subject_id = s.grade_subject_id
JOIN subjects sub ON gs.subject_id = sub.subject_id
JOIN grades g ON st.grade_id = g.grade_id
JOIN classes c ON st.class_id = c.class_id
WHERE st.user_id = :student_id
  AND s.session_date = CURRENT_DATE
            """, nativeQuery=true)
            List<Map<String, Object>> getTodaySessions(@Param("student_id") Integer student_id);
        @Query(value="""
                WITH student_degrees AS (
    SELECT 
        d.student_id,
        s.subject_name,
        d.degree,
        d.value,
        e.exam_date
    FROM degrees d
    JOIN exams e ON d.exam_id = e.exam_id
    JOIN grades_subjects gs ON e.grade_subject_id = gs.grade_subject_id
    JOIN subjects s ON gs.subject_id = s.subject_id
    WHERE d.student_id = :student_id
),
average_degree AS (
    SELECT 
        student_id,
        ROUND(AVG(degree), 2) AS avg_degree
    FROM student_degrees
    GROUP BY student_id
),
grade_label AS (
    SELECT 
        ad.student_id,
        ad.avg_degree,
        CASE
            WHEN ad.avg_degree >= 90 THEN 'Excellent'
            WHEN ad.avg_degree >= 80 THEN 'Very Good'
            WHEN ad.avg_degree >= 70 THEN 'Good'
            WHEN ad.avg_degree >= 60 THEN 'Acceptable'
            ELSE 'Weak'
        END AS average_grade
    FROM average_degree ad
),
best_subject AS (
    SELECT 
        subject_name,
        value AS best_value,
        degree
    FROM student_degrees
    WHERE student_id = :student_id
    ORDER BY degree DESC
    LIMIT 1
),
latest_subjects AS (
    SELECT 
        subject_name,
        degree,
        value,
        exam_date
    FROM student_degrees
    WHERE student_id = :student_id
    ORDER BY exam_date DESC
    LIMIT 5
)
SELECT 
    gl.avg_degree,
    gl.average_grade,
    bs.subject_name AS best_subject,
    bs.best_value,
    ls.subject_name AS latest_subject,
    ls.degree AS latest_degree,
    ls.value AS latest_value,
    ls.exam_date
FROM grade_label gl
CROSS JOIN best_subject bs
JOIN latest_subjects ls ON TRUE;

                """, nativeQuery=true)
                List<Map<String, Object>> getDegreeRate(@Param("student_id") Integer student_id);
            @Query(
                value="""
                        
SELECT COUNT(DISTINCT gs.subject_id) AS number_of_subjects
FROM students s
JOIN grades_subjects gs ON s.grade_id = gs.grade_id
WHERE s.user_id = :student_user_id
                        """, nativeQuery=true
            )
            Integer SubjectsNumber(@Param("student_user_id") Integer student_user_id);
}
