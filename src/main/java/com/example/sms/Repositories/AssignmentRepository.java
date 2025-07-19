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

}
