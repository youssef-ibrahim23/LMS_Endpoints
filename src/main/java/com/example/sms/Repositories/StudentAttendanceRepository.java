package com.example.sms.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sms.Models.StudentAttendance;

public interface StudentAttendanceRepository extends JpaRepository<StudentAttendance, Integer> {
}
