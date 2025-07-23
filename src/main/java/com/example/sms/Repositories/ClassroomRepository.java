package com.example.sms.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sms.Models.SchoolClass;

public interface ClassroomRepository extends JpaRepository<SchoolClass, Integer> {
}
