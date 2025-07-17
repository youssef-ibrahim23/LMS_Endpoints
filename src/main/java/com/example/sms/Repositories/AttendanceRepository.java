package com.example.sms.Repositories;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.sms.Models.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
    @Query(value = """
                WITH date_range AS (
                    SELECT generate_series(CAST(:fromDate AS date), CAST(:toDate AS date), interval '1 day') AS attend_date
                ),
                valid_days AS (
                    SELECT d.attend_date
                    FROM date_range d
                    WHERE EXTRACT(DOW FROM d.attend_date) NOT IN (5, 6)
                ),
                attendance_data AS (
                    SELECT s.grade_id, a.attend_date, sa.status
                    FROM student_attendances sa
                    JOIN attendances a ON a.attendance_id = sa.attendance_id
                    JOIN students s ON s.student_id = sa.student_id
                ),
                base_data AS (
                    SELECT g.grade_name, vd.attend_date,
                        CASE WHEN vd.attend_date > CURRENT_DATE THEN -1
                             ELSE COUNT(CASE WHEN ad.status = 'present' THEN 1 END) END AS present_count,
                        CASE WHEN vd.attend_date > CURRENT_DATE THEN -1
                             ELSE COUNT(CASE WHEN ad.status = 'absent' THEN 1 END) END AS absent_count
                    FROM grades g
                    CROSS JOIN valid_days vd
                    LEFT JOIN students s ON s.grade_id = g.grade_id
                    LEFT JOIN attendance_data ad ON ad.grade_id = s.grade_id AND ad.attend_date = vd.attend_date
                    GROUP BY g.grade_name, vd.attend_date
                )
                SELECT grade_name,
                       ARRAY_AGG(present_count ORDER BY attend_date) AS present,
                       ARRAY_AGG(absent_count ORDER BY attend_date) AS absent
                FROM base_data
                GROUP BY grade_name
                ORDER BY grade_name
            """, nativeQuery = true)
    List<Map<String, Object>> countOfAbsentAndAttendees(
            @Param("fromDate") java.sql.Date fromDate,
            @Param("toDate") java.sql.Date toDate);

}
