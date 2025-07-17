package com.example.sms.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.sms.Models.GradeSubject;
import java.util.List;
import java.util.Map;
public interface GradeSubjectRepository extends JpaRepository<GradeSubject, Integer> {
    @Query(value="""
            SELECT s.subject_name, COUNT(DISTINCT gs.teacher_id) AS teacher_count
            FROM grades_subjects gs
            JOIN subjects s ON gs.subject_id = s.subject_id
            GROUP BY s.subject_name
            """, nativeQuery=true)
            List<Map<String, Long>> getCountOfTeachersForEachSubject();
}
