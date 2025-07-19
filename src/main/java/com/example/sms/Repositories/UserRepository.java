package com.example.sms.Repositories;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.sms.Models.User;

import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = """
                SELECT u.*
                FROM users u
                JOIN students s ON u.user_id = s.student_id
                WHERE s.grade_id = :gradeId
            """, nativeQuery = true)
    List<User> findUsersByGrade(@Param("gradeId") Integer gradeId);

    Optional<User> findByNationalId(String nationalId);

    @Query(value = """
            SELECT * FROM users WHERE role_id = :roleID
            """, nativeQuery = true)
    Optional<List<User>> getUsersByRoleOptional(Integer roleID);

    @Query(value = """
                SELECT COUNT(*) FROM users
            WHERE role_id =
            (SELECT role_id FROM roles WHERE role_name = 'Student')
                        """, nativeQuery = true)
    Long countOfStudents();

    @Query(value = """
                SELECT COUNT(*) FROM users
            WHERE role_id =
            (SELECT role_id FROM roles WHERE role_name = 'Teacher')
                        """, nativeQuery = true)
    Long countOfTeachers();

    @Query(value = """
            SELECT
                COUNT(CASE WHEN g.grade_name = 'Grade 10' THEN 1 END) AS grade10,
                COUNT(CASE WHEN g.grade_name = 'Grade 11' THEN 1 END) AS grade11,
                COUNT(CASE WHEN g.grade_name = 'Grade 12' THEN 1 END) AS grade12
            FROM
                students s
            JOIN grades g ON s.grade_id = g.grade_id
            """, nativeQuery = true)
    List<Map<String, Long>> getGradeCounts();

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE users
            SET is_deleted = true
            WHERE user_id = :id
                """, nativeQuery = true)
    int deleteUser(@Param("id") int id);

        @Query(value = "SELECT u.* FROM users u WHERE u.is_deleted = false AND u.user_id NOT IN (SELECT user_id FROM accounts)", nativeQuery = true)
    List<User> findUsersWithNoAccounts();
    @Query(value= """
SELECT u.* 
FROM users u
WHERE NOT EXISTS (SELECT 1 FROM students s WHERE s.user_id = u.user_id)
AND u.is_deleted = false
AND u.role_id = 3
            """, nativeQuery=true)
            List<User> findNonStudents();
}
